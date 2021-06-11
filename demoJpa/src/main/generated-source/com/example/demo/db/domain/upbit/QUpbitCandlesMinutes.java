package com.example.demo.db.domain.upbit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpbitCandlesMinutes is a Querydsl query type for UpbitCandlesMinutes
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUpbitCandlesMinutes extends EntityPathBase<UpbitCandlesMinutes> {

    private static final long serialVersionUID = 348061115L;

    public static final QUpbitCandlesMinutes upbitCandlesMinutes = new QUpbitCandlesMinutes("upbitCandlesMinutes");

    public final NumberPath<Double> candleAccTradePrice = createNumber("candleAccTradePrice", Double.class);

    public final NumberPath<Double> candleAccTradeVolume = createNumber("candleAccTradeVolume", Double.class);

    public final StringPath candleDateTimeKst = createString("candleDateTimeKst");

    public final StringPath candleDateTimeUtc = createString("candleDateTimeUtc");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Double> highPrice = createNumber("highPrice", Double.class);

    public final NumberPath<Double> lowPrice = createNumber("lowPrice", Double.class);

    public final StringPath market = createString("market");

    public final NumberPath<Double> openingPrice = createNumber("openingPrice", Double.class);

    public final NumberPath<Long> timestamp = createNumber("timestamp", Long.class);

    public final NumberPath<Double> tradePrice = createNumber("tradePrice", Double.class);

    public final NumberPath<Integer> unit = createNumber("unit", Integer.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QUpbitCandlesMinutes(String variable) {
        super(UpbitCandlesMinutes.class, forVariable(variable));
    }

    public QUpbitCandlesMinutes(Path<? extends UpbitCandlesMinutes> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpbitCandlesMinutes(PathMetadata metadata) {
        super(UpbitCandlesMinutes.class, metadata);
    }

}

