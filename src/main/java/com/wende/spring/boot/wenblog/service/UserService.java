package com.wende.spring.boot.wenblog.service;

import com.wende.spring.boot.wenblog.domain.config.City;
import com.wende.spring.boot.wenblog.domain.config.Provincial;
import com.wende.spring.boot.wenblog.domain.user.User;
import com.wende.spring.boot.wenblog.domain.user.UserData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    String login(String loginName, String password );

    User register(User user);

    boolean checkEmail(String email);

    User getUserById(String id);

    UserData getUserDataByUserId(String userId);

    UserData getUserDataByUserId(long userId);

    User getUserById(long id);

    void createUserData(long userId);

    UserData saveUserData(UserData userData);

    User updateUser(User user);

    String uploadPhoto(MultipartFile file,String filePath,String fileDescription);

    List<Provincial> findProvincials();

    Provincial findProvincial(int pid);

    Provincial findProvincial(String provincial);

    List<City> findCityByProvincial(int pid);

    City findCity(int cid);

    List<City> findAllCity();

    boolean changePassword(String originPassword,String newPassword,long userId);
}
