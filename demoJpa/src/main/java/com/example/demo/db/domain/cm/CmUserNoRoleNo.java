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
public class CmUserNoRoleNo implements Serializable {
	@Id
	@Column(nullable = false, name = "user_no")
	long userNo;

	@Id
	@Column(nullable = false, name = "role_no")
	long roleNo;
}
