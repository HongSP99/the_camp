package io.camp.inventory.controller;

import io.camp.inventory.model.dto.InventoryDto;
import io.camp.inventory.service.InventoryService;
import io.camp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {


    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDto> insertInventory(@AuthenticationPrincipal UserDetails userDetails, InventoryDto inventoryDto){

        inventoryDto.setUserEmail(userDetails.getUsername());

        inventoryService.insertInventory(inventoryDto);

        return ResponseEntity.ok(inventoryDto);
    }


    @DeleteMapping("/{seq}")
    public ResponseEntity<Long> deleteInventory(@PathVariable("seq") Long seq){
        inventoryService.deleteInventoryBySeq(seq);
        return ResponseEntity.ok(seq);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDto>> findInventoryByUser(@AuthenticationPrincipal UserDetails userDetails){
       List<InventoryDto> dtos = inventoryService.findInventoriesByUserEmail(userDetails.getUsername());
       return ResponseEntity.ok(dtos);
    }





}
