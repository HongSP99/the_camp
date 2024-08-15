package io.camp.inventory.service;


import io.camp.coupon.model.dto.Coupon;
import io.camp.coupon.repository.CouponRepository;
import io.camp.inventory.model.Inventory;
import io.camp.inventory.model.dto.InventoryDto;
import io.camp.inventory.repository.InventoryRepository;
import io.camp.user.model.User;
import io.camp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final UserRepository userRepository;

    private final CouponRepository couponRepository;

    private final InventoryRepository inventoryRepository;

    @Transactional
    public InventoryDto insertInventory(InventoryDto inventoryDto){
        User user  = userRepository.findByEmail(inventoryDto.getUserEmail());
        Coupon coupon = couponRepository.findById(inventoryDto.getCouponSeq()).orElseThrow();

       Inventory inventory = Inventory.builder().user(user)
                .coupon(coupon)
                .count(inventoryDto.getCount())
                .expireDate(inventoryDto.getExprireDate())
                .build();

       Inventory saved = inventoryRepository.save(inventory);
       inventoryDto.setSeq(saved.getSeq());

       return inventoryDto;
    }

    @Transactional
    public Long deleteInventoryBySeq(Long seq){
        inventoryRepository.deleteById(seq);
        return seq;
    }

    @Transactional(readOnly = true)
    public List<InventoryDto> findInventoriesByUserEmail(String email){
        List<Inventory> inventories = inventoryRepository.findInventoryByUserEmail(email);
        List<InventoryDto> dtos = inventories.stream().map(Inventory::toDto).toList();
        return dtos;
    }

}
