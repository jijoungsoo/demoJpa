package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmBoardGroup is a Querydsl query type for CmBoardGroup
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmBoardGroup extends EntityPathBase<CmBoardGroup> {

    private static final long serialVersionUID = -145899917L;

    public static final QCmBoardGroup cmBoardGroup = new QCmBoardGroup("cmBoardGroup");

    public final ListPath<CmBoard, QCmBoard> al_cmBoard = this.<CmBoard, QCmBoard>createList("al_cmBoard", CmBoard.class, QCmBoard.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath grpNm = createString("grpNm");

    public final NumberPath<Long> grpSeq = createNumber("grpSeq", Long.class);

    public final StringPath rmk = createString("rmk");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public final StringPath useYn = createString("useYn");

    public QCmBoardGroup(String variable) {
        super(CmBoardGroup.class, forVariable(variable));
    }

    public QCmBoardGroup(Path<? extends CmBoardGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmBoardGroup(PathMetadata metadata) {
        super(CmBoardGroup.class, metadata);
    }

}

