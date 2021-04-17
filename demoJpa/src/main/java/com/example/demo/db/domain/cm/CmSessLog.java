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

@Data
@Builder
@NoArgsConstructor /*org.springframework.orm.jpa.JpaSystemException: No default constructor for entity:*/
@AllArgsConstructor /*https://tzara.tistory.com/73*/
@org.hibernate.annotations.DynamicUpdate/*구분생성시 변경된 것만 한다.*/
@org.hibernate.annotations.DynamicInsert/*구분생성시 null인것은 보내지 않는다.*/
@Entity
@Table(name="tb_cm_sess_log")
public class CmSessLog {
	@Id
	@Column(nullable = false,unique=true,name="sess_log_seq")
	long sessLogSeq;

	@Column(nullable = false,unique=false,name="user_no")
	long userNo;
	
	@Column(nullable = false,unique=false, length = 50 ,name="user_id")
	String userId;

	@Column(nullable = false,unique=false, length = 10 ,name="log_type")
	String logType;

	@Column(nullable = true,unique=false, length = 100 ,name="ipaddr")
	String ipaddr;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="crt_dtm")
	Date crtDtm;
}
