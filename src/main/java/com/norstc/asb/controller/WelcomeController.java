package com.norstc.asb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class WelcomeController {
	private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);
	
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
	
	@RequestMapping(value = "/owner/login", method =  RequestMethod.GET)
	public String userLoginHandler(){
		log.info("enter login form");
		return "owner/login";
	}
	
	
	
	@RequestMapping("/owner/logout")
	public String userLogoutHandler(){
		return "owner/logout";
	}
	
	@RequestMapping("/owner/regist")
	public String userRegistHandler(){
		return "owner/regist";
	}
}
