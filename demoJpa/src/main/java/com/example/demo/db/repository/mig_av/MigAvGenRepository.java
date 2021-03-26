package com.example.demo.db.repository.mig_av;

import com.example.demo.db.domain.mig_av.MigAvGen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigAvGenRepository  extends JpaRepository< MigAvGen,Long> {
	
}
