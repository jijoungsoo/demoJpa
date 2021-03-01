package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmGrpCd is a Querydsl query type for CmGrpCd
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmGrpCd extends EntityPathBase<CmGrpCd> {

    private static final long serialVersionUID = -1019047764L;

    public static final QCmGrpCd cmGrpCd = new QCmGrpCd("cmGrpCd");

    public final ListPath<CmCd, QCmCd> cmCds = this.<CmCd, QCmCd>createList("cmCds", CmCd.class, QCmCd.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath grpCd = createString("grpCd");

    public final StringPath grpNm = createString("grpNm");

    public final NumberPath<Integer> ord = createNumber("ord", Integer.class);

    public final StringPath rmk = createString("rmk");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public final StringPath useYn = createString("useYn");

    public QCmGrpCd(String variable) {
        super(CmGrpCd.class, forVariable(variable));
    }

    public QCmGrpCd(Path<? extends CmGrpCd> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmGrpCd(PathMetadata metadata) {
        super(CmGrpCd.class, metadata);
    }

}

