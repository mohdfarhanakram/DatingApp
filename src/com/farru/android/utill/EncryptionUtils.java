package com.farru.android.utill;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author vineet.kumar This class hold encryption related methods
 */
public class EncryptionUtils {

	/**
	 * method convert string to SHA-224, SHA-256, SHA-384, and SHA-512.
	 * 
	 * @param key
	 * @return
	 */
	public static String convertStringToSha(String key) {
		MessageDigest md;
		StringBuffer hexString = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update(key.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			// convert the byte to hex format method 2
			hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return hexString.toString();
	}
}
