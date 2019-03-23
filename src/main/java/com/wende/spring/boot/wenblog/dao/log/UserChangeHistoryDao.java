package com.wende.spring.boot.wenblog.dao.log;

import com.wende.spring.boot.wenblog.domain.log.UserChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChangeHistoryDao extends JpaRepository<UserChangeHistory,Long> {
}
