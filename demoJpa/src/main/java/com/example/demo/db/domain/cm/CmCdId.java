package com.example.demo.db.domain.cm;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class CmCdId implements Serializable {
	@EqualsAndHashCode.Include
	@Id
	@Column(nullable = false, length = 30 ,name="grp_cd")
	String grpCd;
	
	@EqualsAndHashCode.Include
	@Id
	String cd;	
}
