package com.wende.spring.boot.wenblog.dao.message;

import com.wende.spring.boot.wenblog.domain.message.StayMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StayMessageDao extends JpaRepository<StayMessage,Long> {
}
