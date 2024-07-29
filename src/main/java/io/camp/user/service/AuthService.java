package io.camp.user.service;

import io.camp.exception.user.UserAnonymousException;

import io.camp.user.model.User;
import io.camp.user.model.UserRole;
import io.camp.user.model.dto.JoinDto;
import io.camp.user.repository.AuthRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public User getVerifiyLoginCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        if (email.equals("anonymousUser")) {
            throw new UserAnonymousException("유요한 사용자가 아닙니다.");
        }
        return authRepository.findByEmail(email);
    }


    public void testTokenLoginUser() {
        User user = getVerifiyLoginCurrentUser();
        System.out.println("user.seq : " + user.getSeq());
        System.out.println("user.email : " + user.getEmail());
        System.out.println("user.password : " + user.getPassword());
        System.out.println("user.role : " + user.getRole().getTitle() + " " + user.getRole().getKey());
        System.out.println("user.name : " + user.getName());
        System.out.println("user.birthday : " + user.getBirthday());
        System.out.println("user.phoneNumber : " + user.getPhoneNumber());
        System.out.println("user.gender : " + user.getGender());
    }

    @PostConstruct
    public void adminInit() {
        User user = new User();
        user.setEmail("admin");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRole(UserRole.ADMIN);
        user.setName("관리자");
        user.setBirthday("0000-00-00");
        user.setPhoneNumber("000-1234-5678");
        user.setGender("성별");
        authRepository.save(user);
        userInit();
    }

    public void userInit() {
        User user = new User();
        user.setEmail("user01");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRole(UserRole.USER);
        user.setName("홍길동");
        user.setBirthday("2000-01-01");
        user.setPhoneNumber("000-1111-1111");
        user.setGender("남성");
        authRepository.save(user);

        user = new User();
        user.setEmail("user02");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRole(UserRole.USER);
        user.setName("블루이");
        user.setBirthday("2010-01-01");
        user.setPhoneNumber("000-2222-2222");
        user.setGender("여성");
        authRepository.save(user);

        user = new User();
        user.setEmail("user03");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRole(UserRole.USER);
        user.setName("바루스");
        user.setBirthday("2020-01-01");
        user.setPhoneNumber("000-3333-3333");
        user.setGender("남성");
        authRepository.save(user);
    }

    @Transactional
    public void join(JoinDto joinDto) {
        if (authRepository.existsByEmail(joinDto.getEmail())) {
            throw new RuntimeException("아이디가 중복되었습니다");
        }

        User user = new User();
        user.setEmail(joinDto.getEmail());
        user.setPassword(passwordEncoder.encode(joinDto.getPassword()));
        user.setRole(UserRole.USER);
        user.setName(joinDto.getName());
        user.setBirthday(joinDto.getBirthday());
        user.setPhoneNumber(joinDto.getPhoneNumber());
        user.setGender(joinDto.getGender());
        authRepository.save(user);
    }
}
