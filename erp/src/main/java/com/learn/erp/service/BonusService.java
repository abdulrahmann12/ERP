package com.learn.erp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.erp.dto.BonusCreateDTO;
import com.learn.erp.dto.BonusResponseDTO;
import com.learn.erp.exception.BonusNotFoundException;
import com.learn.erp.exception.UserNotFoundException;
import com.learn.erp.mapper.BonusMapper;
import com.learn.erp.model.Bonus;
import com.learn.erp.model.User;
import com.learn.erp.repository.BonusRepository;
import com.learn.erp.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class BonusService {

    private final BonusRepository bonusRepository;
    private final BonusMapper bonusMapper;
    private final UserRepository userRepository;
    
    public BonusResponseDTO createBonus(@Valid BonusCreateDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException());

        Bonus bonus = bonusMapper.toEntity(dto);
        bonus.setUser(user);
        return bonusMapper.toDTO(bonusRepository.save(bonus));
    }
    
    public List<BonusResponseDTO> getBonusesForUser(Long userId, int month, int year) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        return bonusRepository.findByUser_IdAndMonthAndYear(userId, month, year)
                .stream().map(bonusMapper::toDTO).collect(Collectors.toList());
    }
    
    public List<BonusResponseDTO> getAllBonusForMonth( int month, int year) {

        return bonusRepository.findByMonthAndYear(month, year)
                .stream().map(bonusMapper::toDTO).collect(Collectors.toList());
    }
    
    public void deleteBonus(Long bonusId) {
    	 Bonus bonus = bonusRepository.findById(bonusId)
    			 .orElseThrow(() -> new BonusNotFoundException());
    	 bonusRepository.delete(bonus);
    }			
}
