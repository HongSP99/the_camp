package io.camp.user.service;


import io.camp.common.exception.ExceptionCode;
import io.camp.common.exception.user.CustomException;
import io.camp.user.jwt.JwtUserDetails;
import io.camp.user.model.User;
import io.camp.user.model.UserRole;
import io.camp.user.model.dto.JoinDto;
import io.camp.user.model.dto.RoleGetDto;
import io.camp.user.model.dto.UserPaymentGetDto;
import io.camp.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public User getVerifiyLoginCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        if (email.equals("anonymousUser")) {
            throw new CustomException(ExceptionCode.USER_INVALID);
        }
        return userRepository.findByEmail(email);
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
    public void userInit() {
        if (userRepository.findByEmail("admin") != null) {
            return;
        }

        User user = new User();
        user.setEmail("admin");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRole(UserRole.ADMIN);
        user.setName("관리자");
        user.setBirthday("0000-00-00");
        user.setPhoneNumber("000-1234-5678");
        user.setGender("성별");
        userRepository.save(user);

        user = new User();
        user.setEmail("user01");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRole(UserRole.USER);
        user.setName("홍길동");
        user.setBirthday("2000-01-01");
        user.setPhoneNumber("000-1111-1111");
        user.setGender("남성");
        userRepository.save(user);

        user = new User();
        user.setEmail("user02");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRole(UserRole.USER);
        user.setName("블루이");
        user.setBirthday("2010-01-01");
        user.setPhoneNumber("000-2222-2222");
        user.setGender("여성");
        userRepository.save(user);

        user = new User();
        user.setEmail("user03");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRole(UserRole.USER);
        user.setName("바루스");
        user.setBirthday("2020-01-01");
        user.setPhoneNumber("000-3333-3333");
        user.setGender("남성");
        userRepository.save(user);

        user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRole(UserRole.USER);
        user.setName("이메일용");
        user.setBirthday("2020-02-02");
        user.setPhoneNumber("000-4444-4444");
        user.setGender("남성");
        userRepository.save(user);
    }

    @Transactional
    public void join(JoinDto joinDto) {
        if (userRepository.existsByEmail(joinDto.getEmail())) {
            throw new CustomException(ExceptionCode.EMAIL_ALREADY_EXISTS);
        }

        User user = new User();
        user.setEmail(joinDto.getEmail());
        user.setPassword(passwordEncoder.encode(joinDto.getPassword()));
        user.setRole(UserRole.USER);
        user.setName(joinDto.getName());
        user.setBirthday(joinDto.getBirthday());
        user.setPhoneNumber(joinDto.getPhoneNumber());
        user.setGender(joinDto.getGender());
        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(String currentPassword, String newPassword) {
        User user = getVerifiyLoginCurrentUser();
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new CustomException(ExceptionCode.INVALID_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    public RoleGetDto verifyRole(JwtUserDetails jwtUserDetails) {
        RoleGetDto roleGetDto = new RoleGetDto();

        if (jwtUserDetails == null) {
            roleGetDto.setRole(UserRole.GUEST.getKey());
            return roleGetDto;
        }

        User user = jwtUserDetails.getUser();

        if (user.getRole() == UserRole.USER) {
            roleGetDto.setRole(UserRole.USER.getKey());
            return roleGetDto;
        } else if (user.getRole() == UserRole.ADMIN) {
            roleGetDto.setRole(UserRole.ADMIN.getKey());
            return roleGetDto;
        }
        return roleGetDto;
    }

    public UserPaymentGetDto getUserPayment(JwtUserDetails jwtUserDetails) {
        UserPaymentGetDto userPaymentGetDto = new UserPaymentGetDto();

        if (jwtUserDetails == null) {
            throw  new CustomException(ExceptionCode.USER_NOT_FOUND);
        }

        User user = jwtUserDetails.getUser();
        userPaymentGetDto.setEmail(user.getEmail());
        userPaymentGetDto.setFullName(user.getName());
        userPaymentGetDto.setPhoneNumber(user.getPhoneNumber());

        return userPaymentGetDto;
    }
    @Transactional
    public void resetPassword(String email) throws MessagingException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException(ExceptionCode.UNREGISTERED_EMAIL);
        }

        String tempPassword = mailService.generateTemporaryPassword();
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        mailService.sendTemporaryPassword(email, tempPassword);
    }

}
