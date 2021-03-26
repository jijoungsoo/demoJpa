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
@Table(name="mig_av_mk")
public class MigAvMk {
	@Id
	@Column(nullable = false,unique=true ,name="mk_id")
	long mkId;

	@Column(nullable = true,unique=false, length = 100 ,name="nm")
	String nm;

	@Column(nullable = true,unique=false, length = 1000 ,name="img")
	String img;

	@Column(nullable = true,unique=false, length = 1000 ,name="img_l")
	String imgL;

	@Column(nullable = true,unique=false, length = 2000 ,name="ttl")
	String ttl;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updt_dtm")
	Date updtDtm;
	
}