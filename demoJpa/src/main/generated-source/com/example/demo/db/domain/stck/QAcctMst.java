package com.example.demo.db.domain.stck;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAcctMst is a Querydsl query type for AcctMst
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAcctMst extends EntityPathBase<AcctMst> {

    private static final long serialVersionUID = 1612234602L;

    public static final QAcctMst acctMst = new QAcctMst("acctMst");

    public final StringPath acctNm = createString("acctNm");

    public final StringPath acctNum = createString("acctNum");

    public final NumberPath<Long> acctSeq = createNumber("acctSeq", Long.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QAcctMst(String variable) {
        super(AcctMst.class, forVariable(variable));
    }

    public QAcctMst(Path<? extends AcctMst> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAcctMst(PathMetadata metadata) {
        super(AcctMst.class, metadata);
    }

}

