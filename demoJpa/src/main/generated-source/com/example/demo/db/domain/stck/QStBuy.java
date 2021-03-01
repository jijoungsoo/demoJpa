package com.example.demo.db.domain.stck;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStBuy is a Querydsl query type for StBuy
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStBuy extends EntityPathBase<StBuy> {

    private static final long serialVersionUID = 1886930196L;

    public static final QStBuy stBuy = new QStBuy("stBuy");

    public final NumberPath<Integer> amt = createNumber("amt", Integer.class);

    public final NumberPath<Integer> balCnt = createNumber("balCnt", Integer.class);

    public final StringPath buyDate = createString("buyDate");

    public final NumberPath<Long> buySeq = createNumber("buySeq", Long.class);

    public final NumberPath<Integer> cnt = createNumber("cnt", Integer.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath delYn = createString("delYn");

    public final NumberPath<Integer> fee = createNumber("fee", Integer.class);

    public final StringPath rmk = createString("rmk");

    public final StringPath stockCd = createString("stockCd");

    public final StringPath stockNm = createString("stockNm");

    public final NumberPath<Integer> totAmt = createNumber("totAmt", Integer.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QStBuy(String variable) {
        super(StBuy.class, forVariable(variable));
    }

    public QStBuy(Path<? extends StBuy> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStBuy(PathMetadata metadata) {
        super(StBuy.class, metadata);
    }

}

