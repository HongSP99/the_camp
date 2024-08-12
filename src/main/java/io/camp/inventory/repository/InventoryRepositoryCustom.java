package io.camp.inventory.repository;


import io.camp.inventory.model.Inventory;

import java.util.List;

public interface InventoryRepositoryCustom {

    List<Inventory> findInventoryByUserEmail(String email);

}
