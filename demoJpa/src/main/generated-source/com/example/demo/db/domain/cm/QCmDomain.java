package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCmDomain is a Querydsl query type for CmDomain
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmDomain extends EntityPathBase<CmDomain> {

    private static final long serialVersionUID = -1614427906L;

    public static final QCmDomain cmDomain = new QCmDomain("cmDomain");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath dataType = createString("dataType");

    public final StringPath dmnCd = createString("dmnCd");

    public final StringPath dmnNm = createString("dmnNm");

    public final NumberPath<Long> dmnNo = createNumber("dmnNo", Long.class);

    public final StringPath rmk = createString("rmk");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QCmDomain(String variable) {
        super(CmDomain.class, forVariable(variable));
    }

    public QCmDomain(Path<? extends CmDomain> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmDomain(PathMetadata metadata) {
        super(CmDomain.class, metadata);
    }

}

