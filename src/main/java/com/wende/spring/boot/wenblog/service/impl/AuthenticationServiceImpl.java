package com.wende.spring.boot.wenblog.service.impl;


import com.wende.spring.boot.wenblog.dao.user.UserDao;
import com.wende.spring.boot.wenblog.domain.user.User;
import com.wende.spring.boot.wenblog.service.AuthenticationService;

import com.wende.spring.boot.wenblog.util.ParseTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    UserDao userDao;
    @Override
    public Authentication login(String loginName, String pwd) {
        pwd = ParseTool.encodePassword(pwd);
        User user = userDao.findUserByUserEmailAndUserPwd(loginName,pwd);
        if(user != null){
            //user表与其他表关联是通过user_id的，故此处使用
            return new UsernamePasswordAuthenticationToken(user.getUserId(),user.getUserPwd(),getUserRoles(user));
        }
        return null;
    }

    @Override
    public List<GrantedAuthority> getUserRoles(User user) {
        String userRole = user.getUserRole();
        List<GrantedAuthority> userRoles = new ArrayList<>();
        if(userRole.contains(",")){
            String[] userRolesArray = userRole.split(",");
            for(String userRoleTemp:userRolesArray){
                if(!userRoleTemp.equals(""))
                    userRoles.add(new SimpleGrantedAuthority("ROLE_"+userRoleTemp));
            }
        } else if(userRole == null || userRole.equals("")){
            userRoles.add(new SimpleGrantedAuthority("ROLE_inactive"));
        }else{
            userRoles.add(new SimpleGrantedAuthority("ROLE_"+userRole));
        }
        return userRoles;
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public long getAuthUserId() {
        long userId = 0;
        try{
            userId = Long.valueOf(getAuthentication().getName());
        }catch (NumberFormatException e){
            throw new NumberFormatException("用户未登录，无法获取有效userId");
        }finally {
            return userId;
        }
    }
}
