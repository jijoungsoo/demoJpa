package com.example.demo.db.domain.upbit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpbitTradesTicks is a Querydsl query type for UpbitTradesTicks
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUpbitTradesTicks extends EntityPathBase<UpbitTradesTicks> {

    private static final long serialVersionUID = -1851845467L;

    public static final QUpbitTradesTicks upbitTradesTicks = new QUpbitTradesTicks("upbitTradesTicks");

    public final StringPath askBid = createString("askBid");

    public final NumberPath<Double> changePrice = createNumber("changePrice", Double.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath market = createString("market");

    public final NumberPath<Double> prevClosingPrice = createNumber("prevClosingPrice", Double.class);

    public final NumberPath<Long> sequentialId = createNumber("sequentialId", Long.class);

    public final NumberPath<Long> timestamp = createNumber("timestamp", Long.class);

    public final StringPath tradeDateUtc = createString("tradeDateUtc");

    public final NumberPath<Double> tradePrice = createNumber("tradePrice", Double.class);

    public final StringPath tradeTimeUtc = createString("tradeTimeUtc");

    public final NumberPath<Double> tradeVolume = createNumber("tradeVolume", Double.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QUpbitTradesTicks(String variable) {
        super(UpbitTradesTicks.class, forVariable(variable));
    }

    public QUpbitTradesTicks(Path<? extends UpbitTradesTicks> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpbitTradesTicks(PathMetadata metadata) {
        super(UpbitTradesTicks.class, metadata);
    }

}

