package com.wende.spring.boot.wenblog.dao.user;

import com.wende.spring.boot.wenblog.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Long> {
    void deleteUsersByUserName(String userName);

    User findUserByUserNameAndUserPwd(String userName , String pwd);

    User findUserByUserEmailAndUserPwd(String userEmail , String pwd);

    User findUserByUserEmail(String userEmail);

    User findUserByUserId(long userId);
}
