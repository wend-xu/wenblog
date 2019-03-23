package com.wende.spring.boot.wenblog.dao.config;

import com.wende.spring.boot.wenblog.domain.config.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityDao extends JpaRepository<City,Integer> {
    List<City> findCitiesByPid(int pid);

    City findCityByCid(int cid);
}
