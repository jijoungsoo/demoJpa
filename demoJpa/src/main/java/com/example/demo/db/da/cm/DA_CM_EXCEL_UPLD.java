package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmExcelUpld;
import com.example.demo.db.domain.cm.QCmExcelUpld;
import com.example.demo.db.repository.cm.CmExcelFileUpldRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_CM_EXCEL_UPLD {
	
	@Autowired
	JPAQueryFactory qf;
	

	
	@Autowired
	CmExcelFileUpldRepository cmExcelUpldR;
	
	public List<CmExcelUpld> findExcelUpld(String excelUpldSeq) {
		List<CmExcelUpld> al =  qf
	                .selectFrom(QCmExcelUpld.cmExcelUpld)
	                .orderBy(QCmExcelUpld.cmExcelUpld.excelSeq.asc())
	                .fetch();
	                
		 return al;
	}

	public void createExcelUpld(
			String excelUpldId,
			int excelSeq,
			String gbn, /*H헤더  ,D 데테일*/
			String col00,
			String col01,
			String col02,
			String col03,
			String col04,
			String col05,
			String col06,
			String col07,
			String col08,
			String col09,
			String col10,
			String col11,
			String col12,
			String col13,
			String col14,
			String col15,
			String col16,
			String col17,
			String col18,
			String col19,
			String col20,
			Long L_crtUsrNo
			) {
		cmExcelUpldR.save(
				CmExcelUpld.builder()
				.excelUpldId(excelUpldId)
				.excelSeq(excelSeq)
				.gbn(gbn)
				.col00(col00)
				.col01(col01)
				.col02(col02)
				.col03(col03)
				.col04(col04)
				.col05(col05)
				.col06(col06)
				.col07(col07)
				.col08(col08)
				.col09(col09)
				.col10(col10)
				.col11(col11)
				.col12(col12)
				.col13(col13)
				.col14(col14)
				.col15(col15)
				.col16(col16)
				.col17(col17)
				.col18(col18)
				.col19(col19)
				.col20(col20)
				.crtUsrNo(L_crtUsrNo)
				.crtDtm(new Date()).build());
	}
	
}
