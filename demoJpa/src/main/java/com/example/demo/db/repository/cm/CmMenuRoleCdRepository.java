package com.example.demo.db.repository.cm;

import com.example.demo.db.domain.cm.CmMenuNoRoleCd;
import com.example.demo.db.domain.cm.CmMenuRoleCd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmMenuRoleCdRepository extends JpaRepository< CmMenuRoleCd, CmMenuNoRoleCd> {
	
}
