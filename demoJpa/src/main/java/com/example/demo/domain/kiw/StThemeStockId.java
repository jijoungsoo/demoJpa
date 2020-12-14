package com.example.demo.domain.kiw;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class StThemeStockId implements Serializable {
	@EqualsAndHashCode.Include
	@Id
	@Column(nullable = false, length = 3 ,name="THEME_CD")
	String themeCd;
	
	@EqualsAndHashCode.Include
	@Id
	String stockCd;	
}
