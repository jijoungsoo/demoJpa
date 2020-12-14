package com.example.demo.domain.kiw;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class StRealtimeContractId implements Serializable {
	@EqualsAndHashCode.Include
	@Id
	String stockCd;	
	
	/** 날짜 */
	@EqualsAndHashCode.Include
	@Id
	String stockDt;
	
	/** 현재가 */
	@EqualsAndHashCode.Include
	@Id
	String currTime;
}
