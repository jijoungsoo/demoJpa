package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmMenuRoleCd is a Querydsl query type for CmMenuRoleCd
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmMenuRoleCd extends EntityPathBase<CmMenuRoleCd> {

    private static final long serialVersionUID = 1952762096L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCmMenuRoleCd cmMenuRoleCd = new QCmMenuRoleCd("cmMenuRoleCd");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final NumberPath<Long> menuNo = createNumber("menuNo", Long.class);

    public final QCmMenu refCmMenuRoleCd;

    public final StringPath roleCd = createString("roleCd");

    public QCmMenuRoleCd(String variable) {
        this(CmMenuRoleCd.class, forVariable(variable), INITS);
    }

    public QCmMenuRoleCd(Path<? extends CmMenuRoleCd> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCmMenuRoleCd(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCmMenuRoleCd(PathMetadata metadata, PathInits inits) {
        this(CmMenuRoleCd.class, metadata, inits);
    }

    public QCmMenuRoleCd(Class<? extends CmMenuRoleCd> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.refCmMenuRoleCd = inits.isInitialized("refCmMenuRoleCd") ? new QCmMenu(forProperty("refCmMenuRoleCd")) : null;
    }

}

