package com.example.demo.db.repository.mig_av;

import com.example.demo.db.domain.mig_av.MigAvMenu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigAvMenuRepository  extends JpaRepository< MigAvMenu,Long> {
	
}
