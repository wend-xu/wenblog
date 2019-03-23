package com.wende.spring.boot.wenblog.service;

import com.wende.spring.boot.wenblog.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface AuthenticationService {
    Authentication login(String loginName,String pwd);

    List<GrantedAuthority> getUserRoles(User user);

    Authentication getAuthentication();

    long getAuthUserId();
}
