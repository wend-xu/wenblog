package com.wende.spring.boot.wenblog.dao.log;

import com.wende.spring.boot.wenblog.domain.log.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogDao extends JpaRepository<EmailLog,Long> {
}
