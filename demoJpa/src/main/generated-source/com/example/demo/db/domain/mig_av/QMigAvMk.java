package com.example.demo.db.domain.mig_av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMigAvMk is a Querydsl query type for MigAvMk
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMigAvMk extends EntityPathBase<MigAvMk> {

    private static final long serialVersionUID = -1810346579L;

    public static final QMigAvMk migAvMk = new QMigAvMk("migAvMk");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath img = createString("img");

    public final StringPath imgL = createString("imgL");

    public final NumberPath<Long> mkId = createNumber("mkId", Long.class);

    public final StringPath nm = createString("nm");

    public final StringPath ttl = createString("ttl");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QMigAvMk(String variable) {
        super(MigAvMk.class, forVariable(variable));
    }

    public QMigAvMk(Path<? extends MigAvMk> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMigAvMk(PathMetadata metadata) {
        super(MigAvMk.class, metadata);
    }

}

