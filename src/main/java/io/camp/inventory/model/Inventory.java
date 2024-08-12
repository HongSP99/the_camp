package io.camp.inventory.model;

import io.camp.coupon.model.dto.Coupon;
import io.camp.inventory.model.dto.InventoryDto;
import io.camp.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private User user;

    @ManyToOne
    @JoinColumn(name = "couponSeq")
    private Coupon coupon;

    @Column(name = "expireDate")
    private LocalDate expireDate;

    @Column(name = "count")
    private int count;

    public InventoryDto toDto(){
     return  InventoryDto.builder().seq(seq)
                .userEmail(user.getEmail())
                .couponSeq(coupon.getSeq())
                .exprireDate(expireDate)
                .count(count)
                .build();
    }

}
