package com.wende.spring.boot.wenblog.domain.log;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "blog_email_log")
public class EmailLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    long userId;
    String fromAddress;
    String toAddress;
    String content;
    Timestamp sendTime;
}
