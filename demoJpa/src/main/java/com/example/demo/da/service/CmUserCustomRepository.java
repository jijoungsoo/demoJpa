package com.example.demo.da.service;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.example.demo.domain.CmUser;

@NoRepositoryBean
public interface CmUserCustomRepository  {
    CmUser findByUserId(String userId);
}
