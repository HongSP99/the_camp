package io.camp.coupon.model.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Coupon")
@Getter
@Setter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String name;
    private String type;
    private int discountRate;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
