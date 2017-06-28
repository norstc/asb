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
	
	
	// main part
	
	@RequestMapping("/stock/select")
	public String mainSelectHandler(){
		return "/stock/select";
	}
	
	@RequestMapping("/stock/target")
	public String mainTargetHandler(){
		return "/stock/target";
	}
	
	@RequestMapping("/stock/recorder")
	public String mainRecorderHandler(){
		return "/stock/recorder";
	}
	
	@RequestMapping("/stock/balance")
	public String mainBalanceHandler(){
		return "/stock/balance";
	}
	
	@RequestMapping("/stock/another")
	public String mainAnotherHandler(){
		return "/stock/another";
	}
	
	@RequestMapping("/stock/secret")
	public String mainSecretHandler(){
		return "/stock/secret";
	}
	
	
	//user part
	
	@RequestMapping("owner/login")
	public String userLoginHandler(){
		return "owner/login";
	}
	
	@RequestMapping("owner/logout")
	public String userLogoutHandler(){
		return "owner/logout";
	}
	
	@RequestMapping("owner/regist")
	public String userRegistHandler(){
		return "owner/regist";
	}
}
