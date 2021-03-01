package com.example.demo.db.domain.stck;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAcctDpst is a Querydsl query type for AcctDpst
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAcctDpst extends EntityPathBase<AcctDpst> {

    private static final long serialVersionUID = -1560605807L;

    public static final QAcctDpst acctDpst = new QAcctDpst("acctDpst");

    public final NumberPath<Long> acctSeq = createNumber("acctSeq", Long.class);

    public final NumberPath<Long> amt = createNumber("amt", Long.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath imnDate = createString("imnDate");

    public final NumberPath<Long> imnSeq = createNumber("imnSeq", Long.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QAcctDpst(String variable) {
        super(AcctDpst.class, forVariable(variable));
    }

    public QAcctDpst(Path<? extends AcctDpst> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAcctDpst(PathMetadata metadata) {
        super(AcctDpst.class, metadata);
    }

}

