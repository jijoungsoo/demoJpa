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
public class CmExcelUpldExcelSeq implements Serializable {
	
	@Id
	@Column(nullable = false, length = 200 ,name="excel_upld_id")
	String excelUpldId;

	@Id
	@Column(nullable = false, name="excel_seq")
	int excelSeq;
}
