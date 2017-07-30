package com.norstc.asb.owner;

import org.apache.log4j.Logger;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService{
	private Logger log = Logger.getLogger(EncryptionServiceImpl.class);
	private StrongPasswordEncryptor strongEncryptor;
	
	@Autowired
	public void setStrongEncryptor(StrongPasswordEncryptor strongEncryptor){
		this.strongEncryptor = strongEncryptor;
	}

	@Override
	public String encryptString(String input) {
		
		return strongEncryptor.encryptPassword(input);
	}

	@Override
	public boolean checkPassword(String plainPassword, String encryptedPassword) {
		log.info("check password " + strongEncryptor.checkPassword(plainPassword, encryptedPassword));
		return strongEncryptor.checkPassword(plainPassword, encryptedPassword);
	}

}
