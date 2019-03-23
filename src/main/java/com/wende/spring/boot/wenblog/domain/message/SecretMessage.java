package com.wende.spring.boot.wenblog.domain.message;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "blog_secret_message")
public class SecretMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    long sendId;
    long receiveId;
    String messageTitle;
    String messageContent;
    boolean hadRead;
}
