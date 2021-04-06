package com.example.demo.br.cm.cm_role_cd;

import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_MENU_ROLE_CD;
import com.example.demo.db.da.cm.DA_CM_MENU_ROLE_CD_MAPPER;
import com.example.demo.db.domain.cm.CmMenuRoleCd;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "CM_ROLE_CD", description = "역할코드")
@Slf4j
@OpService
@Service
public class BR_CM_ROLE_CD_MENU_SAVE {
	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_ROLE_CD_MENU_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_ROLE_CD,IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_ROLE_CD")
		@Schema(name="IN_DATA-BR_CM_ROLE_CD_MENU_SAVE", description = "입력 데이터")
		ArrayList<IN_ROLE_CD_ROW> IN_ROLE_CD = new ArrayList<IN_ROLE_CD_ROW>();

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_ROLE_CD_MENU_SAVE", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_ROLE_CD_ROW-BR_CM_ROLE_CD_MENU_SAVE")
	@Data
	static class IN_ROLE_CD_ROW {
		@JsonProperty("ROLE_CD")
		@Schema(name = "ROLE_CD", example = "ADMIN", description = "역할코드")
		String ROLE_CD = null;
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_ROLE_CD_MENU_SAVE")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("ROLE_CD")
		@Schema(name = "ROLE_CD", example = "ADMIN", description = "역할코드")
		String ROLE_CD = null;
		
		@JsonProperty("MENU_NO")
		@Schema(name = "MENU_NO", example = "1", description = "메뉴번호")
		String MENU_NO = null;

		@JsonProperty("ROLE_YN")
		@Schema(name = "ROLE_YN", example = "Y,N", description = "역할여부")
		String ROLE_YN = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_ROLE_CD_MENU_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_ROLE_CD_SAVE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_MENU_ROLE_CD daCmMenuRoleCd;

	@Autowired
	DA_CM_MENU_ROLE_CD_MAPPER daMenuRoleCdM;

	@Autowired
	EntityManager em;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_ROLE_CD"},value = "메뉴 역할코드를 저장한다.", notes = "")
	@Transactional
	//@PostMapping(path= "/api/BR_CM_ROLE_CD_MENU_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws Exception {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(pjtU.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}

		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);

		if(inDS.IN_ROLE_CD==null) {
			throw new BizRuntimeException("IN_ROLE_CD 값이 넘어오지 않았습니다.");
		}

		if(inDS.IN_ROLE_CD.size()!=1) {
			throw new BizRuntimeException("IN_ROLE_CD 값이 하나 넘어와야 합니다.");
		}

		IN_ROLE_CD_ROW  rs =inDS.IN_ROLE_CD.get(0);
		if(rs.ROLE_CD==null){
			throw new BizRuntimeException("IN_ROLE_CD-ROLE_CD는 null이면 안되요.");
		}
		if(pjtU.isEmpty(rs.ROLE_CD)==true){
			throw new BizRuntimeException("IN_ROLE_CD-ROLE_CD는 빈 값이면 안되요.");
		}
		String ROLE_CD = rs.ROLE_CD;

		/*1번 전달된 ROLE_CD를 가지고 있는 tb_cm_menu_role_cd를 모두 지운다. 
			2번 전달된 ROLE_CD를 가지고 생성한다.  ROLE_YN이 Y인것만  생성하면 된다.
			3번  입력된 ROLE_CD를 가지고 부모 menu들을 바꾼다. 
		*/
		/*[1]*/	
		daCmMenuRoleCd.rmCmMenuRoleCdAll(ROLE_CD);		

		

		/*
		IN_DATA에서 넘어온 ROLE_CD는 IN_ROLE_CD에서 넘어온  ROLE_CD와 같아야한다.
		*/
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			IN_DATA_ROW  rs2 =inDS.IN_DATA.get(i);
			String  MENU_NO 	= pjtU.str(rs2.MENU_NO);
			String  ROLE_YN 	= pjtU.str(rs2.ROLE_YN);
			if(rs2.ROLE_CD==null){
				throw new BizRuntimeException("IN_DATA-ROLE_CD는 null이면 안되요.");
			}
			if(pjtU.isEmpty(rs2.ROLE_CD)==true){
				throw new BizRuntimeException("IN_DATA-ROLE_CD는 빈 값이면 안되요.");
			}
			if(!pjtU.str(rs2.ROLE_CD).equals(ROLE_CD)){
				throw new BizRuntimeException("IN_DATA-ROLE_CD는 IN_ROLE_CD-ROLE_CD와 같아야한다.");
			}
			
			if(pjtU.isEmpty(MENU_NO)) {
				throw new BizRuntimeException("메뉴번호가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(ROLE_YN)) {
				throw new BizRuntimeException("역할 여부가 입력되지 않았습니다.");
			}
	
			Long L_MENU_NO = Long.parseLong(MENU_NO);
			
			if(ROLE_YN.equals("Y")){
				Optional<CmMenuRoleCd> c=daCmMenuRoleCd.findCmMenuRoleCd(ROLE_CD,L_MENU_NO);
				if(c.isEmpty()) {
					daCmMenuRoleCd.createCmMenuRoleCd(
						ROLE_CD
							,L_MENU_NO
							,L_SESSION_USER_NO
							);
				}
			} 			
		}
		/*[3]*/
		/*문제 발생 -- 실제로 실행되었는데도 불구하고 데이터를 못가져온다.
					   시점에 따른 문제로 생각된다.
					   //select 하는걸 하나 만들어서 실제로 가져올수있는지 보자.
					   //가져올수없었다.
					   em.flush()를 호출하니까. 실제로 가져올수있엇다.
		*/
		em.flush();
		/*
			여기서 에러를 일부러 내서 rollback이 되는지 보자.
		*/
		/*
		List<Map> al = daMenuRoleCdM.findMenuRoleCdParent(ROLE_CD);
		System.out.println("aaaaa");
		System.out.println(al.size());
		*/
		daMenuRoleCdM.reSyncMenuRoleCdParent(ROLE_CD, L_SESSION_USER_NO);
				
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
}
