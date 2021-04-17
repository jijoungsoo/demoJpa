package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;

import com.example.demo.db.domain.cm.CmSessLog;
import com.example.demo.db.repository.cm.CmSessLogRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_SESS_LOG {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmSessLogRepository cmSessLogR;
	
	public List<CmSessLog> findDomain() {
		 return null;
	}	
	/**
	 * @param USER_ID
	 * @param USER_NO
	 */
	public void crt(
			long SESS_LOG_SEQ
			,long USER_NO
			,String USER_ID			
			,String LOG_TYPE
			,String IPADDR
			) {
		
				cmSessLogR.save(
					CmSessLog.builder()
				.sessLogSeq(SESS_LOG_SEQ)
				.userId(USER_ID)
				.userNo(USER_NO)
				.logType(LOG_TYPE)
				.ipaddr(IPADDR)
				.crtDtm(new Date()).build());
	}	
}
