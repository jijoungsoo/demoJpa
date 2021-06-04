package com.example.demo.db.repository.upbit;

import com.example.demo.db.domain.upbit.UpbitMarket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpbitMarketRepository  extends JpaRepository< UpbitMarket,String> {
	
}
