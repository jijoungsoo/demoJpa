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
public class CmUserNoRoleCd implements Serializable {
	private static final long serialVersionUID = 7348959431908690105L;

	@Id
	@Column(nullable = false, name = "user_no")
	long userNo;

	@Id
	@Column(nullable = false,length = 50 , name = "role_cd")
	String roleCd;
}
