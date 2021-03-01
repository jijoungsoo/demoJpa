package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmEmlSnd is a Querydsl query type for CmEmlSnd
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmEmlSnd extends EntityPathBase<CmEmlSnd> {

    private static final long serialVersionUID = -1587688897L;

    public static final QCmEmlSnd cmEmlSnd = new QCmEmlSnd("cmEmlSnd");

    public final ListPath<CmEmlSndRcv, QCmEmlSndRcv> al_cmEmlSnd = this.<CmEmlSndRcv, QCmEmlSndRcv>createList("al_cmEmlSnd", CmEmlSndRcv.class, QCmEmlSndRcv.class, PathInits.DIRECT2);

    public final StringPath cntnt = createString("cntnt");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final DateTimePath<java.util.Date> sndCmplDtm = createDateTime("sndCmplDtm", java.util.Date.class);

    public final DateTimePath<java.util.Date> sndDtm = createDateTime("sndDtm", java.util.Date.class);

    public final StringPath sndrAddr = createString("sndrAddr");

    public final StringPath sndrNm = createString("sndrNm");

    public final NumberPath<Long> sndSeq = createNumber("sndSeq", Long.class);

    public final StringPath sndStatusCd = createString("sndStatusCd");

    public final StringPath sndTypeCd = createString("sndTypeCd");

    public final StringPath ttl = createString("ttl");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QCmEmlSnd(String variable) {
        super(CmEmlSnd.class, forVariable(variable));
    }

    public QCmEmlSnd(Path<? extends CmEmlSnd> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmEmlSnd(PathMetadata metadata) {
        super(CmEmlSnd.class, metadata);
    }

}

