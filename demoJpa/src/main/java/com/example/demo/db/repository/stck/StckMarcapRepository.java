package com.example.demo.db.repository.stck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.db.domain.cm.CmSeq;
import com.example.demo.db.domain.marcap.StckMarcap;

@Repository
public interface StckMarcapRepository  extends JpaRepository< StckMarcap, String> {
	
}
