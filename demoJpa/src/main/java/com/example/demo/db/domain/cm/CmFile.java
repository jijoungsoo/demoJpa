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
@Table(name="tb_cm_file")
public class CmFile {
	@Id
	@Column(nullable = false, length = 200 ,name="file_id")
	String fileId;
	@Column(nullable = false, length = 200 ,name="file_group")
	String fileGroup;
	
	@Column(nullable = false, length = 200 ,name="org_file_nm")
	String orgFileNm;
	@Column(nullable = true, length = 10 ,name="ext")
	String ext;
	@Column(nullable = false, length = 4000 ,name="svr_file_nm")
	String svrFileNm;
	
	@Column(nullable = false, length = 4000 ,name="svr_dir_path")
	String svrDirPath;
	
	@Column(nullable = false, length = 1 ,name="fie_status_cd")
	String fileStatusCd;
		
	@Column(nullable = false, name = "CRT_USR_NO")
	long crtUsrNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="crt_dtm")
	Date crtDtm;
}
