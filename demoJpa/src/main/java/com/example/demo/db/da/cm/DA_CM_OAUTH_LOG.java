package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;

import com.example.demo.db.domain.cm.CmCd;
import com.example.demo.db.domain.cm.CmOauthLog;
import com.example.demo.db.domain.cm.QCmCd;
import com.example.demo.db.domain.cm.QCmOauthLog;
import com.example.demo.db.repository.cm.CmOauthLogRepository;
import com.example.demo.utils.PjtUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class DA_CM_OAUTH_LOG {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmOauthLogRepository cmOauthLogR;

	public List<CmOauthLog> findCmOauthLogCmCd(String GRP_CD,String USE_YN) {
		List<CmOauthLog> al =qf.selectFrom(QCmOauthLog.cmOauthLog)
								.orderBy(QCmOauthLog.cmOauthLog.seqNo.asc())
								.fetch();
		return al;
	}
	
	public void createCmOauthLog(
			 Long L_SEQ_NO
			,String GBN_ID
			,String AUTH_ID
			,String NCK_NM
			,String PRF_IMG
			,String THMB_IMG
			,String EMAIL
			,String BRTHDAY
			,String GNDR
	) {
		cmOauthLogR.save(
			CmOauthLog.builder()
			.seqNo(L_SEQ_NO)
		.gbnId(AUTH_ID)
		.authId(AUTH_ID)
		.ncknm(NCK_NM)
		.prfImg(PRF_IMG)
		.thmbImg(THMB_IMG)
		.email(EMAIL)
		.brthday(BRTHDAY)
		.gndr(GNDR)
		.crtDtm(new Date()).build());
	}
}
