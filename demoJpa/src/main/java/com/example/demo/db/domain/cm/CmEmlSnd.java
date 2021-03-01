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
@NoArgsConstructor /*
					 * org.springframework.orm.jpa.JpaSystemException: No default constructor for
					 * entity:
					 */
@AllArgsConstructor /* https://tzara.tistory.com/73 */
@org.hibernate.annotations.DynamicUpdate /* 구분생성시 변경된 것만 한다. */
@org.hibernate.annotations.DynamicInsert /* 구분생성시 null인것은 보내지 않는다. */
@Data
@Entity
@Table(name = "tb_cm_eml_snd")
//https://gangnam-americano.tistory.com/25
public class CmEmlSnd {
	@Id
	@Column(nullable = false, name = "snd_seq")
	long sndSeq;


	/* 메일제목 */
	@Column(nullable = false,length = 4000, name = "ttl")
	String ttl;
	
	/* 메일 내용 */
	@Column(nullable = false,length = 4000, name = "cntnt")
	String cntnt;

	/* 보내는 사람 */
	@Column(nullable = true,length = 200, name = "sndr_nm")
	String sndrNm;

	/* 보내는 사람 주소 (전화번호, 카톡 id 등등) */
	@Column(nullable = false,length = 200, name = "sndr_addr")
	String sndrAddr;

	/* 메시지 상태 (B-발송전, A-발송) */
	@Column(nullable = false, length = 1, name = "snd_status_cd")
	String sndStatusCd;

	/* 발송타입 (P-예약, D-바로발송) */
	@Column(nullable = false, length = 1, name = "snd_type_cd")
	String sndTypeCd;

	/*발송시각, 예약 발송이 있을수 있다. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true, name = "snd_dtm")
	Date sndDtm;

	/*발송완료 시간  */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true, name = "snd_cmpl_dtm")
	Date sndCmplDtm;

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

	@Builder.Default
	@OneToMany(mappedBy = "orfCmEmlSnd")  
	List<CmEmlSndRcv> al_cmEmlSnd = new ArrayList<CmEmlSndRcv>();
}
