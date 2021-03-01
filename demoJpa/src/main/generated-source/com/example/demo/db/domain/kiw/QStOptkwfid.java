package com.example.demo.db.domain.kiw;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStOptkwfid is a Querydsl query type for StOptkwfid
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStOptkwfid extends EntityPathBase<StOptkwfid> {

    private static final long serialVersionUID = -105206132L;

    public static final QStOptkwfid stOptkwfid = new QStOptkwfid("stOptkwfid");

    public final NumberPath<Integer> bidAmt = createNumber("bidAmt", Integer.class);

    public final NumberPath<Integer> bidAmtFive = createNumber("bidAmtFive", Integer.class);

    public final NumberPath<Integer> bidAmtFour = createNumber("bidAmtFour", Integer.class);

    public final NumberPath<Integer> bidAmtOne = createNumber("bidAmtOne", Integer.class);

    public final NumberPath<Integer> bidAmtThree = createNumber("bidAmtThree", Integer.class);

    public final NumberPath<Integer> bidAmtTwo = createNumber("bidAmtTwo", Integer.class);

    public final NumberPath<Double> breakEvenPoint = createNumber("breakEvenPoint", Double.class);

    public final NumberPath<Integer> capitalAmt = createNumber("capitalAmt", Integer.class);

    public final NumberPath<Integer> clsgAmt = createNumber("clsgAmt", Integer.class);

    public final NumberPath<Integer> contractQty = createNumber("contractQty", Integer.class);

    public final NumberPath<Double> contractStrength = createNumber("contractStrength", Double.class);

    public final StringPath contractTime = createString("contractTime");

    public final NumberPath<Integer> contrastOpenInterest = createNumber("contrastOpenInterest", Integer.class);

    public final NumberPath<Integer> contrastYesterday = createNumber("contrastYesterday", Integer.class);

    public final NumberPath<Integer> contrastYesterdaySymbol = createNumber("contrastYesterdaySymbol", Integer.class);

    public final NumberPath<Double> conversionRt = createNumber("conversionRt", Double.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Integer> currAmt = createNumber("currAmt", Integer.class);

    public final NumberPath<Integer> delta = createNumber("delta", Integer.class);

    public final StringPath elwExpiryDt = createString("elwExpiryDt");

    public final NumberPath<Integer> elwStrikeAmt = createNumber("elwStrikeAmt", Integer.class);

    public final NumberPath<Integer> expectationContractAmt = createNumber("expectationContractAmt", Integer.class);

    public final NumberPath<Integer> expectationContractQty = createNumber("expectationContractQty", Integer.class);

    public final NumberPath<Integer> faceAmt = createNumber("faceAmt", Integer.class);

    public final NumberPath<Double> fluctuationRt = createNumber("fluctuationRt", Double.class);

    public final NumberPath<Integer> fstBidBalance = createNumber("fstBidBalance", Integer.class);

    public final NumberPath<Integer> fstBidQty = createNumber("fstBidQty", Integer.class);

    public final NumberPath<Integer> fstOfferedBalance = createNumber("fstOfferedBalance", Integer.class);

    public final NumberPath<Integer> fstOfferedQty = createNumber("fstOfferedQty", Integer.class);

    public final NumberPath<Integer> gamma = createNumber("gamma", Integer.class);

    public final NumberPath<Double> gearing = createNumber("gearing", Double.class);

    public final NumberPath<Integer> highAmt = createNumber("highAmt", Integer.class);

    public final StringPath hogaTime = createString("hogaTime");

    public final NumberPath<Integer> impliedVolatility = createNumber("impliedVolatility", Integer.class);

    public final NumberPath<Integer> lo = createNumber("lo", Integer.class);

    public final NumberPath<Integer> lowAmt = createNumber("lowAmt", Integer.class);

    public final NumberPath<Integer> lowerAmtLmt = createNumber("lowerAmtLmt", Integer.class);

    public final NumberPath<Integer> offeredAmt = createNumber("offeredAmt", Integer.class);

    public final NumberPath<Integer> offeredAmtFive = createNumber("offeredAmtFive", Integer.class);

    public final NumberPath<Integer> offeredAmtFour = createNumber("offeredAmtFour", Integer.class);

    public final NumberPath<Integer> offeredAmtOne = createNumber("offeredAmtOne", Integer.class);

    public final NumberPath<Integer> offeredAmtThree = createNumber("offeredAmtThree", Integer.class);

    public final NumberPath<Integer> offeredAmtTwo = createNumber("offeredAmtTwo", Integer.class);

    public final NumberPath<Integer> openInterest = createNumber("openInterest", Integer.class);

    public final NumberPath<Double> parityRt = createNumber("parityRt", Double.class);

    public final NumberPath<Integer> startAmt = createNumber("startAmt", Integer.class);

    public final StringPath stockCd = createString("stockCd");

    public final NumberPath<Integer> stockCnt = createNumber("stockCnt", Integer.class);

    public final StringPath stockDt = createString("stockDt");

    public final NumberPath<Integer> theoristAmt = createNumber("theoristAmt", Integer.class);

    public final NumberPath<Integer> theta = createNumber("theta", Integer.class);

    public final NumberPath<Integer> totBidBalance = createNumber("totBidBalance", Integer.class);

    public final NumberPath<Integer> totBidQty = createNumber("totBidQty", Integer.class);

    public final NumberPath<Integer> totMarketAmt = createNumber("totMarketAmt", Integer.class);

    public final NumberPath<Integer> totOfferedBalance = createNumber("totOfferedBalance", Integer.class);

    public final NumberPath<Integer> totOfferedQty = createNumber("totOfferedQty", Integer.class);

    public final NumberPath<Integer> tradeAmt = createNumber("tradeAmt", Integer.class);

    public final NumberPath<Integer> tradeQty = createNumber("tradeQty", Integer.class);

    public final NumberPath<Integer> upperAmtLmt = createNumber("upperAmtLmt", Integer.class);

    public final NumberPath<Integer> vega = createNumber("vega", Integer.class);

    public final NumberPath<Integer> yesterdayAmt = createNumber("yesterdayAmt", Integer.class);

    public final NumberPath<Double> yesterdayContrastTradeRt = createNumber("yesterdayContrastTradeRt", Double.class);

    public QStOptkwfid(String variable) {
        super(StOptkwfid.class, forVariable(variable));
    }

    public QStOptkwfid(Path<? extends StOptkwfid> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStOptkwfid(PathMetadata metadata) {
        super(StOptkwfid.class, metadata);
    }

}

