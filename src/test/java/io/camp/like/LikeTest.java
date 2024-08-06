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
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("낙관적 락 좋아요 테스트")
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

    @Test
    void 낙관적_락_좋아요_테스트() throws InterruptedException, UnsupportedEncodingException, URISyntaxException, ParseException {
        CreateReviewDto dto = new CreateReviewDto();
        dto.setContent("댓글 생성");

        User user1 = new User();
        user1.setEmail("user001");
        user1.setPassword(passwordEncoder.encode("1234"));
        user1.setRole(UserRole.USER);
        user1.setName("홍길동");
        user1.setBirthday("2000-01-01");
        user1.setPhoneNumber("000-1111-1111");
        user1.setGender("남성");
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("user002");
        user2.setPassword(passwordEncoder.encode("1234"));
        user2.setRole(UserRole.USER);
        user2.setName("블루이");
        user2.setBirthday("2010-01-01");
        user2.setPhoneNumber("000-2222-2222");
        user2.setGender("여성");
        userRepository.save(user2);

        User user3 = new User();
        user3.setEmail("user003");
        user3.setPassword(passwordEncoder.encode("1234"));
        user3.setRole(UserRole.USER);
        user3.setName("바루스");
        user3.setBirthday("2020-01-01");
        user3.setPhoneNumber("000-3333-3333");
        user3.setGender("남성");
        userRepository.save(user3);

        JwtUserDetails jwtUserDetails1 = new JwtUserDetails(user1);
        JwtUserDetails jwtUserDetails2 = new JwtUserDetails(user2);
        JwtUserDetails jwtUserDetails3 = new JwtUserDetails(user3);

        campsiteController.getTweetsBlocking("1");

        ReviewDto review = reviewService.createReview(1L, dto, jwtUserDetails1);

        int numberOfThreads = 3;

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

        Exception result = new Exception();

        try {
            future1.get();
            future2.get();
            future3.get();
        } catch (ExecutionException e) {
            result = (Exception) e.getCause();
        }

//        assertTrue(result instanceof OptimisticLockingFailureException);
        assertEquals(3, review.getLikeCount());
    }
}
