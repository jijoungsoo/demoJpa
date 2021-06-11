package com.example.demo.db.domain.upbit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpbitCandlesDays is a Querydsl query type for UpbitCandlesDays
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUpbitCandlesDays extends EntityPathBase<UpbitCandlesDays> {

    private static final long serialVersionUID = 1494490363L;

    public static final QUpbitCandlesDays upbitCandlesDays = new QUpbitCandlesDays("upbitCandlesDays");

    public final NumberPath<Double> candleAccTradePrice = createNumber("candleAccTradePrice", Double.class);

    public final NumberPath<Double> candleAccTradeVolume = createNumber("candleAccTradeVolume", Double.class);

    public final StringPath candleDateTimeKst = createString("candleDateTimeKst");

    public final StringPath candleDateTimeUtc = createString("candleDateTimeUtc");

    public final NumberPath<Double> changePrice = createNumber("changePrice", Double.class);

    public final NumberPath<Double> changeRate = createNumber("changeRate", Double.class);

    public final NumberPath<Double> convertedTradePrice = createNumber("convertedTradePrice", Double.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Double> highPrice = createNumber("highPrice", Double.class);

    public final NumberPath<Double> lowPrice = createNumber("lowPrice", Double.class);

    public final StringPath market = createString("market");

    public final NumberPath<Double> openingPrice = createNumber("openingPrice", Double.class);

    public final NumberPath<Double> prevClosingPrice = createNumber("prevClosingPrice", Double.class);

    public final NumberPath<Long> timestamp = createNumber("timestamp", Long.class);

    public final NumberPath<Double> tradePrice = createNumber("tradePrice", Double.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QUpbitCandlesDays(String variable) {
        super(UpbitCandlesDays.class, forVariable(variable));
    }

    public QUpbitCandlesDays(Path<? extends UpbitCandlesDays> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpbitCandlesDays(PathMetadata metadata) {
        super(UpbitCandlesDays.class, metadata);
    }

}

