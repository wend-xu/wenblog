package com.wende.spring.boot.wenblog.domain.config;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class City {
    @Id
    int cid;
    String city;
    int pid;
}
