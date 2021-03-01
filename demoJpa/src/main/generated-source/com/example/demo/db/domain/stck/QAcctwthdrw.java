package com.example.demo.db.domain.stck;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAcctwthdrw is a Querydsl query type for Acctwthdrw
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAcctwthdrw extends EntityPathBase<Acctwthdrw> {

    private static final long serialVersionUID = 664847138L;

    public static final QAcctwthdrw acctwthdrw = new QAcctwthdrw("acctwthdrw");

    public final NumberPath<Long> acctSeq = createNumber("acctSeq", Long.class);

    public final NumberPath<Long> amt = createNumber("amt", Long.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath imnDate = createString("imnDate");

    public final NumberPath<Long> imnSeq = createNumber("imnSeq", Long.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QAcctwthdrw(String variable) {
        super(Acctwthdrw.class, forVariable(variable));
    }

    public QAcctwthdrw(Path<? extends Acctwthdrw> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAcctwthdrw(PathMetadata metadata) {
        super(Acctwthdrw.class, metadata);
    }

}

