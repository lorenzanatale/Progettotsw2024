package tswProj;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class Sicurezza {
	
	public static Optional<String> hashPassword(String password) {
		if (password == null || password.isEmpty()) {
	            return Optional.empty();
	        }
	    try {
	    	MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
	        byte[] hashBytes = messageDigest.digest(password.getBytes());

	        StringBuilder sb = new StringBuilder();
	        for (byte b : hashBytes) {
	                sb.append(String.format("%02x", b));
	        }

	        return Optional.of(sb.toString());
	        } catch (NoSuchAlgorithmException e) {
	            return Optional.empty();
	        }
	}

	public static boolean verifyPassword(String password, String hash) {
		if (hash == null) return false;
	    Optional<String> hashed = hashPassword(password);
	    return hashed.isPresent() && hashed.get().equals(hash);
	}
}