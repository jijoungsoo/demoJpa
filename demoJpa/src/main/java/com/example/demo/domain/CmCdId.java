package com.example.demo.domain;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CmCdId implements Serializable {
	@EqualsAndHashCode.Include
	@Id
	String cmGrpCd;
	
	@EqualsAndHashCode.Include
	@Id
	String cd;	
}
