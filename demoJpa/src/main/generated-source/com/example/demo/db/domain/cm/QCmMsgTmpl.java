package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCmMsgTmpl is a Querydsl query type for CmMsgTmpl
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmMsgTmpl extends EntityPathBase<CmMsgTmpl> {

    private static final long serialVersionUID = 998533212L;

    public static final QCmMsgTmpl cmMsgTmpl = new QCmMsgTmpl("cmMsgTmpl");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath msg = createString("msg");

    public final StringPath msgTmplKindCd = createString("msgTmplKindCd");

    public final StringPath msgTmplStatusCd = createString("msgTmplStatusCd");

    public final StringPath rmk = createString("rmk");

    public final StringPath tmplPath = createString("tmplPath");

    public final NumberPath<Long> tmplSeq = createNumber("tmplSeq", Long.class);

    public final StringPath ttl = createString("ttl");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public QCmMsgTmpl(String variable) {
        super(CmMsgTmpl.class, forVariable(variable));
    }

    public QCmMsgTmpl(Path<? extends CmMsgTmpl> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmMsgTmpl(PathMetadata metadata) {
        super(CmMsgTmpl.class, metadata);
    }

}

