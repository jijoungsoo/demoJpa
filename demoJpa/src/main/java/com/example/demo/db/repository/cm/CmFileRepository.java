package com.example.demo.db.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.db.domain.cm.CmFile;

public interface CmFileRepository extends JpaRepository< CmFile, String> {
	
}
