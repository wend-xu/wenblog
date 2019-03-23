package com.wende.spring.boot.wenblog.domain.log;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "blog_user_change_history")
@Data
public class UserChangeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    long userId;
    String changeField;
    String changeValue;
    String beforeValue;
    Timestamp changeTime;
}
