package io.camp.inventory.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryDto {

    private Long seq;

    private Long couponSeq;

    private String userEmail;

    private LocalDate exprireDate;

    private int count;


}
