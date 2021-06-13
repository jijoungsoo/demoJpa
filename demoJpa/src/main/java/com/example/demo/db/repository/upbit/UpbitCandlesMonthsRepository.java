package com.example.demo.db.repository.upbit;

import com.example.demo.db.domain.upbit.UpbitCandlesMonths;
import com.example.demo.db.domain.upbit.UpbitCandlesMonthsIdx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpbitCandlesMonthsRepository  extends JpaRepository< UpbitCandlesMonths,UpbitCandlesMonthsIdx> {
	
}
