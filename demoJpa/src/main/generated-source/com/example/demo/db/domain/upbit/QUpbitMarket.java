package com.example.demo.db.domain.upbit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpbitMarket is a Querydsl query type for UpbitMarket
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUpbitMarket extends EntityPathBase<UpbitMarket> {

    private static final long serialVersionUID = 1601290494L;

    public static final QUpbitMarket upbitMarket = new QUpbitMarket("upbitMarket");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath enNm = createString("enNm");

    public final StringPath krNm = createString("krNm");

    public final StringPath market = createString("market");

    public final StringPath marketCd = createString("marketCd");

    public final StringPath marketWarning = createString("marketWarning");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QUpbitMarket(String variable) {
        super(UpbitMarket.class, forVariable(variable));
    }

    public QUpbitMarket(Path<? extends UpbitMarket> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpbitMarket(PathMetadata metadata) {
        super(UpbitMarket.class, metadata);
    }

}

