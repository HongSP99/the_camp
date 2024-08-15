package io.camp.inventory.controller;

import io.camp.common.dto.SingleResponseDto;
import io.camp.inventory.model.dto.InventoryDto;
import io.camp.inventory.service.InventoryService;
import io.camp.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "인벤토리 등록", description = "사용자의 토큰정보를 사용해 쿠폰 등록")
    @PostMapping
    public ResponseEntity<InventoryDto> insertInventory(@AuthenticationPrincipal UserDetails userDetails, InventoryDto inventoryDto){

        inventoryDto.setUserEmail(userDetails.getUsername());

        inventoryService.insertInventory(inventoryDto);

        return ResponseEntity.ok(inventoryDto);
    }


    @Operation(summary = "인벤토리 삭제", description = "seq에 해당하는 인벤토리 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @DeleteMapping("/{seq}")
    public ResponseEntity<Long> deleteInventory(@PathVariable("seq") Long seq){
        inventoryService.deleteInventoryBySeq(seq);
        return ResponseEntity.ok(seq);
    }

    @Operation(summary = "인벤토리 불러오기 ", description = "사용자의 토큰정보를 사용해 인벤토리 정보 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @GetMapping
    public ResponseEntity<List<InventoryDto>> findInventoryByUser(@AuthenticationPrincipal UserDetails userDetails){
       List<InventoryDto> dtos = inventoryService.findInventoriesByUserEmail(userDetails.getUsername());
       return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "인벤토리 불러오기", description = "seq에 해당하는 인벤토리 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
    })
    @PatchMapping("/{invenSeq}")
    public ResponseEntity<SingleResponseDto<InventoryDto>> useCoupon(@PathVariable("invenSeq") Long invenSeq){
        InventoryDto dto = inventoryService.useCoupon(invenSeq);

        return new ResponseEntity<>(
                new SingleResponseDto<>(dto), HttpStatus.OK
        );
    }
}
