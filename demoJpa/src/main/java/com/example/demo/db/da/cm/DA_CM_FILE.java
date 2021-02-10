package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmFile;
import com.example.demo.db.domain.cm.QCmFile;
import com.example.demo.db.domain.stck.StBuy;
import com.example.demo.db.repository.cm.CmFileRepository;
import com.example.demo.exception.BizException;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_CM_FILE {
	
	@Autowired
	JPAQueryFactory qf;
	

	
	@Autowired
	CmFileRepository cmFileR;
	
	public List<CmFile> findFileList() {
		List<CmFile> al =  qf
	                .selectFrom(QCmFile.cmFile)
	                .orderBy(QCmFile.cmFile.crtDtm.desc())
	                .fetch();
		 return al;
	}
		
	public List<CmFile> findFileListByFileNo(Long L_FILE_NO) {
		List<CmFile> al =  qf
	                .selectFrom(QCmFile.cmFile)
	                .where(QCmFile.cmFile.fileNo.eq(L_FILE_NO))
	                .orderBy(QCmFile.cmFile.crtDtm.desc())
	                .fetch();
		 return al;
	}

	public void createFile(
			Long L_FILE_NO,
			String FILE_ID,
			String FILE_GROUP,
			String ORG_FILE_NM, /*H헤더  ,D 데테일*/
			String EXT,
			String SVR_FILE_NM,
			String SVR_DIR_PATH,
			String FILE_STATUS_CD,
			Long FILE_SIZE,
			String CONTENT_TYPE,
			Long L_CRT_USR_NO
			) {
		cmFileR.save(
				CmFile.builder()
				.fileNo(L_FILE_NO)
				.fileId(FILE_ID)
				.fileGroup(FILE_GROUP)
				.orgFileNm(ORG_FILE_NM)
				.ext(EXT)
				.svrFileNm(SVR_FILE_NM)
				.svrDirPath(SVR_DIR_PATH)
				.fileStatusCd(FILE_STATUS_CD)
				.fileSize(FILE_SIZE)
				.contentType(CONTENT_TYPE)
				.crtUsrNo(L_CRT_USR_NO)
				.updtUsrNo(L_CRT_USR_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	public void rmFile(
			String FILE_ID,
			String SVR_DIR_PATH,
			Long L_CRT_USR_NO
			) throws BizException {
		Optional<CmFile>  c = cmFileR.findById(FILE_ID);
		if(c==null) {
			throw new BizException("["+FILE_ID+"] 파일이 존재하지 않는다.[삭제X]");
		}
		CmFile cmFile = c.get();
		
		if(cmFile.getFileStatusCd().equals("D")) {
			throw new BizException("["+FILE_ID+"] 파일이 이미 삭제되었다.[삭제X]");
		}
		cmFile.setFileStatusCd("D");
		cmFile.setSvrDirPath(SVR_DIR_PATH);
		cmFile.setUpdtUsrNo(L_CRT_USR_NO);
		cmFile.setUpdtDtm(new Date());
		cmFileR.save(cmFile);
	}

	public Optional<CmFile>  findFileListByFileId(String fILE_ID) {
		return  cmFileR.findById(fILE_ID);
	
	}
	
}
