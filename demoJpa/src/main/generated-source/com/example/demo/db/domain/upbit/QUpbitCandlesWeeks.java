package com.example.demo.db.domain.upbit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpbitCandlesWeeks is a Querydsl query type for UpbitCandlesWeeks
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUpbitCandlesWeeks extends EntityPathBase<UpbitCandlesWeeks> {

    private static final long serialVersionUID = -897792293L;

    public static final QUpbitCandlesWeeks upbitCandlesWeeks = new QUpbitCandlesWeeks("upbitCandlesWeeks");

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

    public QUpbitCandlesWeeks(String variable) {
        super(UpbitCandlesWeeks.class, forVariable(variable));
    }

    public QUpbitCandlesWeeks(Path<? extends UpbitCandlesWeeks> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpbitCandlesWeeks(PathMetadata metadata) {
        super(UpbitCandlesWeeks.class, metadata);
    }

}

