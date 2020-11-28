package com.example.demo.da.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.CmGrpCd;

@Repository
public interface CmGrpCdRepository extends JpaRepository< CmGrpCd, Long> {
	
}
