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
public class CmMenuNoRoleCd implements Serializable {
	

	@Id
	@Column(nullable = false, name = "menu_no")
	long menuNo;

	@Id
	@Column(nullable = false,length = 50 , name = "role_cd")
	String roleCd;
}
