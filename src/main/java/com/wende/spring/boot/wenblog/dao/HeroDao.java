package com.wende.spring.boot.wenblog.dao;

import com.wende.spring.boot.wenblog.domain.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroDao extends JpaRepository<Hero,Integer> {

}
