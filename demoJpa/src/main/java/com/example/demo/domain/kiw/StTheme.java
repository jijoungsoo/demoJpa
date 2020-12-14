package com.example.demo.domain.kiw;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import lombok.ToString;

@Builder
@NoArgsConstructor /*org.springframework.orm.jpa.JpaSystemException: No default constructor for entity:*/
@AllArgsConstructor /*https://tzara.tistory.com/73*/
@org.hibernate.annotations.DynamicUpdate/*구분생성시 변경된 것만 한다.*/
@org.hibernate.annotations.DynamicInsert/*구분생성시 null인것은 보내지 않는다.*/
@Data
@Entity
@ToString(exclude = "stThemeStocks")    /*무한루프 오류 때문에 필요*/
@Table(name="TB_ST_THEME")  /*키움 테마 */
public class StTheme {
	@Id
	@Column(nullable = false, length = 3 ,name="THEME_CD")
	String themeCd;

	@Column(nullable = false, length = 45 ,name="THEME_NM")
	String themeNm;
	
	@Column(nullable = true, name="ORD")
	int ord;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="CRT_DTM")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="UPDT_DTM")
	Date updtDtm;
	
	
	@Builder.Default  /*이걸 안붙여주면 Builter를 쎃을때 초기화가 안됨*/
	@OneToMany(mappedBy="stTheme" , cascade = CascadeType.ALL)
	List<StThemeStock> stThemeStocks = new ArrayList<StThemeStock>();
	
	
}
