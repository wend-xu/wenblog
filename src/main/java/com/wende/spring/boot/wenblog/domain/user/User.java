package com.wende.spring.boot.wenblog.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "blog_user")
@DynamicInsert
@DynamicUpdate
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long userId;//用户id
    //@Column(name = "user_login_name",unique = true)
    //private String loginName;
    //private String userPhone;//手机号
    private String userEmail;
    //private int userQq;
    //private String userWechat;
    @Column(columnDefinition = "varchar(255) not null default  \"INACTIVE\"")
    private String userRole;
    private String userName;
    private String userPwd;
    private String userImageUrl;
    //private int userMark;
    @Transient
    private UserData userData;

}
