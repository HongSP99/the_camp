package io.camp.inventory.service;


import io.camp.common.exception.ExceptionCode;
import io.camp.common.exception.inventory.InventoryException;
import io.camp.coupon.model.dto.Coupon;
import io.camp.coupon.repository.CouponRepository;
import io.camp.inventory.model.Inventory;
import io.camp.inventory.model.dto.InventoryDto;
import io.camp.inventory.repository.InventoryRepository;
import io.camp.payment.service.PaymentService;
import io.camp.user.model.User;
import io.camp.user.repository.UserRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
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
                .expireDate(inventoryDto.getExpireDate())
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

    public InventoryDto useCoupon(Long invenSeq){
        Inventory inventory = inventoryRepository.findById(invenSeq)
                .orElseThrow(() -> new InventoryException(ExceptionCode.INVENTORY_NOT_FOUND));

        if(LocalDate.now().isAfter(inventory.getExpireDate())){
            throw new InventoryException(ExceptionCode.INVENTORY_NOT_USE);
        }

        if(inventory.isUse()){
            throw new InventoryException(ExceptionCode.INVENTORY_ALREADY_USE);
        }

        inventory.setUse(true);
        inventoryRepository.save(inventory);

        return inventory.toDto();
    }

    //신규 가입자 웰컴 쿠폰 제공.
    public void grantWelcomeCoupon(User user) {
        Coupon welcomeCoupon = couponRepository.findById(1L).orElseThrow();

        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setUserEmail(user.getEmail());
        inventoryDto.setCoupon(welcomeCoupon);
        inventoryDto.setExpireDate(LocalDate.now().plusDays(30));

        insertInventory(inventoryDto);
    }
}
