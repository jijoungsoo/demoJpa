package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmUserRoleCd is a Querydsl query type for CmUserRoleCd
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmUserRoleCd extends EntityPathBase<CmUserRoleCd> {

    private static final long serialVersionUID = -321839524L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCmUserRoleCd cmUserRoleCd = new QCmUserRoleCd("cmUserRoleCd");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath ord = createString("ord");

    public final QCmRoleCd refCmRoleCd;

    public final QCmUser refCmUser;

    public final StringPath rmk = createString("rmk");

    public final StringPath roleCd = createString("roleCd");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public final StringPath useYn = createString("useYn");

    public QCmUserRoleCd(String variable) {
        this(CmUserRoleCd.class, forVariable(variable), INITS);
    }

    public QCmUserRoleCd(Path<? extends CmUserRoleCd> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCmUserRoleCd(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCmUserRoleCd(PathMetadata metadata, PathInits inits) {
        this(CmUserRoleCd.class, metadata, inits);
    }

    public QCmUserRoleCd(Class<? extends CmUserRoleCd> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.refCmRoleCd = inits.isInitialized("refCmRoleCd") ? new QCmRoleCd(forProperty("refCmRoleCd")) : null;
        this.refCmUser = inits.isInitialized("refCmUser") ? new QCmUser(forProperty("refCmUser")) : null;
    }

}

