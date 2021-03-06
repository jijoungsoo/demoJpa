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
	@Column(nullable = false,name="file_no")
	Long fileNo;
	
	@Id
	@Column(nullable = false, length = 1000 ,name="file_id")
	String fileId;
	@Column(nullable = false, length = 1000 ,name="file_group")
	String fileGroup;
	
	@Column(nullable = false, length = 1000 ,name="org_file_nm")
	String orgFileNm;
	@Column(nullable = true, length = 10 ,name="ext")
	String ext;
	@Column(nullable = false, length = 4000 ,name="svr_file_nm")
	String svrFileNm;
	
	@Column(nullable = false, length = 4000 ,name="svr_dir_path")
	String svrDirPath;
	
	@Column(nullable = false, length = 1 ,name="fie_status_cd")
	String fileStatusCd;
	
	@Column(nullable = true, length = 1000 ,name="content_type")
	String contentType;
	
	@Column(nullable = true, name="file_size")
	Long fileSize;
		
	@Column(nullable = false, name = "crt_usr_no")
	long crtUsrNo;
	
	@Column(nullable = false, name = "updt_usr_no")
	long updtUsrNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updt_dtm")
	Date updtDtm;
}
