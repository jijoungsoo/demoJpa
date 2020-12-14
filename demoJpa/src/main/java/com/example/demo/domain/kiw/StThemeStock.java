package com.example.demo.domain.kiw;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@ToString(exclude = "stTheme")    /*무한루프 오류 때문에 필요*/
@Table(name="TB_ST_THEME_STOCK")  /*키움 테마 코드 */
@IdClass(StThemeStockId.class)  

public class StThemeStock {
	@Id
	@Column(nullable = false, length = 3 ,name="THEME_CD")
	String themeCd;
		
	@Id
	@Column(nullable = false, length = 9 ,name="STOCK_CD")
	String stockCd;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="CRT_DTM")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="UPDT_DTM")
	Date updtDtm;
	  
	@ManyToOne
	@JoinColumn(name="THEME_CD", insertable=false, updatable=false)   /*외래키*/
	StTheme stTheme;
	
	public void setThemeCd(StTheme t) {
		this.stTheme =t;
		if(!t.getStThemeStocks().contains(this)) {
			t.getStThemeStocks().add(this);
		}
	}
}
