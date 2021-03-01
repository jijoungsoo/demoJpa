package com.example.demo.db.domain.cm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_cm_brd_cmt")
//https://gangnam-americano.tistory.com/25
public class CmBoardCmt {
	@Column(nullable = false, name = "brd_seq")
	long brdSeq;

	@Id
	@Column(nullable = false, name = "cmt_seq")
	long cmtSeq;
	
	@Column(nullable = false, name = "root_cmt_seq")
	long rootCmtSeq;

	@Column(nullable = false, name = "cmt_rply_ord")
	long cmtRplyOrd;

	@Column(nullable = false, name = "cmt_dpth")
	long cmtDpth;

	@Column(nullable = false, name = "prnt_cmt_seq")
	long prntCmtSeq;

	@Column(nullable = false, length = 4000, name = "cntnt_text")
	String cntntText;

	@Column(nullable = false, length = 4000, name = "cntnt")
	String cntnt;

	@Column(nullable = false, name = "lk_cnt")
	long lkCnt;

	@Column(nullable = false, name = "dslk_cnt")
	long dislkCnt;
	
	@Column(nullable = false, name = "crt_usr_no")
	long crtUsrNo;

	@Column(nullable = false, length = 1, name = "del_yn")
	String delYn;
	
	@Column(nullable = false, name = "updt_usr_no")
	long updtUsrNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updt_dtm")
	Date updtDtm;

	@ManyToOne
	@JoinColumn(name="brd_seq", insertable=false, updatable=false)   //부모 테이블의 조인필드(기본키) 이름
	CmBoard orfCmBoard;
}
