package com.example.demo.db.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.db.domain.cm.CmCd;
import com.example.demo.db.domain.cm.CmCdId;

@Repository
public interface CmCdRepository extends JpaRepository< CmCd, CmCdId> {
	
}
