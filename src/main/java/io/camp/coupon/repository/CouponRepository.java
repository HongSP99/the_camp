package io.camp.coupon.repository;

import io.camp.coupon.model.dto.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Page<Coupon> findByIsDeletedFalse(Pageable pageable);
}
