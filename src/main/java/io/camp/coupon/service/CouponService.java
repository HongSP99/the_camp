package io.camp.coupon.service;

import io.camp.coupon.model.dto.Coupon;
import io.camp.coupon.repository.CouponRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Page<Coupon> getAllCoupons(Pageable pageable) {
        return couponRepository.findByIsDeletedFalse(pageable);
    }

    public Optional<Coupon> getCouponById(Long id) {
        return couponRepository.findById(id);
    }

    public Coupon createCoupon(Coupon coupon) {
        coupon.setCreateDate(LocalDateTime.now());
        coupon.setDeleted(false);
        return couponRepository.save(coupon);
    }

    public Optional<Coupon> updateCoupon(Long id, Coupon couponDetails) {
        return couponRepository.findById(id).map(coupon -> {
            coupon.setName(couponDetails.getName());
            coupon.setType(couponDetails.getType());
            coupon.setDiscountRate(couponDetails.getDiscountRate());
            coupon.setExpireDate(couponDetails.getExpireDate());
            return couponRepository.save(coupon);
        });
    }

    public boolean deleteCoupon(Long id) {
        return couponRepository.findById(id).map(coupon -> {
            coupon.setDeleted(true);
            couponRepository.save(coupon);
            return true;
        }).orElse(false);
    }
}
