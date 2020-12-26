package com.example.demo.db.repository.cm;

import org.springframework.data.repository.NoRepositoryBean;

import com.example.demo.db.domain.cm.CmUser;

@NoRepositoryBean
public interface CmUserCustomRepository  {
    CmUser findByUserId(String userId);
}
