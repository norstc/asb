package com.norstc.asb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
	@RequestMapping("/")
	public String welcomeHandler(){
		return "welcome";
	}
	
	@RequestMapping("/index")
	public String indexHandler(){
		return "index";
	}
	
	@RequestMapping("/temp")
	public String tempHandler(){
		return "temp";
	}
	
	@RequestMapping("/aboutus")
	public String aboutUsHandler(){
		return "aboutus";
	}
	
	@RequestMapping("/help")
	public String helpHandler(){
		return "help";
	}
	
	@RequestMapping("/main/select")
	public String mainSelectHandler(){
		return "main/select";
	}
	
	@RequestMapping("/main/target")
	public String mainTargetHandler(){
		return "main/target";
	}
	
	@RequestMapping("main/recorder")
	public String mainRecorderHandler(){
		return "main/recorder";
	}
	
	@RequestMapping("main/balance")
	public String mainBalanceHandler(){
		return "main/balance";
	}
	
	@RequestMapping("main/another")
	public String mainAnotherHandler(){
		return "main/another";
	}
	
	@RequestMapping("main/secret")
	public String mainSecretHandler(){
		return "main/secret";
	}
	
	@RequestMapping("user/login")
	public String userLoginHandler(){
		return "user/login";
	}
	
	@RequestMapping("user/logout")
	public String userLogoutHandler(){
		return "user/logout";
	}
	
	@RequestMapping("user/regist")
	public String userRegistHandler(){
		return "user/regist";
	}
}
