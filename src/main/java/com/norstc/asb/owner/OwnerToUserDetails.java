package com.norstc.asb.owner;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class OwnerToUserDetails implements Converter<OwnerEntity, UserDetails>{

	@Override
	public UserDetails convert(OwnerEntity ownerEntity) {
		UserDetailsImpl userDetails  = new UserDetailsImpl();
		if(ownerEntity != null){
			userDetails.setUsername(ownerEntity.getName());
			userDetails.setPassword(ownerEntity.getPassword());
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
