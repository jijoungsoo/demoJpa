package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCmSeq is a Querydsl query type for CmSeq
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmSeq extends EntityPathBase<CmSeq> {

    private static final long serialVersionUID = -1811103131L;

    public static final QCmSeq cmSeq = new QCmSeq("cmSeq");

    public final NumberPath<Integer> allocationSize = createNumber("allocationSize", Integer.class);

    public final StringPath colNm = createString("colNm");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final NumberPath<Integer> initVal = createNumber("initVal", Integer.class);

    public final StringPath seqNm = createString("seqNm");

    public final NumberPath<Long> seqNo = createNumber("seqNo", Long.class);

    public final StringPath tbNm = createString("tbNm");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QCmSeq(String variable) {
        super(CmSeq.class, forVariable(variable));
    }

    public QCmSeq(Path<? extends CmSeq> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmSeq(PathMetadata metadata) {
        super(CmSeq.class, metadata);
    }

}

