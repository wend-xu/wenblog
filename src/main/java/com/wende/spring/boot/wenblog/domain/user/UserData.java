package com.wende.spring.boot.wenblog.domain.user;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "blog_user_data")
//@ConfigurationProperties(prefix = "default.userdata")
@DynamicUpdate
@DynamicInsert
//@Component
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(unique = true)
    long userId;
    @Column(nullable = true)
    String userSex;
    @Column(nullable = true)
    Date userBirthday;
    @Column(nullable = true)
    String userDescription;
    @Column(nullable = true)
    String userSchool;
    @Column(nullable = true)
    String userAddress;
    @Column(nullable = true)
    Timestamp userRegisterTime;
    String userRegisterIp;
    Timestamp userLastUpdateTime;
    @Column(nullable = true)
    String userBloodType;
    @Column(nullable = true)
    String userSignature;
}
