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
@NoArgsConstructor /*
					 * org.springframework.orm.jpa.JpaSystemException: No default constructor for
					 * entity:
					 */
@AllArgsConstructor /* https://tzara.tistory.com/73 */
@org.hibernate.annotations.DynamicUpdate /* 구분생성시 변경된 것만 한다. */
@org.hibernate.annotations.DynamicInsert /* 구분생성시 null인것은 보내지 않는다. */
@Data
@Entity
@Table(name = "tb_cm_msg_tmpl")
//https://gangnam-americano.tistory.com/25
public class CmMsgTmpl {
	@Id
	@Column(nullable = false, name = "tmpl_seq")
	long tmplSeq;
	
	/*  EMAIL , SMS, KAKAO, NAVER  */
	@Column(nullable = false,length = 10, name = "msg_tmpl_kind_cd")
	String msgTmplKindCd;

	/* 템플릿 제목 */
	@Column(nullable = false,length = 4000, name = "ttl")
	String ttl;
	
	/* 템플릿 메시지*/
	@Column(nullable = false,length = 4000, name = "msg")
	String msg;

	/* 템플릿 상태 (T-임시, U-사용,D-삭제) */
	@Column(nullable = false, length = 1, name = "msg_tmpl_status_cd")
	String msgTmplStatusCd;

	/* 특정파일경로 존재하는 html 파일 */
	@Column(nullable = false, length = 100, name = "tmpl_path")
	String tmplPath;

	/* 비고 */
	@Column(nullable = false, length = 4000, name = "rmk")
	String rmk;


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
