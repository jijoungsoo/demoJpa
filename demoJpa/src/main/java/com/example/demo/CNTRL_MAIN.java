package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.br.BR_MAIN;




@RestController 
public class CNTRL_MAIN {
	@Autowired
	private BR_MAIN BrM;

	@PostMapping(path= "/findMainMenu", consumes = "application/json", produces = "application/json")
	public String findMainMenu() throws Exception {
		String jsonOutString = BrM.findMainMenu();
		return jsonOutString;		
	}
	
	@PostMapping(path= "/findMainPgm", consumes = "application/json", produces = "application/json")
	public String findMainPgm() throws Exception {
		String jsonOutString = BrM.findMainPgm();
		return jsonOutString;		
	}

}

