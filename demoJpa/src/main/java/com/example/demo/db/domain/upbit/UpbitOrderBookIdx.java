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
public class UpbitOrderBookIdx implements Serializable {
	@Id
	@Column(nullable = false,unique=false ,length = 1000 ,name="market")
	String market;

	@Id
	//호가 생성 시각
	@Column(nullable = false,unique=false,name="timestamp")
	Long timestamp;
}
