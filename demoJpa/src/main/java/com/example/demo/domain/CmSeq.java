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
@NoArgsConstructor /*org.springframework.orm.jpa.JpaSystemException: No default constructor for entity:*/
@AllArgsConstructor /*https://tzara.tistory.com/73*/
@org.hibernate.annotations.DynamicUpdate/*구분생성시 변경된 것만 한다.*/
@org.hibernate.annotations.DynamicInsert/*구분생성시 null인것은 보내지 않는다.*/
@Data
@Entity
@Table(name="TB_CM_SEQ")
public class CmSeq {
	@Id
	@Column(nullable = false, length = 50 ,name="SEQ_NM")
	String seqNm;	//시퀀스 컬러명	   ==> sequence_name	
	
	@Column(nullable = false, name="SEQ_NO")
	long seqNo;	//시퀀스 값 컬럼명  ==> next_val
	
	@Column(nullable = false,length = 50, name="TB_NM")
	String tbNm;	//테이블명
	
	@Column(nullable = false,length = 50, name="COL_NM")
	String colNm;	//테이블 컬럼명
	
	@Column(name="INIT_VAL")
	int initVal;  //초기값,마지막으로 생성된 값이 기준 ==>0
	
	@Column(name="ALLOCATION_SIZE")
	int allocationSize;  //시퀀스 한번 호출에 증가하는 수  50   
	//50개를 메모리에 해두고 나중에   메모리에서 할당 /// insert 가 중요하지 않으면 1로 하면 된다.  
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="CRT_DTM")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="UPDT_DTM")
	Date updtDtm;
}
