package com.norstc.asb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
	@RequestMapping("/")
	public String homeHandler(){
		return "redirect:/welcome";
	}
	
	@RequestMapping("/welcome")
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
