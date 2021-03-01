package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCmExcelUpld is a Querydsl query type for CmExcelUpld
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmExcelUpld extends EntityPathBase<CmExcelUpld> {

    private static final long serialVersionUID = 446520880L;

    public static final QCmExcelUpld cmExcelUpld = new QCmExcelUpld("cmExcelUpld");

    public final StringPath col00 = createString("col00");

    public final StringPath col01 = createString("col01");

    public final StringPath col02 = createString("col02");

    public final StringPath col03 = createString("col03");

    public final StringPath col04 = createString("col04");

    public final StringPath col05 = createString("col05");

    public final StringPath col06 = createString("col06");

    public final StringPath col07 = createString("col07");

    public final StringPath col08 = createString("col08");

    public final StringPath col09 = createString("col09");

    public final StringPath col10 = createString("col10");

    public final StringPath col11 = createString("col11");

    public final StringPath col12 = createString("col12");

    public final StringPath col13 = createString("col13");

    public final StringPath col14 = createString("col14");

    public final StringPath col15 = createString("col15");

    public final StringPath col16 = createString("col16");

    public final StringPath col17 = createString("col17");

    public final StringPath col18 = createString("col18");

    public final StringPath col19 = createString("col19");

    public final StringPath col20 = createString("col20");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final NumberPath<Integer> excelSeq = createNumber("excelSeq", Integer.class);

    public final StringPath excelUpldId = createString("excelUpldId");

    public final StringPath gbn = createString("gbn");

    public QCmExcelUpld(String variable) {
        super(CmExcelUpld.class, forVariable(variable));
    }

    public QCmExcelUpld(Path<? extends CmExcelUpld> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmExcelUpld(PathMetadata metadata) {
        super(CmExcelUpld.class, metadata);
    }

}

