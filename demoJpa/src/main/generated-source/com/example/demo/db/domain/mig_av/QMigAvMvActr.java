package com.example.demo.db.domain.mig_av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMigAvMvActr is a Querydsl query type for MigAvMvActr
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMigAvMvActr extends EntityPathBase<MigAvMvActr> {

    private static final long serialVersionUID = 258588664L;

    public static final QMigAvMvActr migAvMvActr = new QMigAvMvActr("migAvMvActr");

    public final NumberPath<Long> actrIdx = createNumber("actrIdx", Long.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> dvdIdx = createNumber("dvdIdx", Long.class);

    public QMigAvMvActr(String variable) {
        super(MigAvMvActr.class, forVariable(variable));
    }

    public QMigAvMvActr(Path<? extends MigAvMvActr> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMigAvMvActr(PathMetadata metadata) {
        super(MigAvMvActr.class, metadata);
    }

}

