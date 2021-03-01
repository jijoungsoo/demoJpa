package com.example.demo.db.repository.cm;

import com.example.demo.db.domain.cm.CmMsgSndRcv;
import com.example.demo.db.domain.cm.CmMsgSndSeqRcvSeq;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CmMsgSndRcvRepository extends JpaRepository<CmMsgSndRcv, CmMsgSndSeqRcvSeq> {
	
}
