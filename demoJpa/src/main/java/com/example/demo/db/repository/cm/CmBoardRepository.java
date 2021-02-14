package com.example.demo.db.repository.cm;

import com.example.demo.db.domain.cm.CmBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmBoardRepository extends JpaRepository< CmBoard, Long> {
	
}
