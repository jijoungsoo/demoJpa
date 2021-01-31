package com.example.demo.db.repository.av;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.db.domain.av.AvActr;

@Repository
public interface AvActrRepository  extends JpaRepository< AvActr,Long> {
	
}
