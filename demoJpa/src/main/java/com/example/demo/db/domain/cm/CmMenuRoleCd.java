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


@Builder
@NoArgsConstructor /*org.springframework.orm.jpa.JpaSystemException: No default constructor for entity:*/
@AllArgsConstructor /*https://tzara.tistory.com/73*/
@org.hibernate.annotations.DynamicUpdate/*구분생성시 변경된 것만 한다.*/
@org.hibernate.annotations.DynamicInsert/*구분생성시 null인것은 보내지 않는다.*/
@Data
@Entity
@IdClass(CmMenuNoRoleCd.class)  

@Table(name="tb_cm_menu_role_cd")
public class CmMenuRoleCd {
	@Id
	@Column(nullable = false,unique=false, name="menu_no")
	Long menuNo;
	
	@Id
	@Column(nullable = false,unique=false, length = 50 ,name="role_cd")
	String roleCd;
	
	@Column(nullable = false, name = "crt_usr_no")
	long crtUsrNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="crt_dtm")
	Date crtDtm;
	

	@ManyToOne
	@JoinColumn(name="menu_no", insertable=false, updatable=false)
	CmMenu refCmMenuRoleCd;  //여기 변수명이   ==> CmMenu.java 에  mappedBy = "refCmMenuRoleCd" 이것이 됨

}
