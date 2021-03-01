package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmBoardCmt is a Querydsl query type for CmBoardCmt
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmBoardCmt extends EntityPathBase<CmBoardCmt> {

    private static final long serialVersionUID = -500713922L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCmBoardCmt cmBoardCmt = new QCmBoardCmt("cmBoardCmt");

    public final NumberPath<Long> brdSeq = createNumber("brdSeq", Long.class);

    public final NumberPath<Long> cmtDpth = createNumber("cmtDpth", Long.class);

    public final NumberPath<Long> cmtRplyOrd = createNumber("cmtRplyOrd", Long.class);

    public final NumberPath<Long> cmtSeq = createNumber("cmtSeq", Long.class);

    public final StringPath cntnt = createString("cntnt");

    public final StringPath cntntText = createString("cntntText");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath delYn = createString("delYn");

    public final NumberPath<Long> dislkCnt = createNumber("dislkCnt", Long.class);

    public final NumberPath<Long> lkCnt = createNumber("lkCnt", Long.class);

    public final QCmBoard orfCmBoard;

    public final NumberPath<Long> prntCmtSeq = createNumber("prntCmtSeq", Long.class);

    public final NumberPath<Long> rootCmtSeq = createNumber("rootCmtSeq", Long.class);

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QCmBoardCmt(String variable) {
        this(CmBoardCmt.class, forVariable(variable), INITS);
    }

    public QCmBoardCmt(Path<? extends CmBoardCmt> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCmBoardCmt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCmBoardCmt(PathMetadata metadata, PathInits inits) {
        this(CmBoardCmt.class, metadata, inits);
    }

    public QCmBoardCmt(Class<? extends CmBoardCmt> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orfCmBoard = inits.isInitialized("orfCmBoard") ? new QCmBoard(forProperty("orfCmBoard"), inits.get("orfCmBoard")) : null;
    }

}

