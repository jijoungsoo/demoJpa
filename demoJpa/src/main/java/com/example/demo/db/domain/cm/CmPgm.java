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
@Table(name="tb_cm_pgm")
public class CmPgm {
	@Id
	@Column(nullable = false,unique=true ,name="pgm_no")
	long pgmNo;
	
	@Column(nullable = false,unique=true, length = 50 ,name="pgm_id")
	String pgmId;
	
	@Column(nullable = false,unique=false, length = 100 ,name="pgm_nm")
	String pgmNm;
		
	@Column(nullable = true, length = 4000 ,name="rmk")
	String rmk;
	

	@Column(nullable = true, length = 5 ,name="ord")
	String ord;
	
	@Column(nullable = true, length = 100 ,name="category")
	String category;
	
	@Column(nullable = true, length = 1000 ,name="dir_link")
	String dirLink;
	
	@Column(nullable = true, length = 200 ,name="pgm_link")
	String pgmLink;
	
	@Column(nullable = false, name = "crt_usr_no")
	long crtUsrNo;
	
	@Column(nullable = false, name = "updt_usr_no")
	long updtUsrNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="crt_dtm")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="updt_dtm")
	Date updtDtm;
}