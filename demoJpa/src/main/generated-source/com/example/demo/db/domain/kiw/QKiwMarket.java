package com.example.demo.db.domain.kiw;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QKiwMarket is a Querydsl query type for KiwMarket
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QKiwMarket extends EntityPathBase<KiwMarket> {

    private static final long serialVersionUID = 1225354892L;

    public static final QKiwMarket kiwMarket = new QKiwMarket("kiwMarket");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath marketCd = createString("marketCd");

    public final StringPath stockCd = createString("stockCd");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QKiwMarket(String variable) {
        super(KiwMarket.class, forVariable(variable));
    }

    public QKiwMarket(Path<? extends KiwMarket> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKiwMarket(PathMetadata metadata) {
        super(KiwMarket.class, metadata);
    }

}

