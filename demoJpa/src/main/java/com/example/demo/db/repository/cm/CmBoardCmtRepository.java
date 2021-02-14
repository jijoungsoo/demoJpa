package com.example.demo.db.repository.cm;

import com.example.demo.db.domain.cm.CmBoardCmt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmBoardCmtRepository extends JpaRepository< CmBoardCmt, Long> {
	
}
