package com.example.demo.db.domain.marcap;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class StckMarcapId implements Serializable {
	@EqualsAndHashCode.Include
	@Id
	String stockCd;
	
	@EqualsAndHashCode.Include
	@Id
	String stockDt;	
}
