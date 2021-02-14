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
@Table(name="tb_cm_menu")
public class CmMenu {
	@Id
	@Column(nullable = false,unique=true, length = 50 ,name="menu_no")
	Long menuNo;
	
	@Column(nullable = false,unique=true, length = 50 ,name="menu_cd")
	String menuCd;
	
	@Column(nullable = false,unique=false, length = 100 ,name="menu_nm")
	String menuNm;
		
	@Column(nullable = true, length = 50 ,name="prnt_menu_cd")
	String prntMenuCd;
	
	@Column(nullable = true, length = 50 ,name="menu_lvl")
	String menuLvl;
	
	@Column(nullable = true, length = 1 ,name="menu_kind")
	String menuKind;
	
	@Column(nullable = true, length = 4000 ,name="rmk")
	String rmk;
	
	@Column(nullable = true, length = 5 ,name="ord")
	String ord;
	
	@Column(nullable = true, length = 1000 ,name="menu_path")
	String menuPath;
		
	
	@Column(nullable = true, length = 50 ,name="pgm_id")
	String pgmId;
	
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
	@OneToMany(mappedBy = "refCmMenuRoleCd")  
	List<CmMenuRoleCd> refCmMenuRoleCds = new ArrayList<CmMenuRoleCd>();
	
}
