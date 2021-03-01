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
@Table(name = "tb_cm_oauth_log")

public class CmOauthLog {

	@Id
	@Column(nullable = false, name = "seq_no")  
	Long seqNo;

	/* 
		registration_id
		kakao
		google
		이런 구분 값
		인증에서 넘겨줌
	*/
	@Column(nullable = false, name = "gbn_id")  
	String gbnId;

	/*	각 업체에서 키가 되는 필드의 이름	
	@Column(nullable = false, name = "user_name_attribute_name")  
	String userNameAttributeName;*/	

	/*카카오는 id  => Long */

	@Column(nullable = false, name = "auth_id")
	String authId;

	/*사용자 프로퍼티(Property)===>  사용자가 해당 서비스에서 설정한 닉네임 기본 값은 카카오계정 닉네임   */
	@Column(nullable = true,length = 100, name = "ncknm")
	String ncknm;

	/*사용자 프로퍼티(Property)===>  프로필 이미지 URL, 640px * 640px 또는 480px * 480px  */
	@Column(nullable = true,length = 4000, name = "prf_img")
	String prfImg;

	/*사용자 프로퍼티(Property)===>  프로필 미리보기 이미지 URL, 110px * 110px 또는 100px * 100px */
	@Column(nullable = true,length = 4000, name = "thumb_img")
	String thmbImg;

	/*카카오계정 정보(kakao_account)===>  카카오계정 대표 이메일 */
	@Column(nullable = true, length = 500, name = "eml")
	String email;
	
	/*카카오계정 정보(kakao_account)===>  생일, MMDD 형식 */
	@Column(nullable = true, length = 12, name = "brthday")
	String brthday;

	/*카카오계정 정보(kakao_account)===>  성별, female/male */
	@Column(nullable = true, length = 4, name = "gndr")
	String gndr;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;
}
