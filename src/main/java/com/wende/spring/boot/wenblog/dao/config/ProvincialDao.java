package com.wende.spring.boot.wenblog.dao.config;

import com.wende.spring.boot.wenblog.domain.config.Provincial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvincialDao extends JpaRepository<Provincial,Integer> {
    Provincial findProvincialByPid(int pid);

    Provincial findProvincialByProvincial(String provincial);
}
