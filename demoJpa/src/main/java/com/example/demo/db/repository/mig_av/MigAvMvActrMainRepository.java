package com.example.demo.db.repository.mig_av;

import com.example.demo.db.domain.mig_av.MigAvMvActrMain;
import com.example.demo.db.domain.mig_av.MigAvMvActrMainIdx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigAvMvActrMainRepository  extends JpaRepository< MigAvMvActrMain,MigAvMvActrMainIdx> {
	
}
