package io.camp.user.jwt;

import io.camp.user.model.User;
import io.camp.user.model.UserRole;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class JwtUserDetails implements UserDetails {

    private User user;

    public JwtUserDetails(User user) {
        this.user = user;
    }

    public UserRole getRole() {
        return user.getRole();
    }
    public User getUser() { return user; }


    public Long getSeq() {
        return user.getSeq();
    }

    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }
    public String getGender() {
        return user.getGender();
    }
    public String getName() {
        return user.getName();
    }
    public String getBirthDay() {
        return user.getBirthday();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(user.getRole().getKey()).stream().map(role -> new SimpleGrantedAuthority(role)).toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
