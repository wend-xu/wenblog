package com.wende.spring.boot.wenblog.domain.message;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name="blog_stay_message")
public class StayMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    long userId;
    long stayUserId;
    String messageContent;
    Timestamp messageStayTime;
}
