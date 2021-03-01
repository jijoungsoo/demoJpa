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
@NoArgsConstructor /*
					 * org.springframework.orm.jpa.JpaSystemException: No default constructor for
					 * entity:
					 */
@AllArgsConstructor /* https://tzara.tistory.com/73 */
@org.hibernate.annotations.DynamicUpdate /* 구분생성시 변경된 것만 한다. */
@org.hibernate.annotations.DynamicInsert /* 구분생성시 null인것은 보내지 않는다. */
@Data
@Entity
@IdClass(CmEmlSndSeqRcvSeq.class)  
@Table(name = "tb_cm_eml_snd_rcv")
//https://gangnam-americano.tistory.com/25
public class CmEmlSndRcv {
	@Id
	@Column(nullable = false, name = "snd_seq")
	long sndSeq;

	@Id
	@Column(nullable = false, name = "rcv_seq")
	long rcvSeq;

	/* 받는 사람 */
	@Column(nullable = true ,length = 200, name = "rcv_nm")
	String rcvNm;

	/* 받는 사람 주소 (전화번호, 카톡 id 등등) */
	@Column(nullable = false,length = 200, name = "rcv_addr")
	String rcvAddr;

	/* 메시지 수신 상태 (S-발송 , C 완료) */
	@Column(nullable = false, length = 1, name = "rcv_status_cd")
	String rcvStatusCd;

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

	@ManyToOne
	@JoinColumn(name="snd_seq", insertable=false, updatable=false)   //부모 테이블의 조인필드(기본키) 이름
	CmEmlSnd orfCmEmlSnd;

	public void setAl_cmEmlSnd(CmEmlSnd t) {
		this.orfCmEmlSnd =t;
		if(!t.getAl_cmEmlSnd().contains(this)) {
			t.getAl_cmEmlSnd().add(this);
		}
	}
}
