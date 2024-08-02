package io.camp.payment.controller;

import io.camp.payment.model.Payment;
import io.camp.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final PaymentRepository paymentRepository;

    @GetMapping("/gtest")
    public String gtest() {
        System.out.println("연결 테스트");
        return "접속 테스트";
    }

    @GetMapping("/qtest")
    public String qtest() {
        System.out.println("쿼리dsl 테스트");
        List<Integer> list = paymentRepository.qTestQueryDsl();
        for (Integer i : list) {
            System.out.println("test : " + i);
        }

        return "qtest";
    }
}
