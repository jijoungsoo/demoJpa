package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmCd is a Querydsl query type for CmCd
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmCd extends EntityPathBase<CmCd> {

    private static final long serialVersionUID = 1327050139L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCmCd cmCd = new QCmCd("cmCd");

    public final StringPath attr1 = createString("attr1");

    public final StringPath attr2 = createString("attr2");

    public final StringPath attr3 = createString("attr3");

    public final StringPath attr4 = createString("attr4");

    public final StringPath cd = createString("cd");

    public final StringPath cdNm = createString("cdNm");

    public final QCmGrpCd cmGrpCd;

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath grpCd = createString("grpCd");

    public final NumberPath<Integer> ord = createNumber("ord", Integer.class);

    public final StringPath rmk = createString("rmk");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public final StringPath useYn = createString("useYn");

    public QCmCd(String variable) {
        this(CmCd.class, forVariable(variable), INITS);
    }

    public QCmCd(Path<? extends CmCd> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCmCd(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCmCd(PathMetadata metadata, PathInits inits) {
        this(CmCd.class, metadata, inits);
    }

    public QCmCd(Class<? extends CmCd> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cmGrpCd = inits.isInitialized("cmGrpCd") ? new QCmGrpCd(forProperty("cmGrpCd")) : null;
    }

}

