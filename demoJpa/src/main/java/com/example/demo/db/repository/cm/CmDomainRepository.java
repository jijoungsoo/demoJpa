package com.example.demo.db.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.db.domain.cm.CmDomain;

@Repository
public interface CmDomainRepository extends JpaRepository< CmDomain, Long> {
	
}
