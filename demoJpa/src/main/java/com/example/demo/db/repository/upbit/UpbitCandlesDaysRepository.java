package com.example.demo.db.repository.upbit;

import com.example.demo.db.domain.upbit.UpbitCandlesDays;
import com.example.demo.db.domain.upbit.UpbitCandlesDaysIdx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpbitCandlesDaysRepository  extends JpaRepository< UpbitCandlesDays,UpbitCandlesDaysIdx> {
	
}
