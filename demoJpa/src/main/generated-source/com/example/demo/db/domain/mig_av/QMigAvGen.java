package com.example.demo.db.domain.mig_av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMigAvGen is a Querydsl query type for MigAvGen
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMigAvGen extends EntityPathBase<MigAvGen> {

    private static final long serialVersionUID = -286174943L;

    public static final QMigAvGen migAvGen = new QMigAvGen("migAvGen");

    public final StringPath cateNm = createString("cateNm");

    public final StringPath cateNmJp = createString("cateNmJp");

    public final NumberPath<Long> cateNo = createNumber("cateNo", Long.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> menuNo = createNumber("menuNo", Long.class);

    public QMigAvGen(String variable) {
        super(MigAvGen.class, forVariable(variable));
    }

    public QMigAvGen(Path<? extends MigAvGen> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMigAvGen(PathMetadata metadata) {
        super(MigAvGen.class, metadata);
    }

}

