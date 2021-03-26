package com.example.demo.db.domain.mig_av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMigAvMvGen is a Querydsl query type for MigAvMvGen
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMigAvMvGen extends EntityPathBase<MigAvMvGen> {

    private static final long serialVersionUID = -130199944L;

    public static final QMigAvMvGen migAvMvGen = new QMigAvMvGen("migAvMvGen");

    public final NumberPath<Long> cateNo = createNumber("cateNo", Long.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> dvdIdx = createNumber("dvdIdx", Long.class);

    public final NumberPath<Long> menuNo = createNumber("menuNo", Long.class);

    public QMigAvMvGen(String variable) {
        super(MigAvMvGen.class, forVariable(variable));
    }

    public QMigAvMvGen(Path<? extends MigAvMvGen> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMigAvMvGen(PathMetadata metadata) {
        super(MigAvMvGen.class, metadata);
    }

}

