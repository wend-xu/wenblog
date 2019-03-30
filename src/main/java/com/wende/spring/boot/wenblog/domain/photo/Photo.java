package com.wende.spring.boot.wenblog.domain.photo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "blog_photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String originName;
    String photoUrl;
    String photoDescription;
    long albumId;
    Timestamp uploadTime;
    String photoMD5;
}
