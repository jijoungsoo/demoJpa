package com.example.demo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Data
@Entity
@Table(name="TB_CM_CD")
@IdClass(CmCdId.class)  
public class CmCd {
	
	@Id  
	@Column(nullable = false, length = 30 ,name="GRP_CD")
	String grpCd;
	


	
	@ManyToOne
	@JoinColumn(name="GRP_CD", insertable=false, updatable=false)
	CmGrpCd cmGrpCd;
	
	
	@Id
	@Column(nullable = false, length = 30 ,name="CD")
	String cd;
	
	@Column(nullable = false, length = 100 ,name="CD_NM")
	String cdNm;
	
		
	@Column(nullable = false, length = 1 ,name="USE_YN")
	String useYn;
	
	@Column(nullable = true, length = 4000 ,name="RMK")
	String rmk;
	
	@Column(nullable = true, name="ORD")
	int ord;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="CRT_DTM")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="UPDT_DTM")
	Date updtDtm;
	

	public void setCmGrpCd(CmGrpCd t) {
		this.cmGrpCd =t;
		if(!t.getCmCds().contains(this)) {
			t.getCmCds().add(this);
		}
	}
}
