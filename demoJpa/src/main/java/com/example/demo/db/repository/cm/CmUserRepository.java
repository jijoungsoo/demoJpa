package com.example.demo.db.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.db.domain.cm.CmUser;

@Repository
public interface CmUserRepository extends JpaRepository< CmUser, Long> ,CmUserCustomRepository ,  QuerydslPredicateExecutor<CmUser>  {
	
}
