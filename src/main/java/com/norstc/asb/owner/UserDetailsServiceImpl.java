package com.norstc.asb.owner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{
	private OwnerService ownerService;
	private Converter<OwnerEntity, UserDetails> ownerUserDetailsConverter;
	private Logger log = Logger.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	public void setOwnerService(OwnerService ownerService){
		this.ownerService = ownerService;
	}
	
	@Autowired
	@Qualifier(value = "ownerToUserDetails")
	public void setOwnerUserDetailsConverter(Converter<OwnerEntity, UserDetails> ownerUserDetailsConverter){
		this.ownerUserDetailsConverter = ownerUserDetailsConverter;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("UserDetailsService loadUserByUsername: " + username);
		return ownerUserDetailsConverter.convert(ownerService.findByUsername(username));
	}

}
