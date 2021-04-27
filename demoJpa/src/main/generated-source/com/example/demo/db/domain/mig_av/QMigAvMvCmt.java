package com.example.demo.db.domain.mig_av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMigAvMvCmt is a Querydsl query type for MigAvMvCmt
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMigAvMvCmt extends EntityPathBase<MigAvMvCmt> {

    private static final long serialVersionUID = -130203534L;

    public static final QMigAvMvCmt migAvMvCmt = new QMigAvMvCmt("migAvMvCmt");

    public final NumberPath<Long> actorIdx = createNumber("actorIdx", Long.class);

    public final StringPath cmt = createString("cmt");

    public final NumberPath<Long> cmtIdx = createNumber("cmtIdx", Long.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> dslkCnt = createNumber("dslkCnt", Long.class);

    public final NumberPath<Long> dvdIdx = createNumber("dvdIdx", Long.class);

    public final NumberPath<Long> lkCnt = createNumber("lkCnt", Long.class);

    public final StringPath writer = createString("writer");

    public QMigAvMvCmt(String variable) {
        super(MigAvMvCmt.class, forVariable(variable));
    }

    public QMigAvMvCmt(Path<? extends MigAvMvCmt> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMigAvMvCmt(PathMetadata metadata) {
        super(MigAvMvCmt.class, metadata);
    }

}

