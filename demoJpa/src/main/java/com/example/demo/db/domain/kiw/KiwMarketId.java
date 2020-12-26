package com.example.demo.db.domain.kiw;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class KiwMarketId implements Serializable {
	@EqualsAndHashCode.Include
	@Id
	String stockCd;
	
	@EqualsAndHashCode.Include
	@Id
	String marketCd;	
}
