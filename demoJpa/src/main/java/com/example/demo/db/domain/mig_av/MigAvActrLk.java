package com.example.demo.db.domain.mig_av;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@Table(name="mig_av_actr_lk")
public class MigAvActrLk {
	@Id
	@Column(nullable = false,unique=true ,name="lk_idx")
	long lkIdx;

	@Column(nullable = false,unique=false, name="actor_idx")
	long actrIdx;

	@Column(nullable = false,unique=false, name="usr_no")
	long usrNo;

	@Column(nullable = false,unique=false, name="lk_cnt")
	Integer lkCnt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;
}