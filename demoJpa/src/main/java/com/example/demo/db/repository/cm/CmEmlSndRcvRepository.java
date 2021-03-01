package com.example.demo.db.repository.cm;

import com.example.demo.db.domain.cm.CmEmlSndRcv;
import com.example.demo.db.domain.cm.CmEmlSndSeqRcvSeq;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CmEmlSndRcvRepository extends JpaRepository<CmEmlSndRcv, CmEmlSndSeqRcvSeq> {
	
}
