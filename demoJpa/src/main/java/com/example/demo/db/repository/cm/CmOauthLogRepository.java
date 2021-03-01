package com.example.demo.db.repository.cm;

import com.example.demo.db.domain.cm.CmOauthLog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CmOauthLogRepository  extends JpaRepository< CmOauthLog, Long> {
	
}
