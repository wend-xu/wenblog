package com.wende.spring.boot.wenblog.domain.log;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "blog_login_history")
@Entity
@Data
public class LoginHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;
    int loginUserId;
    Timestamp loginTime;
    String loginIp;
}
