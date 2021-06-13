package com.example.demo.db.domain.upbit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpbitOrderBook is a Querydsl query type for UpbitOrderBook
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUpbitOrderBook extends EntityPathBase<UpbitOrderBook> {

    private static final long serialVersionUID = 385114965L;

    public static final QUpbitOrderBook upbitOrderBook = new QUpbitOrderBook("upbitOrderBook");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath market = createString("market");

    public final NumberPath<Long> timestamp = createNumber("timestamp", Long.class);

    public final NumberPath<Double> totalAskSize = createNumber("totalAskSize", Double.class);

    public final NumberPath<Double> totalBidSize = createNumber("totalBidSize", Double.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QUpbitOrderBook(String variable) {
        super(UpbitOrderBook.class, forVariable(variable));
    }

    public QUpbitOrderBook(Path<? extends UpbitOrderBook> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpbitOrderBook(PathMetadata metadata) {
        super(UpbitOrderBook.class, metadata);
    }

}

