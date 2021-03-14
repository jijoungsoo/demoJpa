package com.example.demo.db.domain.mig_av;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class MigAvMvGenIdx implements Serializable {
	@Id
	@Column(nullable = false,unique=false ,name="dvd_idx")
	long dvdIdx;

	@Id
	@Column(nullable = true,unique=false, name="menu_no")
	long menuNo;

	@Id
	@Column(nullable = true,unique=false, name="cate_no")
	long cateNo;
}
