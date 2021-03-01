package com.example.demo.db.domain.kiw;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStTheme is a Querydsl query type for StTheme
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStTheme extends EntityPathBase<StTheme> {

    private static final long serialVersionUID = 685276095L;

    public static final QStTheme stTheme = new QStTheme("stTheme");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Integer> ord = createNumber("ord", Integer.class);

    public final ListPath<StThemeStock, QStThemeStock> stThemeStocks = this.<StThemeStock, QStThemeStock>createList("stThemeStocks", StThemeStock.class, QStThemeStock.class, PathInits.DIRECT2);

    public final StringPath themeCd = createString("themeCd");

    public final StringPath themeNm = createString("themeNm");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public QStTheme(String variable) {
        super(StTheme.class, forVariable(variable));
    }

    public QStTheme(Path<? extends StTheme> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStTheme(PathMetadata metadata) {
        super(StTheme.class, metadata);
    }

}

