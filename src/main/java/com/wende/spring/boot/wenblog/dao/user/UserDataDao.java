package com.wende.spring.boot.wenblog.dao.user;

import com.wende.spring.boot.wenblog.domain.user.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataDao extends JpaRepository<UserData,Long> {
    UserData findUserDataByUserId(long userId);

    UserData findUserDataById(long id);
}
