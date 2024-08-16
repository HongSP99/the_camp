package io.camp.coupon.service;

import io.camp.coupon.model.dto.Coupon;
import io.camp.coupon.repository.CouponRepository;
import io.camp.user.model.User;
import io.camp.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    public CouponService(CouponRepository couponRepository, UserRepository userRepository) {
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    public Page<Coupon> getAllCoupons(Pageable pageable) {
        return couponRepository.findByIsDeletedFalse(pageable);
    }

    public Optional<Coupon> getCouponById(Long id) {
        return couponRepository.findById(id);
    }

    @Transactional
    public Coupon createCoupon(Coupon coupon) {
        coupon.setCreateDate(LocalDate.now());
        coupon.setDeleted(false);
        return couponRepository.save(coupon);
    }

    @Transactional
    public Optional<Coupon> updateCoupon(Long id, Coupon couponDetails) {
        return couponRepository.findById(id).map(coupon -> {
            coupon.setName(couponDetails.getName());
            coupon.setType(couponDetails.getType());
            coupon.setDiscountRate(couponDetails.getDiscountRate());
            coupon.setExpireDate(couponDetails.getExpireDate());
            return couponRepository.save(coupon);
        });
    }

    @Transactional
    public boolean deleteCoupon(Long id) {
        return couponRepository.findById(id).map(coupon -> {
            coupon.setDeleted(true);
            couponRepository.save(coupon);
            return true;
        }).orElse(false);
    }
}
