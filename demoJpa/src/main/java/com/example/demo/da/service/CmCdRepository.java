package com.example.demo.da.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.CmCd;

@Repository
public interface CmCdRepository extends JpaRepository< CmCd, Long> {
	
}
