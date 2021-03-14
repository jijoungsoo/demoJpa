package com.example.demo.db.domain.mig_av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMigAvMv is a Querydsl query type for MigAvMv
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMigAvMv extends EntityPathBase<MigAvMv> {

    private static final long serialVersionUID = -1810346568L;

    public static final QMigAvMv migAvMv = new QMigAvMv("migAvMv");

    public final StringPath actrNm = createString("actrNm");

    public final StringPath bestYn = createString("bestYn");

    public final StringPath comp_nm = createString("comp_nm");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath drctr = createString("drctr");

    public final NumberPath<Long> dvdIdx = createNumber("dvdIdx", Long.class);

    public final StringPath genLst = createString("genLst");

    public final StringPath imgA = createString("imgA");

    public final StringPath imgAs = createString("imgAs");

    public final StringPath imgN = createString("imgN");

    public final StringPath imgNs = createString("imgNs");

    public final StringPath lbl = createString("lbl");

    public final NumberPath<Long> mnActrIdx = createNumber("mnActrIdx", Long.class);

    public final StringPath mnActrNm = createString("mnActrNm");

    public final StringPath mvNm = createString("mvNm");

    public final StringPath openDt = createString("openDt");

    public final StringPath rnTm = createString("rnTm");

    public final StringPath series = createString("series");

    public final StringPath stryKr = createString("stryKr");

    public final StringPath sync = createString("sync");

    public final StringPath ttlKr = createString("ttlKr");

    public QMigAvMv(String variable) {
        super(MigAvMv.class, forVariable(variable));
    }

    public QMigAvMv(Path<? extends MigAvMv> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMigAvMv(PathMetadata metadata) {
        super(MigAvMv.class, metadata);
    }

}

