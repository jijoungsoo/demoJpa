package com.example.demo.db.repository.cm;

import com.example.demo.db.domain.cm.CmExcelUpld;
import com.example.demo.db.domain.cm.CmExcelUpldExcelSeq;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CmExcelFileUpldRepository extends JpaRepository< CmExcelUpld, CmExcelUpldExcelSeq> {
	
}
