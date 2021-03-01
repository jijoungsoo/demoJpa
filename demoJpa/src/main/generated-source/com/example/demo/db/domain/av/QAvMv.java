package com.example.demo.db.domain.av;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAvMv is a Querydsl query type for AvMv
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAvMv extends EntityPathBase<AvMv> {

    private static final long serialVersionUID = -190345469L;

    public static final QAvMv avMv = new QAvMv("avMv");

    public final StringPath avNm = createString("avNm");

    public final NumberPath<Long> avSeq = createNumber("avSeq", Long.class);

    public final StringPath cntnt = createString("cntnt");

    public final StringPath cptnYn = createString("cptnYn");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final NumberPath<Long> dslkCnt = createNumber("dslkCnt", Long.class);

    public final NumberPath<Integer> lkCnt = createNumber("lkCnt", Integer.class);

    public final StringPath mkDt = createString("mkDt");

    public final StringPath mscCd = createString("mscCd");

    public final StringPath ord = createString("ord");

    public final StringPath rmk = createString("rmk");

    public final StringPath ttl = createString("ttl");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public final StringPath vrYn = createString("vrYn");

    public QAvMv(String variable) {
        super(AvMv.class, forVariable(variable));
    }

    public QAvMv(Path<? extends AvMv> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAvMv(PathMetadata metadata) {
        super(AvMv.class, metadata);
    }

}

