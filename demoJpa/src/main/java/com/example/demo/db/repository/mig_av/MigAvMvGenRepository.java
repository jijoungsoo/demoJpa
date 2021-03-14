package com.example.demo.db.repository.mig_av;

import com.example.demo.db.domain.mig_av.MigAvMvGen;
import com.example.demo.db.domain.mig_av.MigAvMvGenIdx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigAvMvGenRepository  extends JpaRepository< MigAvMvGen,MigAvMvGenIdx> {
	
}
