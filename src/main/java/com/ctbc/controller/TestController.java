package com.ctbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	
	
	public TestController() {
		System.out.println("TestController 建立");
	}

	@RequestMapping("/testSpringMvc")
	public String testSpringMvc() {
		System.out.println("=========== testSpringMvc() ===========");
		return "success";
	}
	
}
