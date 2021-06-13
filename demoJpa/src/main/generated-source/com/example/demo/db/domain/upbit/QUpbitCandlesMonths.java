package com.example.demo.db.domain.upbit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpbitCandlesMonths is a Querydsl query type for UpbitCandlesMonths
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUpbitCandlesMonths extends EntityPathBase<UpbitCandlesMonths> {

    private static final long serialVersionUID = 1956430231L;

    public static final QUpbitCandlesMonths upbitCandlesMonths = new QUpbitCandlesMonths("upbitCandlesMonths");

    public final NumberPath<Double> candleAccTradePrice = createNumber("candleAccTradePrice", Double.class);

    public final NumberPath<Double> candleAccTradeVolume = createNumber("candleAccTradeVolume", Double.class);

    public final StringPath candleDateTimeKst = createString("candleDateTimeKst");

    public final StringPath candleDateTimeUtc = createString("candleDateTimeUtc");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath firstDayOfPeriod = createString("firstDayOfPeriod");

    public final NumberPath<Double> highPrice = createNumber("highPrice", Double.class);

    public final NumberPath<Double> lowPrice = createNumber("lowPrice", Double.class);

    public final StringPath market = createString("market");

    public final NumberPath<Double> openingPrice = createNumber("openingPrice", Double.class);

    public final NumberPath<Long> timestamp = createNumber("timestamp", Long.class);

    public final NumberPath<Double> tradePrice = createNumber("tradePrice", Double.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QUpbitCandlesMonths(String variable) {
        super(UpbitCandlesMonths.class, forVariable(variable));
    }

    public QUpbitCandlesMonths(Path<? extends UpbitCandlesMonths> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpbitCandlesMonths(PathMetadata metadata) {
        super(UpbitCandlesMonths.class, metadata);
    }

}

