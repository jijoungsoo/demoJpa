package com.example.demo.db.domain.upbit;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class UpbitTradesTicksIdx implements Serializable {
	@Id
	@Column(nullable = false,unique=false ,length = 1000 ,name="market")
	String market;

	@Id
	@Column(nullable = true,unique=false,name="sequential_id")
	Long sequentialId;

	//체결 시각(UTC 기준)
	@Id
	@Column(nullable = false,unique=false, length = 1000 ,name="trade_date_utc")
	String tradeDateUtc	;
	//체결 시각(UTC 기준)
	@Id
	@Column(nullable = false,unique=false, length = 1000 ,name="trade_time_utc")
	String tradeTimeUtc;

	
	//체결 타임스탬프
	@Id
	@Column(nullable = true,unique=false,name="timestamp")
	Long timestamp;
}
