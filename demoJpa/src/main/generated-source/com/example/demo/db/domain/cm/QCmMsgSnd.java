package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmMsgSnd is a Querydsl query type for CmMsgSnd
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmMsgSnd extends EntityPathBase<CmMsgSnd> {

    private static final long serialVersionUID = -1353263518L;

    public static final QCmMsgSnd cmMsgSnd = new QCmMsgSnd("cmMsgSnd");

    public final ListPath<CmMsgSndRcv, QCmMsgSndRcv> al_cmMsgSnd = this.<CmMsgSndRcv, QCmMsgSndRcv>createList("al_cmMsgSnd", CmMsgSndRcv.class, QCmMsgSndRcv.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath msg = createString("msg");

    public final DateTimePath<java.util.Date> sndCmplDtm = createDateTime("sndCmplDtm", java.util.Date.class);

    public final DateTimePath<java.util.Date> sndDtm = createDateTime("sndDtm", java.util.Date.class);

    public final StringPath sndKindCd = createString("sndKindCd");

    public final StringPath sndrNm = createString("sndrNm");

    public final StringPath sndrTelNo = createString("sndrTelNo");

    public final NumberPath<Long> sndSeq = createNumber("sndSeq", Long.class);

    public final StringPath sndStatusCd = createString("sndStatusCd");

    public final StringPath sndTypeCd = createString("sndTypeCd");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QCmMsgSnd(String variable) {
        super(CmMsgSnd.class, forVariable(variable));
    }

    public QCmMsgSnd(Path<? extends CmMsgSnd> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmMsgSnd(PathMetadata metadata) {
        super(CmMsgSnd.class, metadata);
    }

}

