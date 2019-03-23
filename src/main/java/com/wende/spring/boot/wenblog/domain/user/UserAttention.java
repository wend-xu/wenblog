package com.wende.spring.boot.wenblog.domain.user;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@DynamicUpdate
@Table(name = "blog_user_attention")
public class UserAttention {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    long userId;
    long attentionId;
    boolean mutualAttention;
}
