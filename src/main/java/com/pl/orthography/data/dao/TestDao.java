package com.pl.orthography.data.dao;

import com.pl.orthography.data.entity.Test;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDao  extends BasicDao<Test, Long> {
}