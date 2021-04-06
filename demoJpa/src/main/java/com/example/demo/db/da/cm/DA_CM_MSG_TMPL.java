package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.cm.CmMsgTmpl;
import com.example.demo.db.domain.cm.QCmMsgTmpl;
import com.example.demo.db.repository.cm.CmMsgTmplRepository;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_MSG_TMPL {

	@Autowired
    PjtUtil pjtU;
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmMsgTmplRepository cmMsgTmplR;
	
	public List<CmMsgTmpl> findMsgTmpl(
		String MSG_TMPL_KIND_CD,
		String MSG_TMPL_STATUS_CD 
	) {
		JPAQuery<CmMsgTmpl>  c=  qf
		.selectFrom(QCmMsgTmpl.cmMsgTmpl)
		.orderBy(QCmMsgTmpl.cmMsgTmpl.tmplSeq.asc());

		if(!pjtU.isEmpty(MSG_TMPL_KIND_CD)){
			c= c.where(QCmMsgTmpl.cmMsgTmpl.msgTmplKindCd.eq(MSG_TMPL_KIND_CD));
		}
		if(!pjtU.isEmpty(MSG_TMPL_STATUS_CD)){
			c= c.where(QCmMsgTmpl.cmMsgTmpl.msgTmplStatusCd.eq(MSG_TMPL_STATUS_CD));
		}
		
		List<CmMsgTmpl> al =  c.fetch();
		 return al;
	}

	public void crtMsgTmpl(
			Long L_TMPL_SEQ
			,String MSG_TMPL_KIND_CD
			,String TTL
			,String MSG
			,String TMPL_PATH
			,String RMK
			,Long L_SESSION_USER_NO
			) {
				String MSG_TMPL_STATUS_CD = "T" ; //임시
				cmMsgTmplR.save(
					CmMsgTmpl.builder()
					.tmplSeq(L_TMPL_SEQ)
					.msgTmplKindCd(MSG_TMPL_KIND_CD)
					.msgTmplStatusCd(MSG_TMPL_STATUS_CD)
					.ttl(TTL)
					.msg(MSG)
					.tmplPath(TMPL_PATH)
					.rmk(RMK)
					.crtUsrNo(L_SESSION_USER_NO)
					.updtUsrNo(L_SESSION_USER_NO)
					.updtDtm(new Date())
					.crtDtm(new Date()).build());
	}

	public void updtMsgTmpl(
				Long L_TMPL_SEQ
				,String TTL
				,String MSG
				,String TMPL_PATH
				,String RMK
				,Long L_SESSION_USER_NO
			) throws BizException {
		
		Optional<CmMsgTmpl> c = cmMsgTmplR.findById(L_TMPL_SEQ);
		if(c==null) {
			throw new BizException("["+L_TMPL_SEQ+"] 템플릿SEQ가 존재하지 않습니다.[수정X]");
		}
		CmMsgTmpl tmp = c.get();
		tmp.setTtl(TTL);
		tmp.setMsg(MSG);
		tmp.setTmplPath(TMPL_PATH);
		tmp.setRmk(RMK);
		tmp.setUpdtUsrNo(L_SESSION_USER_NO);
		tmp.setUpdtDtm(new Date());
		cmMsgTmplR.save(tmp);
	}
	
	public void rmMsgTmpl(
			long L_TMPL_SEQ
			) {
				cmMsgTmplR.deleteById(L_TMPL_SEQ);
	}

	public List<CmMsgTmpl> findMsgTmplByTmplSeq(Long L_TMPL_SEQ) {
		List<CmMsgTmpl> al =  qf
		.selectFrom(QCmMsgTmpl.cmMsgTmpl)
		.where(QCmMsgTmpl.cmMsgTmpl.tmplSeq.eq(L_TMPL_SEQ))
		.orderBy(QCmMsgTmpl.cmMsgTmpl.tmplSeq.asc())
		.fetch();
		return al;
	}

	public void chgMsgTmpl(Long L_TMPL_SEQ, String MSG_TMPL_STATUS_CD, Long L_SESSION_USER_NO) throws BizException {
		Optional<CmMsgTmpl> c = cmMsgTmplR.findById(L_TMPL_SEQ);
		if(c==null) {
			throw new BizException("["+L_TMPL_SEQ+"] 템플릿SEQ가 존재하지 않습니다.[수정X]");
		}
		CmMsgTmpl tmp = c.get();
		tmp.setMsgTmplStatusCd(MSG_TMPL_STATUS_CD);
		tmp.setUpdtUsrNo(L_SESSION_USER_NO);
		tmp.setUpdtDtm(new Date());
		cmMsgTmplR.save(tmp);
	}
}
