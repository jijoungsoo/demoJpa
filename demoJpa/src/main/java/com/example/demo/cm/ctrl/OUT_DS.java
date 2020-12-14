package com.example.demo.cm.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cm.anotation.OpService;
import com.example.demo.cm.da.DA_CM_LOGIN;
import com.example.demo.domain.CmUser;
import com.example.demo.exception.BizException;

public class OUT_DS extends LinkedHashMap<String, ArrayList<HashMap<String,Object>>> {
	
	/*맵의 마지막은 Object 로 했다.   클라이언트에서    tree구현하는게 너무힘들다.. ObjectMapping하기에는 타입이 Ojbect인게 편하다.*/

}


