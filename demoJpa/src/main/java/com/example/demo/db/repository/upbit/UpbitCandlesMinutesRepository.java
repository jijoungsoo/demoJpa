package com.example.demo.db.repository.upbit;

import com.example.demo.db.domain.upbit.UpbitCandlesMinutes;
import com.example.demo.db.domain.upbit.UpbitCandlesMinutesIdx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpbitCandlesMinutesRepository  extends JpaRepository< UpbitCandlesMinutes,UpbitCandlesMinutesIdx> {
	
}
