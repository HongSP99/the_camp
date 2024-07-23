package io.camp.user.service;

import io.camp.user.model.User;
import io.camp.user.model.UserRole;
import io.camp.user.model.dto.JoinDto;
import io.camp.user.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

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
