package com.example.demo.db.domain.kiw;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QKiwStockMst is a Querydsl query type for KiwStockMst
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QKiwStockMst extends EntityPathBase<KiwStockMst> {

    private static final long serialVersionUID = -2093457688L;

    public static final QKiwStockMst kiwStockMst = new QKiwStockMst("kiwStockMst");

    public final NumberPath<Integer> clsAmt = createNumber("clsAmt", Integer.class);

    public final StringPath Construction = createString("Construction");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath OpenDt = createString("OpenDt");

    public final StringPath stockCd = createString("stockCd");

    public final NumberPath<Integer> stockCnt = createNumber("stockCnt", Integer.class);

    public final StringPath stockNm = createString("stockNm");

    public final StringPath stockState = createString("stockState");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QKiwStockMst(String variable) {
        super(KiwStockMst.class, forVariable(variable));
    }

    public QKiwStockMst(Path<? extends KiwStockMst> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKiwStockMst(PathMetadata metadata) {
        super(KiwStockMst.class, metadata);
    }

}

