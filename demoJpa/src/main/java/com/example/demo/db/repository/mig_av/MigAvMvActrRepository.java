package com.example.demo.db.repository.mig_av;

import com.example.demo.db.domain.mig_av.MigAvMvActr;
import com.example.demo.db.domain.mig_av.MigAvMvActrIdx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigAvMvActrRepository  extends JpaRepository< MigAvMvActr,MigAvMvActrIdx> {
	
}
