package com.example.demo.db.domain.kiw;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStRealtimeContract is a Querydsl query type for StRealtimeContract
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStRealtimeContract extends EntityPathBase<StRealtimeContract> {

    private static final long serialVersionUID = 453069799L;

    public static final QStRealtimeContract stRealtimeContract = new QStRealtimeContract("stRealtimeContract");

    public final NumberPath<Integer> accumulatedTradeAmt = createNumber("accumulatedTradeAmt", Integer.class);

    public final NumberPath<Integer> accumulatedTradeQty = createNumber("accumulatedTradeQty", Integer.class);

    public final NumberPath<Integer> bidAmt = createNumber("bidAmt", Integer.class);

    public final NumberPath<Double> contractStrength = createNumber("contractStrength", Double.class);

    public final NumberPath<Integer> contrastYesterday = createNumber("contrastYesterday", Integer.class);

    public final NumberPath<Integer> contrastYesterdaySymbol = createNumber("contrastYesterdaySymbol", Integer.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Integer> currAmt = createNumber("currAmt", Integer.class);

    public final StringPath currTime = createString("currTime");

    public final NumberPath<Double> fluctuationRt = createNumber("fluctuationRt", Double.class);

    public final NumberPath<Integer> highAmt = createNumber("highAmt", Integer.class);

    public final NumberPath<Double> koAccessibilityRt = createNumber("koAccessibilityRt", Double.class);

    public final NumberPath<Integer> lowAmt = createNumber("lowAmt", Integer.class);

    public final StringPath lowerAmtLimitTime = createString("lowerAmtLimitTime");

    public final NumberPath<Integer> marketGubun = createNumber("marketGubun", Integer.class);

    public final NumberPath<Integer> offeredAmt = createNumber("offeredAmt", Integer.class);

    public final StringPath realName = createString("realName");

    public final NumberPath<Integer> startAmt = createNumber("startAmt", Integer.class);

    public final StringPath stockCd = createString("stockCd");

    public final StringPath stockDt = createString("stockDt");

    public final NumberPath<Integer> totMarketAmt = createNumber("totMarketAmt", Integer.class);

    public final NumberPath<Double> trade_turnover_ratio = createNumber("trade_turnover_ratio", Double.class);

    public final NumberPath<Double> tradeAmountVariation = createNumber("tradeAmountVariation", Double.class);

    public final NumberPath<Integer> tradeCost = createNumber("tradeCost", Integer.class);

    public final NumberPath<Integer> tradeQty = createNumber("tradeQty", Integer.class);

    public final StringPath upperAmtLimitTime = createString("upperAmtLimitTime");

    public final NumberPath<Integer> yesterdayContrastTradeQty = createNumber("yesterdayContrastTradeQty", Integer.class);

    public final NumberPath<Double> yesterdayContrastTradeRt = createNumber("yesterdayContrastTradeRt", Double.class);

    public QStRealtimeContract(String variable) {
        super(StRealtimeContract.class, forVariable(variable));
    }

    public QStRealtimeContract(Path<? extends StRealtimeContract> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStRealtimeContract(PathMetadata metadata) {
        super(StRealtimeContract.class, metadata);
    }

}

