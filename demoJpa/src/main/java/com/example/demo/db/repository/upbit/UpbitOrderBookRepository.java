package com.example.demo.db.repository.upbit;

import com.example.demo.db.domain.upbit.UpbitOrderBook;
import com.example.demo.db.domain.upbit.UpbitOrderBookIdx;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpbitOrderBookRepository  extends JpaRepository< UpbitOrderBook,UpbitOrderBookIdx> {
	
}
