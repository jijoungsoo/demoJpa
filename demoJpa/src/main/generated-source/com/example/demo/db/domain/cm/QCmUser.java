package com.example.demo.db.domain.cm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCmUser is a Querydsl query type for CmUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCmUser extends EntityPathBase<CmUser> {

    private static final long serialVersionUID = -309549435L;

    public static final QCmUser cmUser = new QCmUser("cmUser");

    public final DateTimePath<java.util.Date> crtDtm = createDateTime("crtDtm", java.util.Date.class);

    public final NumberPath<Long> crtUsrNo = createNumber("crtUsrNo", Long.class);

    public final StringPath email = createString("email");

    public final DateTimePath<java.util.Date> lstAccDtm = createDateTime("lstAccDtm", java.util.Date.class);

    public final StringPath rmk = createString("rmk");

    public final StringPath snsGubun = createString("snsGubun");

    public final StringPath snsId = createString("snsId");

    public final DateTimePath<java.util.Date> updtDtm = createDateTime("updtDtm", java.util.Date.class);

    public final NumberPath<Long> updtUsrNo = createNumber("updtUsrNo", Long.class);

    public final StringPath userId = createString("userId");

    public final StringPath userNm = createString("userNm");

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public final StringPath userPwd = createString("userPwd");

    public final StringPath useYn = createString("useYn");

    public QCmUser(String variable) {
        super(CmUser.class, forVariable(variable));
    }

    public QCmUser(Path<? extends CmUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCmUser(PathMetadata metadata) {
        super(CmUser.class, metadata);
    }

}

