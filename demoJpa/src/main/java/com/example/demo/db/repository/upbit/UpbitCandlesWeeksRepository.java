package com.example.demo.db.repository.upbit;

import com.example.demo.db.domain.upbit.UpbitCandlesWeeks;
import com.example.demo.db.domain.upbit.UpbitCandlesWeeksIdx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpbitCandlesWeeksRepository  extends JpaRepository< UpbitCandlesWeeks,UpbitCandlesWeeksIdx> {
	
}
