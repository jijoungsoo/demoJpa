package com.example.demo.db.repository.cm;

import com.example.demo.db.domain.cm.CmMsgTmpl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmMsgTmplRepository extends JpaRepository< CmMsgTmpl, Long> {
	
}
