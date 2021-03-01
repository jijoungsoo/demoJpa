package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmMsgSndRcv is a Querydsl query type for CmMsgSndRcv
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmMsgSndRcv extends EntityPathBase<CmMsgSndRcv> {

    private static final long serialVersionUID = 1784624803L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCmMsgSndRcv cmMsgSndRcv = new QCmMsgSndRcv("cmMsgSndRcv");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final QCmMsgSnd orfCmMsgSnd;

    public final StringPath rcvNm = createString("rcvNm");

    public final NumberPath<Long> rcvSeq = createNumber("rcvSeq", Long.class);

    public final StringPath rcvStatusCd = createString("rcvStatusCd");

    public final StringPath rcvTelNo = createString("rcvTelNo");

    public final NumberPath<Long> sndSeq = createNumber("sndSeq", Long.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QCmMsgSndRcv(String variable) {
        this(CmMsgSndRcv.class, forVariable(variable), INITS);
    }

    public QCmMsgSndRcv(Path<? extends CmMsgSndRcv> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCmMsgSndRcv(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCmMsgSndRcv(PathMetadata metadata, PathInits inits) {
        this(CmMsgSndRcv.class, metadata, inits);
    }

    public QCmMsgSndRcv(Class<? extends CmMsgSndRcv> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orfCmMsgSnd = inits.isInitialized("orfCmMsgSnd") ? new QCmMsgSnd(forProperty("orfCmMsgSnd")) : null;
    }

}

