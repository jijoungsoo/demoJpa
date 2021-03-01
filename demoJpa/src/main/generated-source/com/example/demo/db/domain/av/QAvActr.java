package com.example.demo.db.domain.av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAvActr is a Querydsl query type for AvActr
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAvActr extends EntityPathBase<AvActr> {

    private static final long serialVersionUID = 1761225978L;

    public static final QAvActr avActr = new QAvActr("avActr");

    public final StringPath actrNmEng = createString("actrNmEng");

    public final StringPath actrNmJp = createString("actrNmJp");

    public final StringPath actrNmKr = createString("actrNmKr");

    public final NumberPath<Long> actrSeq = createNumber("actrSeq", Long.class);

    public final StringPath birthDt = createString("birthDt");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath mscYn = createString("mscYn");

    public final NumberPath<Integer> ord = createNumber("ord", Integer.class);

    public final StringPath rmk = createString("rmk");

    public final NumberPath<Integer> rnk = createNumber("rnk", Integer.class);

    public final StringPath sex = createString("sex");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QAvActr(String variable) {
        super(AvActr.class, forVariable(variable));
    }

    public QAvActr(Path<? extends AvActr> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAvActr(PathMetadata metadata) {
        super(AvActr.class, metadata);
    }

}

