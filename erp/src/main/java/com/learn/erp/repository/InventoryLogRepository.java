package com.learn.erp.repository;

import com.learn.erp.model.InventoryLog;
import com.learn.erp.model.Product;
import com.learn.erp.model.InventoryLog.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryLogRepository extends JpaRepository<InventoryLog, Long> {

    List<InventoryLog> findByProduct(Product product);

    List<InventoryLog> findByProductAndActionType(Product product, ActionType actionType);

    List<InventoryLog> findByActionType(ActionType actionType);
}
