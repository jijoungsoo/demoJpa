package com.example.demo.db.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.db.domain.cm.CmUserNoRoleCd;
import com.example.demo.db.domain.cm.CmUserRoleCd;

@Repository
public interface CmUserRoleCdRepository extends JpaRepository< CmUserRoleCd, CmUserNoRoleCd> {
	
}
