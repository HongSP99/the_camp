package io.camp.coupon.controller;

import io.camp.common.dto.MultiResponseDto;
import io.camp.common.dto.SingleResponseDto;
import io.camp.coupon.model.dto.Coupon;
import io.camp.coupon.service.CouponService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<Coupon>> getAllCoupons(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Coupon> couponPage = couponService.getAllCoupons(pageable);

        MultiResponseDto<Coupon> response = new MultiResponseDto<>(couponPage.getContent(), couponPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponseDto<Coupon>> getCouponById(@PathVariable("id") Long id) {
        return couponService.getCouponById(id)
                .map(coupon -> ResponseEntity.ok(new SingleResponseDto<>(coupon)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SingleResponseDto<Coupon>> createCoupon(@RequestBody Coupon coupon) {
        Coupon createdCoupon = couponService.createCoupon(coupon);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SingleResponseDto<>(createdCoupon));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleResponseDto<Coupon>> updateCoupon(@PathVariable("id") Long id, @RequestBody Coupon couponDetails) {
        return couponService.updateCoupon(id, couponDetails)
                .map(coupon -> ResponseEntity.ok(new SingleResponseDto<>(coupon)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable("id") Long id) {
        if (couponService.deleteCoupon(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
