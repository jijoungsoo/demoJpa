package com.example.demo.db.domain.mig_av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMigAvActrCmt is a Querydsl query type for MigAvActrCmt
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMigAvActrCmt extends EntityPathBase<MigAvActrCmt> {

    private static final long serialVersionUID = 1164717147L;

    public static final QMigAvActrCmt migAvActrCmt = new QMigAvActrCmt("migAvActrCmt");

    public final NumberPath<Long> actorIdx = createNumber("actorIdx", Long.class);

    public final StringPath cmt = createString("cmt");

    public final NumberPath<Long> cmtIdx = createNumber("cmtIdx", Long.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> dslkCnt = createNumber("dslkCnt", Long.class);

    public final NumberPath<Long> lkCnt = createNumber("lkCnt", Long.class);

    public final StringPath writer = createString("writer");

    public QMigAvActrCmt(String variable) {
        super(MigAvActrCmt.class, forVariable(variable));
    }

    public QMigAvActrCmt(Path<? extends MigAvActrCmt> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMigAvActrCmt(PathMetadata metadata) {
        super(MigAvActrCmt.class, metadata);
    }

}

