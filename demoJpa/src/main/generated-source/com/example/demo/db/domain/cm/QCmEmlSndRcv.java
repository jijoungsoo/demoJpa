package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmEmlSndRcv is a Querydsl query type for CmEmlSndRcv
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmEmlSndRcv extends EntityPathBase<CmEmlSndRcv> {

    private static final long serialVersionUID = 1634982310L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCmEmlSndRcv cmEmlSndRcv = new QCmEmlSndRcv("cmEmlSndRcv");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final QCmEmlSnd orfCmEmlSnd;

    public final StringPath rcvAddr = createString("rcvAddr");

    public final StringPath rcvNm = createString("rcvNm");

    public final NumberPath<Long> rcvSeq = createNumber("rcvSeq", Long.class);

    public final StringPath rcvStatusCd = createString("rcvStatusCd");

    public final NumberPath<Long> sndSeq = createNumber("sndSeq", Long.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QCmEmlSndRcv(String variable) {
        this(CmEmlSndRcv.class, forVariable(variable), INITS);
    }

    public QCmEmlSndRcv(Path<? extends CmEmlSndRcv> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCmEmlSndRcv(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCmEmlSndRcv(PathMetadata metadata, PathInits inits) {
        this(CmEmlSndRcv.class, metadata, inits);
    }

    public QCmEmlSndRcv(Class<? extends CmEmlSndRcv> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orfCmEmlSnd = inits.isInitialized("orfCmEmlSnd") ? new QCmEmlSnd(forProperty("orfCmEmlSnd")) : null;
    }

}

