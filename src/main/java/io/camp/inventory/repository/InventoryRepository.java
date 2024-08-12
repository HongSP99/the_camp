package io.camp.inventory.repository;

import io.camp.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository  extends JpaRepository<Inventory, Long> , InventoryRepositoryCustom{
}
