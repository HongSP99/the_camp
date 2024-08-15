package io.camp.like;


import io.camp.campsite.controller.CampsiteController;
import io.camp.like.service.LikeService;
import io.camp.review.model.dto.CreateReviewDto;
import io.camp.review.model.dto.ReviewDto;
import io.camp.review.service.ReviewService;
import io.camp.user.jwt.JwtUserDetails;
import io.camp.user.model.User;
import io.camp.user.model.UserRole;
import io.camp.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("좋아요 테스트")
public class LikeTest {

    @Autowired
    private CampsiteController campsiteController;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;

    private JwtUserDetails jwtUserDetails1;
    private JwtUserDetails jwtUserDetails2;
    private JwtUserDetails jwtUserDetails3;
    private JwtUserDetails jwtUserDetails4;
    private JwtUserDetails jwtUserDetails5;

    @BeforeEach
    void 좋아요_테스트_세팅() throws Exception {
        CreateReviewDto dto = new CreateReviewDto();
        dto.setContent("댓글 생성");

        user1 = new User();
        user1.setEmail("user001");
        user1.setPassword(passwordEncoder.encode("1234"));
        user1.setRole(UserRole.USER);
        user1.setName("홍길동");
        user1.setBirthday("2000-01-01");
        user1.setPhoneNumber("000-1111-1111");
        user1.setGender("남성");
        userRepository.save(user1);

        user2 = new User();
        user2.setEmail("user002");
        user2.setPassword(passwordEncoder.encode("1234"));
        user2.setRole(UserRole.USER);
        user2.setName("블루이");
        user2.setBirthday("2010-01-01");
        user2.setPhoneNumber("000-2222-2222");
        user2.setGender("여성");
        userRepository.save(user2);

        user3 = new User();
        user3.setEmail("user003");
        user3.setPassword(passwordEncoder.encode("1234"));
        user3.setRole(UserRole.USER);
        user3.setName("바루스");
        user3.setBirthday("2020-01-01");
        user3.setPhoneNumber("000-3333-3333");
        user3.setGender("남성");
        userRepository.save(user3);

        user4 = new User();
        user4.setEmail("user004");
        user4.setPassword(passwordEncoder.encode("1234"));
        user4.setRole(UserRole.USER);
        user4.setName("물고기");
        user4.setBirthday("2024-04-04");
        user4.setPhoneNumber("000-4444-4444");
        user4.setGender("남성");
        userRepository.save(user4);

        user5 = new User();
        user5.setEmail("user005");
        user5.setPassword(passwordEncoder.encode("1234"));
        user5.setRole(UserRole.USER);
        user5.setName("호랑이");
        user5.setBirthday("2025-05-05");
        user5.setPhoneNumber("000-5555-5555");
        user5.setGender("동물");
        userRepository.save(user5);

        jwtUserDetails1 = new JwtUserDetails(user1);
        jwtUserDetails2 = new JwtUserDetails(user2);
        jwtUserDetails3 = new JwtUserDetails(user3);
        jwtUserDetails4 = new JwtUserDetails(user4);
        jwtUserDetails5 = new JwtUserDetails(user5);

        campsiteController.getTweetsBlocking("1");
        ReviewDto review = reviewService.createReview(1L, dto, jwtUserDetails1);
    }

    @Test
    void 좋아요_테스트() throws Exception {
        int numberOfThreads = 5;

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        Future<?> future1 = executorService.submit(
                () -> reviewService.likeReview(1L, jwtUserDetails1)
        );

        Future<?> future2 = executorService.submit(
                () -> reviewService.likeReview(1L, jwtUserDetails2)
        );

        Future<?> future3 = executorService.submit(
                () -> reviewService.likeReview(1L, jwtUserDetails3)
        );

        Future<?> future4 = executorService.submit(
                () -> reviewService.likeReview(1L, jwtUserDetails4)
        );

        Future<?> future5 = executorService.submit(
                () -> reviewService.likeReview(1L, jwtUserDetails5)
        );

        Exception result = new Exception();

        try {
            future1.get();
            future2.get();
            future3.get();
            future4.get();
            future5.get();
        } catch (ExecutionException e) {
            result = (Exception) e.getCause();
        }

        ReviewDto reviewOne = reviewService.getReviewOne(1L);
        assertEquals(5, reviewOne.getLikeCount());
    }
}
