package com.example.demo.db.repository.stck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.db.domain.stck.StBuy;

@Repository
public interface StBuyRepository  extends JpaRepository< StBuy,Long> {
	
}
