package com.example.demo.db.domain.cm;

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
import lombok.ToString;

@Builder
@NoArgsConstructor /*org.springframework.orm.jpa.JpaSystemException: No default constructor for entity:*/
@AllArgsConstructor /*https://tzara.tistory.com/73*/
@org.hibernate.annotations.DynamicUpdate/*구분생성시 변경된 것만 한다.*/
@org.hibernate.annotations.DynamicInsert/*구분생성시 null인것은 보내지 않는다.*/
@Data
@ToString(exclude = "cmGrpCd")  /*무한루프 오류 때문에 필요*/
@Entity
@Table(name="tb_cm_cd")
@IdClass(CmCdId.class)  
public class CmCd {
	@Id
	@Column(nullable = false, length = 30 ,name="grp_cd")
	String grpCd;
	
	@Id
	@Column(nullable = false, length = 30 ,name="cd")
	String cd;
	
	@Column(nullable = false, length = 100 ,name="cd_nm")
	String cdNm;
	
		
	@Column(nullable = false, length = 1 ,name="use_yn")
	String useYn;
	
	@Column(nullable = true, length = 4000 ,name="rmk")
	String rmk;
	
	@Column(nullable = true, name="ord")
	int ord;

	
	@Column(nullable = true, length = 100 ,name="attr1")
	String attr1;

	@Column(nullable = true, length = 100 ,name="attr2")
	String attr2;

	@Column(nullable = true, length = 100 ,name="attr3")
	String attr3;

	@Column(nullable = true, length = 100 ,name="attr4")
	String attr4;
	
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
	  
	@ManyToOne
	@JoinColumn(name="grp_cd", insertable=false, updatable=false)
	CmGrpCd cmGrpCd;
	
	public void setCmGrpCd(CmGrpCd t) {
		this.cmGrpCd =t;
		if(!t.getCmCds().contains(this)) {
			t.getCmCds().add(this);
		}
	}
	
	
}
