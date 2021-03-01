package com.example.demo.db.domain.kiw;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStThemeStock is a Querydsl query type for StThemeStock
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStThemeStock extends EntityPathBase<StThemeStock> {

    private static final long serialVersionUID = -856552809L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStThemeStock stThemeStock = new QStThemeStock("stThemeStock");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath stockCd = createString("stockCd");

    public final QStTheme stTheme;

    public final StringPath themeCd = createString("themeCd");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QStThemeStock(String variable) {
        this(StThemeStock.class, forVariable(variable), INITS);
    }

    public QStThemeStock(Path<? extends StThemeStock> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStThemeStock(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStThemeStock(PathMetadata metadata, PathInits inits) {
        this(StThemeStock.class, metadata, inits);
    }

    public QStThemeStock(Class<? extends StThemeStock> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.stTheme = inits.isInitialized("stTheme") ? new QStTheme(forProperty("stTheme")) : null;
    }

}

