package application;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Encryption {
	
	static String saltValue = null;
	
	public static String getSalt() {
		saltValue = EncryptionSetup.getSaltvalue(30);
		return saltValue;
	}
	
	public static String getPwd(String password) {
		String securePwd = EncryptionSetup.generatePwd(password, saltValue);
		return securePwd;
	}

	public static boolean verifiyPwd(String Pwd, String securePwd, String salt) {
		boolean finalval = false;
		String newSecurePwd = EncryptionSetup.generatePwd(Pwd, salt);
		finalval = newSecurePwd.equalsIgnoreCase(securePwd);
		
		return finalval;
	}
}

class EncryptionSetup{
	
	private static final Random random = new SecureRandom();
	private static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final int iterations = 10000;
	private static final int keylenght = 256;
	
	//Salt method
	public static String getSaltvalue(int lenght) {
		StringBuilder finalval = new StringBuilder(lenght);
		
		for(int i = 0; i < lenght; i++)
			finalval.append(characters.charAt(random.nextInt(characters.length())));
		
		return new String(finalval);
	}
	
	//Hash method
	public static byte[] hash(char[] password, byte[] salt) {
		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keylenght);
		Arrays.fill(password, Character.MIN_VALUE);
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");  
            return skf.generateSecret(spec).getEncoded();  
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException e)   {  
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);  
        }
		finally {
			spec.clearPassword();
		}
	}
	
	//Encryption
	public static String generatePwd(String password, String salt) {
		String finalval = null;
		byte[] securePwd = hash(password.toCharArray(), salt.getBytes());
		finalval = Base64.getEncoder().encodeToString(securePwd);
		return finalval;
	}
	
	//Verify Pwd
}