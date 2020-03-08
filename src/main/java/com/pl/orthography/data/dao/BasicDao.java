package com.pl.orthography.data.dao;

import com.pl.orthography.data.entity.BasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicDao<ENTITY extends BasicEntity, ID> extends JpaRepository<ENTITY, ID> {
}
