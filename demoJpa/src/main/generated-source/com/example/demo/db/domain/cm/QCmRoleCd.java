package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmRoleCd is a Querydsl query type for CmRoleCd
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmRoleCd extends EntityPathBase<CmRoleCd> {

    private static final long serialVersionUID = -1213646927L;

    public static final QCmRoleCd cmRoleCd = new QCmRoleCd("cmRoleCd");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath ord = createString("ord");

    public final ListPath<CmUserRoleCd, QCmUserRoleCd> refCmUserRoleCds = this.<CmUserRoleCd, QCmUserRoleCd>createList("refCmUserRoleCds", CmUserRoleCd.class, QCmUserRoleCd.class, PathInits.DIRECT2);

    public final StringPath rmk = createString("rmk");

    public final StringPath roleCd = createString("roleCd");

    public final StringPath roleNm = createString("roleNm");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public final StringPath useYn = createString("useYn");

    public QCmRoleCd(String variable) {
        super(CmRoleCd.class, forVariable(variable));
    }

    public QCmRoleCd(Path<? extends CmRoleCd> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmRoleCd(PathMetadata metadata) {
        super(CmRoleCd.class, metadata);
    }

}

