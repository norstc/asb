package com.norstc.asb.owner;

public interface EncryptionService {
	String encryptString(String input);
	boolean checkPassword(String plainPassword, String encryptedPassword);
}
