package com.example.demo.db.domain.mig_av;

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
@Table(name="mig_av_mv")
public class MigAvMv {
	@Id
	@Column(nullable = false,unique=true ,name="dvd_idx")
	long dvdIdx;

	//일반이미지   			https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_a.jpg
	@Column(nullable = true,unique=false, length = 1000 ,name="img_a")
	String imgA;

	//작은일반이미지   		https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_as.jpg
	@Column(nullable = true,unique=false, length = 1000 ,name="img_as")
	String imgAs;

	//모자이크이미지   		https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_n.jpg
	@Column(nullable = true,unique=false, length = 1000 ,name="img_n")
	String imgN;

	//작은모자이크이미지	https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_ns.jpg
	@Column(nullable = true,unique=false, length = 1000 ,name="img_ns")
	String imgNs;


	//일반이미지   			https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_a.jpg
	@Column(nullable = true,unique=false, length = 1000 ,name="img_la")
	String imgLA;

	//작은일반이미지   		https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_as.jpg
	@Column(nullable = true,unique=false, length = 1000 ,name="img_las")
	String imgLAs;

	//모자이크이미지   		https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_n.jpg
	@Column(nullable = true,unique=false, length = 1000 ,name="img_ln")
	String imgLN;

	//작은모자이크이미지	https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_ns.jpg
	@Column(nullable = true,unique=false, length = 1000 ,name="img_lns")
	String imgLNs;



	@Column(nullable = true,unique=false, length = 1000 ,name="mv_nm")
	String mvNm;
	
	@Column(nullable = true,unique=false, length = 4000 ,name="title_kr")
	String ttlKr;

	@Column(nullable = true,unique=false, length = 1000 ,name="main_actor_nm")
	String mnActrNm;


	@Column(nullable = true,unique=false, name="main_actor_idx")
	long mnActrIdx;

	@Column(nullable = true,unique=false, length = 4000 ,name="actr_nm")
	String actrNm;
	
	@Column(nullable = true,unique=false, length = 100 ,name="open_dt")
	String openDt; //출시

	@Column(nullable = true,unique=false, length = 1000 ,name="comp_nm")
	String cmpNm;

	@Column(nullable = true,unique=false, length = 1000 ,name="label")
	String lbl;

	@Column(nullable = false,unique=false, length = 1 ,name="sync")
	String sync;

	@Column(nullable = false,unique=false, length = 1 ,name="best_yn")
	String bestYn;

	@Column(nullable = true,unique=false, length = 1 ,name="sample_yn")
	String sampleYn;
	
	@Column(nullable = true,unique=false, length = 1000 ,name="series")
	String series;

	@Column(nullable = true,unique=false, length = 100 ,name="director")
	String drctr;


	@Column(nullable = true,unique=false, length = 100 ,name="run_time")
	String rnTm;
	
	@Column(nullable = true,unique=false, length = 4000 ,name="story_kr")
	String stryKr;

	@Column(nullable = true,unique=false, length = 4000 ,name="gen_list")
	String genLst;

	@Column(nullable = true,unique=false, length = 4000 ,name="file_path")
	String filePath;

	@Column(nullable = true,unique=false, length = 4000 ,name="rmk")
	String rmk;

	@Column(nullable = true,unique=false, length = 1 ,name="del_yn")
	String delYn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updt_dtm")
	Date updtDtm;
	
}