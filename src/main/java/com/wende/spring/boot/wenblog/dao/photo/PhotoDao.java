package com.wende.spring.boot.wenblog.dao.photo;

import com.wende.spring.boot.wenblog.domain.photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoDao extends JpaRepository<Photo,Long> {
}
