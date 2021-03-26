package com.example.demo.db.domain.mig_av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMigAvActrLk is a Querydsl query type for MigAvActrLk
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMigAvActrLk extends EntityPathBase<MigAvActrLk> {

    private static final long serialVersionUID = -100975538L;

    public static final QMigAvActrLk migAvActrLk = new QMigAvActrLk("migAvActrLk");

    public final NumberPath<Long> actrIdx = createNumber("actrIdx", Long.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Integer> lkCnt = createNumber("lkCnt", Integer.class);

    public final NumberPath<Long> lkIdx = createNumber("lkIdx", Long.class);

    public final NumberPath<Long> usrNo = createNumber("usrNo", Long.class);

    public QMigAvActrLk(String variable) {
        super(MigAvActrLk.class, forVariable(variable));
    }

    public QMigAvActrLk(Path<? extends MigAvActrLk> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMigAvActrLk(PathMetadata metadata) {
        super(MigAvActrLk.class, metadata);
    }

}

