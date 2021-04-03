package com.example.demo.db.domain.mig_av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMigAvMvActrMain is a Querydsl query type for MigAvMvActrMain
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMigAvMvActrMain extends EntityPathBase<MigAvMvActrMain> {

    private static final long serialVersionUID = -1002603055L;

    public static final QMigAvMvActrMain migAvMvActrMain = new QMigAvMvActrMain("migAvMvActrMain");

    public final NumberPath<Long> actrIdx = createNumber("actrIdx", Long.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> dvdIdx = createNumber("dvdIdx", Long.class);

    public QMigAvMvActrMain(String variable) {
        super(MigAvMvActrMain.class, forVariable(variable));
    }

    public QMigAvMvActrMain(Path<? extends MigAvMvActrMain> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMigAvMvActrMain(PathMetadata metadata) {
        super(MigAvMvActrMain.class, metadata);
    }

}

