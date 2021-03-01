package com.example.demo.db.domain.kiw;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStReceiveMsg is a Querydsl query type for StReceiveMsg
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStReceiveMsg extends EntityPathBase<StReceiveMsg> {

    private static final long serialVersionUID = -1185484440L;

    public static final QStReceiveMsg stReceiveMsg = new QStReceiveMsg("stReceiveMsg");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final StringPath msg = createString("msg");

    public final StringPath rqNm = createString("rqNm");

    public final StringPath scrNo = createString("scrNo");

    public final StringPath stockCd = createString("stockCd");

    public final StringPath trcd = createString("trcd");

    public QStReceiveMsg(String variable) {
        super(StReceiveMsg.class, forVariable(variable));
    }

    public QStReceiveMsg(Path<? extends StReceiveMsg> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStReceiveMsg(PathMetadata metadata) {
        super(StReceiveMsg.class, metadata);
    }

}

