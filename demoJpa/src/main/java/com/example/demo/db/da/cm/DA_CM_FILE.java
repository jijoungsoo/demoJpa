package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmFile;
import com.example.demo.db.domain.cm.QCmFile;
import com.example.demo.db.repository.cm.CmFileRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_CM_FILE {
	
	@Autowired
	JPAQueryFactory qf;
	

	
	@Autowired
	CmFileRepository cmFileR;
	
	public List<CmFile> findFile(String fileId) {
		List<CmFile> al =  qf
	                .selectFrom(QCmFile.cmFile)
	                .where(QCmFile.cmFile.fileId.eq(fileId))
	                .orderBy(QCmFile.cmFile.crtDtm.asc())
	                .fetch();
		 return al;
	}

	public void createFile(
			String FILE_ID,
			String FILE_GROUP,
			String ORG_FILE_NM, /*H헤더  ,D 데테일*/
			String EXT,
			String SVR_FILE_NM,
			String SVR_DIR_PATH,
			String FILE_STATUS_CD,
			Long L_CRT_USR_NO
			) {
		cmFileR.save(
				CmFile.builder()
				.fileId(FILE_ID)
				.fileGroup(FILE_GROUP)
				.orgFileNm(ORG_FILE_NM)
				.ext(EXT)
				.svrFileNm(SVR_FILE_NM)
				.svrDirPath(SVR_DIR_PATH)
				.fileStatusCd(FILE_STATUS_CD)
				.crtUsrNo(L_CRT_USR_NO)
				.crtDtm(new Date()).build());
		
		
	}
	
}
