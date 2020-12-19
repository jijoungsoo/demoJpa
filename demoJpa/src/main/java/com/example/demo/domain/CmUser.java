package com.example.demo.domain;

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
@Table(name = "TB_CM_USER")
public class CmUser {
	@Id
	@Column(nullable = false, name = "USER_NO")
	long userNo;

	@Column(nullable = false, length = 100, name = "USER_NM")
	String userNm;

	@Column(nullable = false, length = 100, name = "USER_ID")
	String userId;

	@Column(nullable = false, length = 100, name = "USER_PWD")
	String userPwd;

	@Column(nullable = true, length = 100, name = "EMAIL")
	String email;

	@Column(nullable = false, length = 1, name = "USE_YN")
	String useYn;

	@Column(nullable = true, length = 4000, name = "RMK")
	String rmk;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "LST_ACC_DTM")
	Date lstAccDtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "CRT_DTM")
	Date crtDtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "UPDT_DTM")
	Date updtDtm;
}
