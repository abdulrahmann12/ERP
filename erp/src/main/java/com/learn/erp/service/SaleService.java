package com.learn.erp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.annotation.Validated;

import com.learn.erp.config.AfterCommitExecutor;
import com.learn.erp.config.Messages;
import com.learn.erp.dto.CustomerSalesReportDTO;
import com.learn.erp.dto.SaleCreateDTO;
import com.learn.erp.dto.SaleItemCreateDTO;
import com.learn.erp.dto.SaleResponseDTO;
import com.learn.erp.exception.CustomerNotFoundException;
import com.learn.erp.exception.MailSendingException;
import com.learn.erp.exception.ProductNotFoundException;
import com.learn.erp.exception.SaleNotFoundException;
import com.learn.erp.exception.UnauthorizedActionException;
import com.learn.erp.exception.UserNotFoundException;
import com.learn.erp.mapper.CustomerMapper;
import com.learn.erp.mapper.SaleMapper;
import com.learn.erp.model.Customer;
import com.learn.erp.model.Product;
import com.learn.erp.model.Sale;
import com.learn.erp.model.SaleItems;
import com.learn.erp.model.User;
import com.learn.erp.repository.CustomerRepository;
import com.learn.erp.repository.ProductRepository;
import com.learn.erp.repository.SaleRepository;
import com.learn.erp.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class SaleService {

	private final SaleRepository saleRepository;
	private final UserRepository userRepository;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	private final SaleMapper saleMapper;
	private final CustomerMapper customerMapper;
	private final EmailService emailService;
	private final PdfGeneratorService generatorService;

	@Transactional
	public SaleResponseDTO createSale(Long userId, @Valid SaleCreateDTO dto) {
		User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

		Customer customer = customerRepository.findById(dto.getCustomerId())
				.orElseThrow(CustomerNotFoundException::new);

		Sale sale = Sale.builder().user(user).customer(customer).notes(dto.getNotes()).build();

		BigDecimal totalAmount = BigDecimal.ZERO;
		for (SaleItemCreateDTO itemDTO : dto.getItems()) {
			Product product = productRepository.findById(itemDTO.getProductId())
					.orElseThrow(ProductNotFoundException::new);

			if (product.getStock() < itemDTO.getQuantity()) {
				throw new IllegalArgumentException(Messages.NOT_ENOUGH_STOCK + product.getName());
			}

			product.setStock(product.getStock() - itemDTO.getQuantity());
			productRepository.save(product);

			if (product.getStock() < 10) {
				List<User> storeManagers = userRepository.findByRole(User.Role.STORE_MANAGER);
				for (User manager : storeManagers) {

					TransactionSynchronizationManager.registerSynchronization(new AfterCommitExecutor() {
						@Override
						public void afterCommit() {
							try {
								emailService.sendLowStockAlert(manager, product);
							} catch (Exception e) {
								throw new MailSendingException();
							}
						}
					});
				}
			}
			BigDecimal price = product.getPrice();
			BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
			totalAmount = totalAmount.add(lineTotal);

			SaleItems saleItem = SaleItems.builder().product(product).price(lineTotal).quantity(itemDTO.getQuantity())
					.sale(sale).build();
			sale.getItems().add(saleItem);
		}
		sale.setTotalAmount(totalAmount);
		Sale savedSale = saleRepository.save(sale);

		TransactionSynchronizationManager.registerSynchronization(new AfterCommitExecutor() {
			@Override
			public void afterCommit() {
				try {
					byte[] pdfBytes = generatorService.generateSalePdf(saleMapper.toDTO(savedSale));
					emailService.sendSaleInvoiceWithPdf(customer.getEmail(), savedSale.getSaleId(),
							savedSale.getTotalAmount(), pdfBytes);
				} catch (Exception e) {
					throw new MailSendingException();
				}
			}
		});

		return saleMapper.toDTO(savedSale);
	}

	public List<SaleResponseDTO> getSalesByUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

		if (user.getRole() != User.Role.SALES_EMPLOYEE) {
			throw new UnauthorizedActionException(Messages.NOT_SALES_EMPLOYEE);
		}

		List<Sale> sales = saleRepository.findByUser(user);
		return sales.stream().map(saleMapper::toDTO).collect(Collectors.toList());
	}

	public List<SaleResponseDTO> getSalesByCustomer(Long customerId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);

		List<Sale> sales = saleRepository.findByCustomer(customer);
		return sales.stream().map(saleMapper::toDTO).collect(Collectors.toList());
	}

	public BigDecimal getCustomerTotalSales(Long customerId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);

		List<Sale> sales = saleRepository.findByCustomer(customer);
		return sales.stream().map(Sale::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public Page<SaleResponseDTO> getAllSales(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return saleRepository.findAll(pageable).map(saleMapper::toDTO);
	}

	public SaleResponseDTO getSaleById(Long saleId) {
		Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new SaleNotFoundException());
		return saleMapper.toDTO(sale);
	}

	@Transactional
	public CustomerSalesReportDTO generateCustomerSalesReport(Long customerId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);

		List<Sale> sales = saleRepository.findByCustomer(customer);
		BigDecimal totalAmount = getCustomerTotalSales(customer.getCustomerId());

		return CustomerSalesReportDTO.builder().customer(customerMapper.toDTO(customer)).totalSales(totalAmount)
				.numberOfOrders(sales.size()).build();
	}

	public byte[] generateSalePdfById(Long saleId) {
		return generatorService.generateSalePdfById(saleId);
	}
}