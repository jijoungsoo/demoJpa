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
@Table(name="mig_av_actr")
public class MigAvActr {
	@Id
	@Column(nullable = false,unique=true ,name="actor_idx")
	long actrIdx;

	@Column(nullable = true,unique=false, length = 1000 ,name="img_s")
	String imgS;

	@Column(nullable = true,unique=false, length = 1000 ,name="img")
	String img;

	@Column(nullable = true,unique=false, length = 1000 ,name="img_ls")
	String imgLs;

	@Column(nullable = true,unique=false, length = 1000 ,name="img_l")
	String imgL;

	
	@Column(nullable = true,unique=false, length = 100 ,name="debut_dt")
	String debutDt;	
	
	@Column(nullable = true,unique=false, length = 100 ,name="name_kr")
	String nmKr;

	@Column(nullable = false,unique=false, length = 1 ,name="sync")
	String sync;

	/*위에 까지 배우 list에서 넣자. */


	
	@Column(nullable = true,unique=false, length = 100 ,name="name_en")
	String nmEn;

	@Column(nullable = true,unique=false, length = 100 ,name="name_cn")
	String nmCn;

	@Column(nullable = true,unique=false, length = 100 ,name="birth")
	String brth;
	
	@Column(nullable = true,unique=false, length = 100 ,name="height")
	String height;

	@Column(nullable = true,unique=false, length = 100 ,name="size")
	String size;

	@Column(nullable = true,unique=false, length = 100 ,name="bra_size")
	String brSize;

	@Column(nullable = true,unique=false, length = 1000 ,name="o_nm")
	String oNm; //다른이름

	@Column(nullable = true,unique=false, length = 4000 ,name="dscr_ttl")
	String dscrTtl;  //배우설명 포인트

	@Column(nullable = true,unique=false, length = 4000 ,name="dscr")
	String dscr;	 //배우설명

	@Column(nullable = true,unique=false, length = 100 ,name="size_b")
	String sizeB;	 //가슴(bust)

	@Column(nullable = true,unique=false, length = 100 ,name="size_w")
	String sizeW;	 //허리(Waist)

	@Column(nullable = true,unique=false, length = 100 ,name="size_h")
	String sizeH;	 //엉덩이(hip)

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updt_dtm")
	Date updtDtm;
}