package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmMenu is a Querydsl query type for CmMenu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmMenu extends EntityPathBase<CmMenu> {

    private static final long serialVersionUID = -309800935L;

    public static final QCmMenu cmMenu = new QCmMenu("cmMenu");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath menuCd = createString("menuCd");

    public final StringPath menuKind = createString("menuKind");

    public final StringPath menuLvl = createString("menuLvl");

    public final StringPath menuNm = createString("menuNm");

    public final NumberPath<Long> menuNo = createNumber("menuNo", Long.class);

    public final StringPath menuPath = createString("menuPath");

    public final StringPath ord = createString("ord");

    public final StringPath pgmId = createString("pgmId");

    public final StringPath prntMenuCd = createString("prntMenuCd");

    public final ListPath<CmMenuRoleCd, QCmMenuRoleCd> refCmMenuRoleCds = this.<CmMenuRoleCd, QCmMenuRoleCd>createList("refCmMenuRoleCds", CmMenuRoleCd.class, QCmMenuRoleCd.class, PathInits.DIRECT2);

    public final StringPath rmk = createString("rmk");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QCmMenu(String variable) {
        super(CmMenu.class, forVariable(variable));
    }

    public QCmMenu(Path<? extends CmMenu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmMenu(PathMetadata metadata) {
        super(CmMenu.class, metadata);
    }

}

