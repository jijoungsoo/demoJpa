package com.example.demo.db.repository.upbit;

import com.example.demo.db.domain.upbit.UpbitTradesTicks;
import com.example.demo.db.domain.upbit.UpbitTradesTicksIdx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpbitTradesTicksRepository  extends JpaRepository< UpbitTradesTicks,UpbitTradesTicksIdx> {
	
}
