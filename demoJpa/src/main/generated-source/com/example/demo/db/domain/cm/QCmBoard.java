package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmBoard is a Querydsl query type for CmBoard
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmBoard extends EntityPathBase<CmBoard> {

    private static final long serialVersionUID = -1023767700L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCmBoard cmBoard = new QCmBoard("cmBoard");

    public final ListPath<CmBoardCmt, QCmBoardCmt> al_cmBoardCmt = this.<CmBoardCmt, QCmBoardCmt>createList("al_cmBoardCmt", CmBoardCmt.class, QCmBoardCmt.class, PathInits.DIRECT2);

    public final NumberPath<Long> brdDpth = createNumber("brdDpth", Long.class);

    public final NumberPath<Long> brdRplyOrd = createNumber("brdRplyOrd", Long.class);

    public final NumberPath<Long> brdSeq = createNumber("brdSeq", Long.class);

    public final StringPath cntnt = createString("cntnt");

    public final StringPath cntntText = createString("cntntText");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath delYn = createString("delYn");

    public final NumberPath<Long> dislkCnt = createNumber("dislkCnt", Long.class);

    public final NumberPath<Long> grpSeq = createNumber("grpSeq", Long.class);

    public final NumberPath<Long> lkCnt = createNumber("lkCnt", Long.class);

    public final QCmBoardGroup orfCmBoardGroup;

    public final NumberPath<Long> prntBrdSeq = createNumber("prntBrdSeq", Long.class);

    public final NumberPath<Long> rootBrdSeq = createNumber("rootBrdSeq", Long.class);

    public final StringPath ttl = createString("ttl");

    public final StringPath ttlText = createString("ttlText");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QCmBoard(String variable) {
        this(CmBoard.class, forVariable(variable), INITS);
    }

    public QCmBoard(Path<? extends CmBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCmBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCmBoard(PathMetadata metadata, PathInits inits) {
        this(CmBoard.class, metadata, inits);
    }

    public QCmBoard(Class<? extends CmBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orfCmBoardGroup = inits.isInitialized("orfCmBoardGroup") ? new QCmBoardGroup(forProperty("orfCmBoardGroup")) : null;
    }

}

