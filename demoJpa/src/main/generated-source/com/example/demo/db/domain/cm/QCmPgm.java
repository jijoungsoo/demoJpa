package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCmPgm is a Querydsl query type for CmPgm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmPgm extends EntityPathBase<CmPgm> {

    private static final long serialVersionUID = -1811105956L;

    public static final QCmPgm cmPgm = new QCmPgm("cmPgm");

    public final StringPath category = createString("category");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath dirLink = createString("dirLink");

    public final StringPath ord = createString("ord");

    public final StringPath pgmId = createString("pgmId");

    public final StringPath pgmLink = createString("pgmLink");

    public final StringPath pgmNm = createString("pgmNm");

    public final NumberPath<Long> pgmNo = createNumber("pgmNo", Long.class);

    public final StringPath rmk = createString("rmk");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QCmPgm(String variable) {
        super(CmPgm.class, forVariable(variable));
    }

    public QCmPgm(Path<? extends CmPgm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmPgm(PathMetadata metadata) {
        super(CmPgm.class, metadata);
    }

}

