package com.example.demo.db.domain.stck;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStSell is a Querydsl query type for StSell
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStSell extends EntityPathBase<StSell> {

    private static final long serialVersionUID = -1634215292L;

    public static final QStSell stSell = new QStSell("stSell");

    public final NumberPath<Integer> amt = createNumber("amt", Integer.class);

    public final NumberPath<Long> buySeq = createNumber("buySeq", Long.class);

    public final NumberPath<Integer> cnt = createNumber("cnt", Integer.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath delYn = createString("delYn");

    public final NumberPath<Integer> fee = createNumber("fee", Integer.class);

    public final StringPath sellDate = createString("sellDate");

    public final NumberPath<Long> sellSeq = createNumber("sellSeq", Long.class);

    public final StringPath stockCd = createString("stockCd");

    public final StringPath stockNm = createString("stockNm");

    public final NumberPath<Integer> tax = createNumber("tax", Integer.class);

    public final NumberPath<Integer> totAmt = createNumber("totAmt", Integer.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QStSell(String variable) {
        super(StSell.class, forVariable(variable));
    }

    public QStSell(Path<? extends StSell> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStSell(PathMetadata metadata) {
        super(StSell.class, metadata);
    }

}

