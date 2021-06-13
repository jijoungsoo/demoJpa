package com.example.demo.db.domain.upbit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpbitOrderBookUnits is a Querydsl query type for UpbitOrderBookUnits
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUpbitOrderBookUnits extends EntityPathBase<UpbitOrderBookUnits> {

    private static final long serialVersionUID = -1489055462L;

    public static final QUpbitOrderBookUnits upbitOrderBookUnits = new QUpbitOrderBookUnits("upbitOrderBookUnits");

    public final NumberPath<Double> askPrice = createNumber("askPrice", Double.class);

    public final NumberPath<Double> askSize = createNumber("askSize", Double.class);

    public final NumberPath<Double> bidPrice = createNumber("bidPrice", Double.class);

    public final NumberPath<Double> bidSize = createNumber("bidSize", Double.class);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath market = createString("market");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final NumberPath<Long> timestamp = createNumber("timestamp", Long.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QUpbitOrderBookUnits(String variable) {
        super(UpbitOrderBookUnits.class, forVariable(variable));
    }

    public QUpbitOrderBookUnits(Path<? extends UpbitOrderBookUnits> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpbitOrderBookUnits(PathMetadata metadata) {
        super(UpbitOrderBookUnits.class, metadata);
    }

}

