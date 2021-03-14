package com.example.demo.db.domain.mig_av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMigAvActr is a Querydsl query type for MigAvActr
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMigAvActr extends EntityPathBase<MigAvActr> {

    private static final long serialVersionUID = -281669009L;

    public static final QMigAvActr migAvActr = new QMigAvActr("migAvActr");

    public final NumberPath<Long> actrIdx = createNumber("actrIdx", Long.class);

    public final StringPath brSize = createString("brSize");

    public final StringPath brth = createString("brth");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath debutDt = createString("debutDt");

    public final StringPath dscr = createString("dscr");

    public final StringPath dscrTtl = createString("dscrTtl");

    public final StringPath height = createString("height");

    public final StringPath img = createString("img");

    public final StringPath imgS = createString("imgS");

    public final StringPath nmCn = createString("nmCn");

    public final StringPath nmEn = createString("nmEn");

    public final StringPath nmKr = createString("nmKr");

    public final StringPath oNm = createString("oNm");

    public final StringPath size = createString("size");

    public final StringPath sync = createString("sync");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QMigAvActr(String variable) {
        super(MigAvActr.class, forVariable(variable));
    }

    public QMigAvActr(Path<? extends MigAvActr> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMigAvActr(PathMetadata metadata) {
        super(MigAvActr.class, metadata);
    }

}

