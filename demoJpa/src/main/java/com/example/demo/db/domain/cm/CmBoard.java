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
@Table(name = "tb_cm_brd")
//https://gangnam-americano.tistory.com/25
public class CmBoard {
	@Column(nullable = false,  name = "grp_seq")
	long grpSeq;

	@Id
	@Column(nullable = false, name = "brd_seq")
	long brdSeq;

	@Column(nullable = false, name = "prnt_brd_seq")
	long prntBrdSeq;	

	@Column(nullable = false, name = "root_brd_seq")
	long rootBrdSeq;

	@Column(nullable = false, name = "brd_rply_ord")
	long brdRplyOrd;

	@Column(nullable = false, name = "brd_dpth")
	long brdDpth;

	@Column(nullable = true, length = 4000, name = "ttl")
	String ttl;

	@Column(nullable = true, length = 4000, name = "ttl_text")
	String ttlText;

	@Column(nullable = false, length = 4000, name = "cntnt")
	String cntnt;

	@Column(nullable = false, length = 4000, name = "cntnt_text")
	String cntntText;

	@Column(nullable = false, name = "lk_cnt")
	long lkCnt;

	@Column(nullable = false, name = "dslk_cnt")
	long dislkCnt;

	@Column(nullable = false, length = 1, name = "del_yn")
	String delYn;
	
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
	@JoinColumn(name="grp_seq", insertable=false, updatable=false)   //부모 테이블의 조인필드(기본키) 이름
	CmBoardGroup orfCmBoardGroup;

	@Builder.Default
	@OneToMany(mappedBy = "orfCmBoard")  
	List<CmBoardCmt> al_cmBoardCmt = new ArrayList<CmBoardCmt>();
}
