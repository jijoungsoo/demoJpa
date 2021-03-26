package com.example.demo.db.domain.mig_av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMigAvMenu is a Querydsl query type for MigAvMenu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMigAvMenu extends EntityPathBase<MigAvMenu> {

    private static final long serialVersionUID = -281309778L;

    public static final QMigAvMenu migAvMenu = new QMigAvMenu("migAvMenu");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath menuNm = createString("menuNm");

    public final NumberPath<Long> menuNo = createNumber("menuNo", Long.class);

    public QMigAvMenu(String variable) {
        super(MigAvMenu.class, forVariable(variable));
    }

    public QMigAvMenu(Path<? extends MigAvMenu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMigAvMenu(PathMetadata metadata) {
        super(MigAvMenu.class, metadata);
    }

}

