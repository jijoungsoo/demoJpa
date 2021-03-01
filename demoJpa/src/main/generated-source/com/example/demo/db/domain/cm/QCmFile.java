package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCmFile is a Querydsl query type for CmFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmFile extends EntityPathBase<CmFile> {

    private static final long serialVersionUID = -310005706L;

    public static final QCmFile cmFile = new QCmFile("cmFile");

    public final StringPath contentType = createString("contentType");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath ext = createString("ext");

    public final StringPath fileGroup = createString("fileGroup");

    public final StringPath fileId = createString("fileId");

    public final NumberPath<Long> fileNo = createNumber("fileNo", Long.class);

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final StringPath fileStatusCd = createString("fileStatusCd");

    public final StringPath orgFileNm = createString("orgFileNm");

    public final StringPath svrDirPath = createString("svrDirPath");

    public final StringPath svrFileNm = createString("svrFileNm");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QCmFile(String variable) {
        super(CmFile.class, forVariable(variable));
    }

    public QCmFile(Path<? extends CmFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmFile(PathMetadata metadata) {
        super(CmFile.class, metadata);
    }

}

