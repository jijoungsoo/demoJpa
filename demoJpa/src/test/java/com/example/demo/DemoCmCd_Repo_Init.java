package com.example.demo;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.db.domain.cm.CmCd;
import com.example.demo.db.domain.cm.CmDomain;
import com.example.demo.db.domain.cm.CmGrpCd;
import com.example.demo.db.domain.cm.CmMenu;
import com.example.demo.db.domain.cm.CmPgm;
import com.example.demo.db.repository.cm.CmCdRepository;
import com.example.demo.db.repository.cm.CmDomainRepository;
import com.example.demo.db.repository.cm.CmGrpCdRepository;
import com.example.demo.db.repository.cm.CmMenuRepository;
import com.example.demo.db.repository.cm.CmPgmRepository;
import com.example.demo.db.repository.cm.CmUserRepository;

@SpringBootTest(properties = "classpath:/application.yml")  /*https://velog.io/@hellozin/Spring-Boot-Test에서-Yaml-프로퍼티-적용하기*/
@Transactional
@Rollback(false)  /*롤백하지 않게 하자. */
/*@ContextConfiguration(loader = AnnotationConfigContextLoader.class) */ /*이거하면 임시 디비가 만들어지는 듯하다. --위에 실db쓰는건 h2 특성상 2개를 못여는 듯한다. 서버 모드 안됨 */		
class DemoCmCd_Repo_Init {
	
	@Autowired
	DataLoader t;
		
	@Test
	void insertGrpCd() {
		t.removeData();
		t.loadData();
	}
	

}
