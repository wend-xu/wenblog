package com.wende.spring.boot.wenblog.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;
}
