package com.example.demo.db.domain.cm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@ToString(exclude = "cmCds")    /*무한루프 오류 때문에 필요*/
@Entity
@Table(name="tb_cm_grp_cd")
public class CmGrpCd {
	@Id
	@Column(nullable = false,unique=true, length = 30 ,name="grp_cd")
	private String grpCd;
	
	@Column(nullable = false, length = 100 ,name="grp_nm")
	private String grpNm;
		
	@Column(nullable = false, length = 1 ,name="use_yn")
	private String useYn;
	
	@Column(nullable = true, length = 4000 ,name="rmk")
	String rmk;
	
	@Column(nullable = true, name="ord")
	int ord;	
	
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
	
	@Builder.Default  /*이걸 안붙여주면 Builter를 쎃을때 초기화가 안됨*/
	@OneToMany(mappedBy = "cmGrpCd")  
	/*mappedBy에 써주는 것은 참조되는 쪽 ManyToOne 쪽에 테이블 컬럼명이 아니라 속성명을 써야한다.*/
	/*무언가 버전이 바뀌었나보다 mappedBy에 cmGrpCd가 아니라 cd를 적어줘야 정상동작한다.*/
	List<CmCd> cmCds = new ArrayList<CmCd>();
	/* 	https://ict-nroo.tistory.com/122  
	 * 
	 * lombok를 사용하면 무한루프에 빠진다고 한다.
	 * lombok를 사용할 경우 toString을 재정의해야한다고 한다.
	 * 
	 * https://lng1982.tistory.com/300
	 * 위에 재정의보다 더 쉬운 방법
	 * /
	/*  CmCd에 setCmGrpCd를 구현할 경우 아래경우가 필요 없다고 한다.
	public void addCmCd(CmCd c) {
		this.cmCds.add(c);
		if(c.getCmGrpCd()!=this) {
			c.setCmGrpCd(this);
		}	
	}
	*/
}
