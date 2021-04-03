package com.example.demo.db.domain.marcap;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStckMarcap is a Querydsl query type for StckMarcap
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStckMarcap extends EntityPathBase<StckMarcap> {

    private static final long serialVersionUID = 58024771L;

    public static final QStckMarcap stckMarcap = new QStckMarcap("stckMarcap");

    public final NumberPath<Integer> changesAmt = createNumber("changesAmt", Integer.class);

    public final NumberPath<Double> changesRt = createNumber("changesRt", Double.class);

    public final NumberPath<Integer> clsAmt = createNumber("clsAmt", Integer.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Integer> highAmt = createNumber("highAmt", Integer.class);

    public final NumberPath<Integer> lowAmt = createNumber("lowAmt", Integer.class);

    public final NumberPath<Integer> rnk = createNumber("rnk", Integer.class);

    public final NumberPath<Integer> startAmt = createNumber("startAmt", Integer.class);

    public final StringPath stockCd = createString("stockCd");

    public final NumberPath<Double> stockCnt = createNumber("stockCnt", Double.class);

    public final StringPath stockDt = createString("stockDt");

    public final StringPath stockNm = createString("stockNm");

    public final NumberPath<Long> totalMrktAmt = createNumber("totalMrktAmt", Long.class);

    public final NumberPath<Double> totalMrktAmtRt = createNumber("totalMrktAmtRt", Double.class);

    public final NumberPath<Long> tradeAmt = createNumber("tradeAmt", Long.class);

    public final NumberPath<Long> tradeQty = createNumber("tradeQty", Long.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QStckMarcap(String variable) {
        super(StckMarcap.class, forVariable(variable));
    }

    public QStckMarcap(Path<? extends StckMarcap> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStckMarcap(PathMetadata metadata) {
        super(StckMarcap.class, metadata);
    }

}

