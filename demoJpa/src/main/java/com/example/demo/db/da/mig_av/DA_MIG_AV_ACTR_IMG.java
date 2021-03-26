package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.domain.mig_av.MigAvActrImg;
import com.example.demo.db.domain.mig_av.QMigAvActrImg;
import com.example.demo.db.repository.mig_av.MigAvActrImgRepository;
import com.example.demo.exception.BizException;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_MIG_AV_ACTR_IMG {
	
	@Autowired
	JPAQueryFactory qf;
	
	
	@Autowired
	MigAvActrImgRepository migAvActrImgR;

	
	@Autowired
	DA_CM_SEQ daCmSeq;
	
	public void crtMigAvActrImg(Long L_ACTR_IDX
				,String IMG
				,String IMG_S
				,String IMG_L
				,String IMG_LS
			) throws BizException {
				long L_IMG_SEQ =daCmSeq.increate("MIG_AV_ACTR_IMG_SEQ");
				
				migAvActrImgR.save(
				MigAvActrImg.builder()
				.ImgSeq(L_IMG_SEQ)
				.actrIdx(L_ACTR_IDX)
				.img(IMG)
				.imgS(IMG_S)
				.imgL(IMG_L)
				.imgLs(IMG_LS)
				.crtDtm(new Date()).build());
	}

	public void updtMigAvActrImg(Long L_IMG_SEQ
		,String IMG
		,String IMG_S
		,String IMG_L
		,String IMG_LS
	) throws BizException {
		Optional<MigAvActrImg> c= this.findById(L_IMG_SEQ);
		if(c.isPresent()){
			MigAvActrImg m =c.get();
			m.setImg(IMG);
			m.setImgS(IMG_S);
			m.setImgL(IMG_L);
			m.setImgLs(IMG_LS);
			migAvActrImgR.save(m);
		}
	}

	public List<MigAvActrImg> findByImgS(String IMG_S){
		List<MigAvActrImg>  r=qf
		.selectFrom(QMigAvActrImg.migAvActrImg)
		.where(QMigAvActrImg.migAvActrImg.imgS.eq(IMG_S))
		.orderBy(QMigAvActrImg.migAvActrImg.imgS.desc())
		.fetch();

		return r;
	}
	
	public Optional<MigAvActrImg> findById(Long IMG_SEQ){
		return migAvActrImgR.findById(IMG_SEQ);
	}

	public List<MigAvActrImg> findByIdActorIdx(Long L_ACTOR_IDX){


		List<MigAvActrImg>  r=qf
		.selectFrom(QMigAvActrImg.migAvActrImg)
		.where(QMigAvActrImg.migAvActrImg.actrIdx.eq(L_ACTOR_IDX))
		.orderBy(QMigAvActrImg.migAvActrImg.img.desc())
		.fetch();
		return r;
	}
}

