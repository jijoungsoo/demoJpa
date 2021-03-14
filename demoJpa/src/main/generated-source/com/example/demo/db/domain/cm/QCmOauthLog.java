package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCmOauthLog is a Querydsl query type for CmOauthLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmOauthLog extends EntityPathBase<CmOauthLog> {

    private static final long serialVersionUID = 1715392231L;

    public static final QCmOauthLog cmOauthLog = new QCmOauthLog("cmOauthLog");

    public final StringPath authId = createString("authId");

    public final StringPath brthday = createString("brthday");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath eml = createString("eml");

    public final StringPath gbnId = createString("gbnId");

    public final StringPath gndr = createString("gndr");

    public final StringPath nckNm = createString("nckNm");

    public final StringPath prfImg = createString("prfImg");

    public final NumberPath<Long> seqNo = createNumber("seqNo", Long.class);

    public final StringPath thmbImg = createString("thmbImg");

    public QCmOauthLog(String variable) {
        super(CmOauthLog.class, forVariable(variable));
    }

    public QCmOauthLog(Path<? extends CmOauthLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmOauthLog(PathMetadata metadata) {
        super(CmOauthLog.class, metadata);
    }

}

