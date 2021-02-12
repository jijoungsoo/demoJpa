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

@Builder
@NoArgsConstructor /*org.springframework.orm.jpa.JpaSystemException: No default constructor for entity:*/
@AllArgsConstructor /*https://tzara.tistory.com/73*/
@org.hibernate.annotations.DynamicUpdate/*구분생성시 변경된 것만 한다.*/
@org.hibernate.annotations.DynamicInsert/*구분생성시 null인것은 보내지 않는다.*/
@Data
@Entity
@Table(name="tb_cm_role_cd")
public class CmRoleCd {
	@Id
	@Column(nullable = false,unique=true, length = 50 ,name="role_cd")
	String roleCd;
	
	@Column(nullable = false,unique=false, length = 100 ,name="role_nm")
	String roleNm;
	

	@Column(nullable = false, length = 1, name = "use_yn")
	String useYn;
		
	@Column(nullable = true, length = 4000 ,name="rmk")
	String rmk;
	

	@Column(nullable = true, length = 5 ,name="ord")
	String ord;
	
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


	@Builder.Default
	@OneToMany(mappedBy = "cmRoleCd")  
	List<CmUserRoleCd> cmUserRoleCds = new ArrayList<CmUserRoleCd>();

}