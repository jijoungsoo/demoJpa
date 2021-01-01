package com.example.demo.db.repository.cm;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.db.domain.cm.CmFavMenu;

public interface CmFavMneuRepository extends JpaRepository< CmFavMenu, Long> {
	
}
