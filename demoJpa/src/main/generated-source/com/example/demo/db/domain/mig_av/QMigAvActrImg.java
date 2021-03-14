package com.example.demo.db.domain.mig_av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMigAvActrImg is a Querydsl query type for MigAvActrImg
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMigAvActrImg extends EntityPathBase<MigAvActrImg> {

    private static final long serialVersionUID = 1164722900L;

    public static final QMigAvActrImg migAvActrImg = new QMigAvActrImg("migAvActrImg");

    public final NumberPath<Long> actrIdx = createNumber("actrIdx", Long.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath img = createString("img");

    public QMigAvActrImg(String variable) {
        super(MigAvActrImg.class, forVariable(variable));
    }

    public QMigAvActrImg(Path<? extends MigAvActrImg> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMigAvActrImg(PathMetadata metadata) {
        super(MigAvActrImg.class, metadata);
    }

}

