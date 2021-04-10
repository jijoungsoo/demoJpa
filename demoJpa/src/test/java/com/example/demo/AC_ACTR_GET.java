package com.example.demo;

import com.example.demo.utils.HttpUtil;

import org.junit.jupiter.api.Test;

public class AC_ACTR_GET {
	@Test
    void download() {
        //String tmp =httpGet("https://www.avdbs.com/");
        HttpUtil h = new HttpUtil();
        String tmp =h.httpGet("http://www.avdbs.com/menu/actor.php?actor_idx=6869");
        System.out.println(tmp);
    }
}
