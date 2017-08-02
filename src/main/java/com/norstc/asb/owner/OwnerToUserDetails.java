package com.norstc.asb.owner;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class OwnerToUserDetails implements Converter<OwnerEntity, UserDetails>{
	private Logger log = Logger.getLogger(OwnerToUserDetails.class);
	@Override
	public UserDetails convert(OwnerEntity ownerEntity) {
		UserDetailsImpl userDetails  = new UserDetailsImpl();
		if(ownerEntity != null){
			log.info("ownerEntity.getUsername() is: " + ownerEntity.getUsername());
			log.info("getEncryptedPassword() is: " + ownerEntity.getEncryptedPassword());
			userDetails.setUsername(ownerEntity.getUsername());
			userDetails.setPassword(ownerEntity.getEncryptedPassword());
			userDetails.setEnabled(ownerEntity.getEnabled());
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			ownerEntity.getRoles().forEach(role -> {
				authorities.add(new SimpleGrantedAuthority(role.getRole()));
			});
			userDetails.setAuthorities(authorities);
			
		}
		return userDetails;
	}

}
