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

    public final NumberPath<Double> accTradePrice = createNumber("accTradePrice", Double.class);

    public final NumberPath<Double> accTradePrice24h = createNumber("accTradePrice24h", Double.class);

    public final NumberPath<Double> accTradeVolume = createNumber("accTradeVolume", Double.class);

    public final NumberPath<Double> accTradeVolume24h = createNumber("accTradeVolume24h", Double.class);

    public final StringPath change = createString("change");

    public final NumberPath<Double> changePrice = createNumber("changePrice", Double.class);

    public final NumberPath<Double> changeRate = createNumber("changeRate", Double.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath enNm = createString("enNm");

    public final StringPath highest52WeekDate = createString("highest52WeekDate");

    public final NumberPath<Double> highest52WeekPrice = createNumber("highest52WeekPrice", Double.class);

    public final NumberPath<Double> highPrice = createNumber("highPrice", Double.class);

    public final StringPath krNm = createString("krNm");

    public final StringPath lowest52WeekDate = createString("lowest52WeekDate");

    public final NumberPath<Double> lowest52WeekPrice = createNumber("lowest52WeekPrice", Double.class);

    public final NumberPath<Double> lowPrice = createNumber("lowPrice", Double.class);

    public final StringPath market = createString("market");

    public final StringPath marketCd = createString("marketCd");

    public final StringPath marketWarning = createString("marketWarning");

    public final NumberPath<Double> openingPrice = createNumber("openingPrice", Double.class);

    public final NumberPath<Double> prevClosingPrice = createNumber("prevClosingPrice", Double.class);

    public final NumberPath<Double> signedChangePrice = createNumber("signedChangePrice", Double.class);

    public final NumberPath<Double> signedChangeRate = createNumber("signedChangeRate", Double.class);

    public final NumberPath<Long> timestamp = createNumber("timestamp", Long.class);

    public final StringPath tradeDate = createString("tradeDate");

    public final StringPath tradeDateKst = createString("tradeDateKst");

    public final NumberPath<Double> tradePrice = createNumber("tradePrice", Double.class);

    public final StringPath tradeTime = createString("tradeTime");

    public final StringPath tradeTimeKst = createString("tradeTimeKst");

    public final NumberPath<Double> tradeVolume = createNumber("tradeVolume", Double.class);

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

