package com.example.demo.db.repository.upbit;

import com.example.demo.db.domain.upbit.UpbitOrderBookUnits;
import com.example.demo.db.domain.upbit.UpbitOrderBookUnitsIdx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpbitOrderBookUnitsRepository  extends JpaRepository< UpbitOrderBookUnits,UpbitOrderBookUnitsIdx> {
	
}
