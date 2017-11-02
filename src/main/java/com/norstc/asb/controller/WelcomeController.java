package com.norstc.asb.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.norstc.asb.owner.OwnerEntity;
import com.norstc.asb.owner.OwnerService;
import com.norstc.asb.owner.RoleEntity;
import com.norstc.asb.owner.RoleService;


@Controller
public class WelcomeController {
	private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);
	private OwnerService ownerService;
	private RoleService roleService;
	@Autowired
	public void setOwnerService(OwnerService ownerService){
		this.ownerService = ownerService;
	}
	
	@Autowired
	public void setRoleService(RoleService roleService){
		this.roleService= roleService;
	}
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
	//登录页面GET
	@RequestMapping(value = "/owner/login", method =  RequestMethod.GET)
	public String userLoginHandler(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		if(username.equals("anonymousUser")){
			log.info("enter login form");
			return "owner/login";
		}else{
			log.info("have logged in as :  " + username);
			return "welcome";
		}
		
	}
	
	//登出页面
	
	@RequestMapping("/owner/logout")
	public String userLogoutHandler(){
		return "owner/logout";
	}
	
	//注册用户
	//显示表单
	@RequestMapping(value = "/owner/regist", method = RequestMethod.GET)
	public String userRegistHandler(Map<String, Object> model){
		OwnerEntity ownerEntity = new OwnerEntity();
		model.put("ownerEntity",  ownerEntity);
		return "owner/regist";
	}
	//提交表单
	@RequestMapping(value  = "/owner/regist", method = RequestMethod.POST)
	public String processRegist(@Valid OwnerEntity ownerEntity, BindingResult result){
		
		
		if(result.hasErrors()){
			log.info("form has error : " + result.toString());
			return "owner/regist";
		}else{
			if(ownerEntity.getPassword().equals(ownerEntity.getConfirmPassword())){
				ownerEntity.setCashLeft(ownerEntity.getCashStart());
				ownerEntity.setMarketLeft(new BigDecimal(0));
				ownerEntity.setCashProfit(new BigDecimal(0));
				
				//注册的帐号全部是'USER'角色
				RoleEntity roleEntity = roleService.getById(2);
				List<RoleEntity> roles = new ArrayList<>();
				roles.add(roleEntity);
				ownerEntity.setRoles(roles);
				
				//注册的帐号全部是1级
				ownerEntity.setOwnerLevel(1);
				
				this.ownerService.saveOrUpdate(ownerEntity);
				log.info("add new owner: " + ownerEntity.getUsername());
				return "redirect:/welcome";
			}else{
				//ObjectError error = new ObjectError("confirmPassword", "should be same with password");
				//result.addError(error);
				result.rejectValue("confirmPassword", "error.ownerEntity","confirmPassword should be same as password");
				return "owner/regist";
			}
			
		}
	}
	
	
	//修改密码
	//修改密码GET
	@RequestMapping(value="/owner/editpasswd", method = RequestMethod.GET)
	public String editpasswdHandler(Map<String,Object> model, Principal principal){
		String username = principal.getName();
		OwnerEntity ownerEntity = ownerService.findByUsername(username);
		
		model.put("ownerEntity", ownerEntity);
		return "owner/editpasswd";
	}
	//修改密码POST
	@RequestMapping(value="/owner/editpasswd",method = RequestMethod.POST)
	public String editpasswdProcess(@Valid OwnerEntity ownerEntity, BindingResult result, Principal principal){
		String username = principal.getName();
		OwnerEntity oldOwnerEntity = ownerService.findByUsername(username);
		if(result.hasErrors()){
			log.info("editpasswd process failed: " + result.toString());
			return "owner/editpasswd";
		}else{
			if(ownerEntity.getPassword().equals(ownerEntity.getConfirmPassword())){
				oldOwnerEntity.setPassword(ownerEntity.getPassword());
				this.ownerService.saveOrUpdate(oldOwnerEntity);
				log.info("update owner: " + oldOwnerEntity.getUsername());
				return "redirect:/stock/target/";
			}else{
				result.rejectValue("confirmPassword", "error.ownerEntity","confirmPassword should be same as password");
				return "redirect:/stock/target/";
			}
			
		}
		
	}
}
