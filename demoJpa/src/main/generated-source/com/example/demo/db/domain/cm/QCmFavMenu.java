package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCmFavMenu is a Querydsl query type for CmFavMenu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmFavMenu extends EntityPathBase<CmFavMenu> {

    private static final long serialVersionUID = -1420713440L;

    public static final QCmFavMenu cmFavMenu = new QCmFavMenu("cmFavMenu");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> favNo = createNumber("favNo", Long.class);

    public final NumberPath<Long> menuNo = createNumber("menuNo", Long.class);

    public final NumberPath<Integer> ord = createNumber("ord", Integer.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public QCmFavMenu(String variable) {
        super(CmFavMenu.class, forVariable(variable));
    }

    public QCmFavMenu(Path<? extends CmFavMenu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmFavMenu(PathMetadata metadata) {
        super(CmFavMenu.class, metadata);
    }

}

