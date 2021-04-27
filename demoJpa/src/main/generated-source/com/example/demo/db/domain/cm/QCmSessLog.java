package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCmSessLog is a Querydsl query type for CmSessLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmSessLog extends EntityPathBase<CmSessLog> {

    private static final long serialVersionUID = 1639753912L;

    public static final QCmSessLog cmSessLog = new QCmSessLog("cmSessLog");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath ipaddr = createString("ipaddr");

    public final StringPath logType = createString("logType");

    public final NumberPath<Long> sessLogSeq = createNumber("sessLogSeq", Long.class);

    public final StringPath userId = createString("userId");

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public QCmSessLog(String variable) {
        super(CmSessLog.class, forVariable(variable));
    }

    public QCmSessLog(Path<? extends CmSessLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmSessLog(PathMetadata metadata) {
        super(CmSessLog.class, metadata);
    }

}

