package com.example.demo.db.repository.stck;

import com.example.demo.db.domain.marcap.StckMarcap;
import com.example.demo.db.domain.marcap.StckMarcapId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StckMarcapRepository  extends JpaRepository< StckMarcap, StckMarcapId> {
	
}
