package com.example.demo.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CmCdId implements Serializable {
	@EqualsAndHashCode.Include
	@Id
	@Column(nullable = false, length = 30 ,name="GRP_CD")
	String grpCd;
	
	@EqualsAndHashCode.Include
	@Id
	String cd;	
}
