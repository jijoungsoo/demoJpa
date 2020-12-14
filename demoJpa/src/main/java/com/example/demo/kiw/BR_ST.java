package com.example.demo.kiw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CmUser;

@Service
public class BR_ST {
	@Autowired
	private DA_ST daKiw;

	public String kiw2100_findThemeCd() {
		// TODO Auto-generated method stub
		CmUser c =daKiw.kiw2100_findThemeCd();
		return null;
	}
	
	
}
