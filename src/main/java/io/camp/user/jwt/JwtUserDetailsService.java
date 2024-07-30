package io.camp.user.jwt;

import io.camp.user.model.User;
import io.camp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email);
            return new JwtUserDetails(user);
        }
        throw new UsernameNotFoundException("아이디, 비밀번호가 일치하지 않습니다.");
    }
}