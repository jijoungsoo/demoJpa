package com.example.demo.db.domain.cm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor /*org.springframework.orm.jpa.JpaSystemException: No default constructor for entity:*/
@AllArgsConstructor /*https://tzara.tistory.com/73*/
@org.hibernate.annotations.DynamicUpdate/*구분생성시 변경된 것만 한다.*/
@org.hibernate.annotations.DynamicInsert/*구분생성시 null인것은 보내지 않는다.*/
@Data
@Entity
@Table(name="tb_cm_excel_upld")
public class CmExcelUpld {
	@Id
	@Column(nullable = false, length = 200 ,name="excel_upld_id")
	String excelUpldId;

	@Column(nullable = false, name="excel_seq")
	int excelSeq;

	@Column(nullable = false, length = 1 ,name="gbn")
	String gbn;  /*H헤더  ,D 데테일*/
	
	@Column(nullable = true, length = 4000 ,name="col00")
	String col00;
	@Column(nullable = true, length = 4000 ,name="col01")
	String col01;
	@Column(nullable = true, length = 4000 ,name="col02")
	String col02;
	@Column(nullable = true, length = 4000 ,name="col03")
	String col03;
	@Column(nullable = true, length = 4000 ,name="col04")
	String col04;
	@Column(nullable = true, length = 4000 ,name="col05")
	String col05;
	@Column(nullable = true, length = 4000 ,name="col06")
	String col06;
	@Column(nullable = true, length = 4000 ,name="col07")
	String col07;
	@Column(nullable = true, length = 4000 ,name="col08")
	String col08;
	@Column(nullable = true, length = 4000 ,name="col09")
	String col09;
	@Column(nullable = true, length = 4000 ,name="col10")
	String col10;
	@Column(nullable = true, length = 4000 ,name="col11")
	String col11;
	@Column(nullable = true, length = 4000 ,name="col12")
	String col12;
	@Column(nullable = true, length = 4000 ,name="col13")
	String col13;
	@Column(nullable = true, length = 4000 ,name="col14")
	String col14;
	@Column(nullable = true, length = 4000 ,name="col15")
	String col15;
	@Column(nullable = true, length = 4000 ,name="col16")
	String col16;
	@Column(nullable = true, length = 4000 ,name="col17")
	String col17;
	@Column(nullable = true, length = 4000 ,name="col18")
	String col18;
	@Column(nullable = true, length = 4000 ,name="col19")
	String col19;
	@Column(nullable = true, length = 4000 ,name="col20")
	String col20;
	
	@Column(nullable = false, name = "CRT_USR_NO")
	long crtUsrNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="crt_dtm")
	Date crtDtm;
}
