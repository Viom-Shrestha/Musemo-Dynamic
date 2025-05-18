package com.musemo.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for password encryption and decryption using AES with GCM and
 * PBKDF2 for key derivation from a password.
 * 
 * @author 23048612 Viom Shrestha
 */
public class PasswordUtil {
	private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

	private static final int TAG_LENGTH_BIT = 128; // Must be one of {128, 120, 112, 104, 96} for GCM
	private static final int IV_LENGTH_BYTE = 12; // Recommended IV length for GCM
	private static final int SALT_LENGTH_BYTE = 16; // Salt length for PBKDF2
	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	/**
	 * Generates a random nonce (number used once) of the specified size. Nonces are
	 * used to ensure the uniqueness of encryption operations.
	 *
	 * @param numBytes The desired length of the nonce in bytes.
	 * @return A byte array containing the random nonce.
	 */
	public static byte[] getRandomNonce(int numBytes) {
		byte[] nonce = new byte[numBytes];
		new SecureRandom().nextBytes(nonce);
		return nonce;
	}

	/**
	 * Generates a secret key for AES encryption of the specified key size.
	 *
	 * @param keysize The desired key size in bits (e.g., 128, 192, 256).
	 * @return A SecretKey object for AES.
	 * @throws NoSuchAlgorithmException If the "AES" algorithm is not available.
	 */
	public static SecretKey getAESKey(int keysize) throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(keysize, SecureRandom.getInstanceStrong());
		return keyGen.generateKey();
	}

	/**
	 * Derives an AES secret key from a password and a salt using
	 * PBKDF2WithHmacSHA256. This method is used to generate a strong encryption key
	 * from a user-provided password.
	 *
	 * @param password The user's password as a character array.
	 * @param salt     A random salt value used to prevent dictionary attacks.
	 * @return A SecretKey object for AES derived from the password and salt, or
	 *         null if an error occurs.
	 */
	public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt) {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			// iterationCount = 65536 (recommended minimum for strong security)
			// keyLength = 256 bits (common and secure key size for AES)
			KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
			SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
			return secret;
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvalidKeySpecException ex) {
			Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	/**
	 * Encrypts a password using AES/GCM/NoPadding. It generates a random salt and
	 * initialization vector (IV) for each encryption operation. The salt and IV are
	 * prepended to the ciphertext in the output. The final encrypted output is
	 * Base64 encoded for easy storage and transmission.
	 *
	 * @param username The username (used as the primary password for key
	 *                 derivation).
	 * @param password The password to be encrypted.
	 * @return A Base64 encoded string containing the IV, salt, and the encrypted
	 *         password, or null if an error occurs during encryption.
	 */
	public static String encrypt(String username, String password) {
		try {
			// Generate a random salt
			byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);

			// Generate a recommended 12-byte IV for GCM
			byte[] iv = getRandomNonce(IV_LENGTH_BYTE);

			// Derive the secret key from the username (acting as password) and salt
			SecretKey aesKeyFromPassword = getAESKeyFromPassword(username.toCharArray(), salt);

			// Get the Cipher instance for AES GCM without padding
			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

			// Initialize the cipher for encryption mode with the derived key and GCM
			// parameters
			cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

			// Encrypt the password
			byte[] cipherText = cipher.doFinal(password.getBytes(UTF_8));

			// Prefix the IV and salt to the ciphertext for decryption
			byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length).put(iv)
					.put(salt).put(cipherText).array();

			// Base64 encode the combined IV, salt, and ciphertext for string representation
			return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
		} catch (Exception ex) {
			Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, "Error during encryption", ex);
			return null;
		}
	}

	/**
	 * Decrypts a Base64 encoded ciphertext (which includes the IV and salt) using
	 * the provided username (acting as the password). It extracts the IV and salt
	 * from the beginning of the ciphertext, derives the decryption key, and then
	 * decrypts the actual ciphertext.
	 *
	 * @param encryptedPassword The Base64 encoded string containing the IV, salt,
	 *                          and the encrypted password.
	 * @param username          The username used during encryption (acting as the
	 *                          password for key derivation).
	 * @return The decrypted password as a String, or null if an error occurs during
	 *         decryption.
	 */
	public static String decrypt(String encryptedPassword, String username) {
		try {
			// Decode the Base64 encoded ciphertext
			byte[] decode = Base64.getDecoder().decode(encryptedPassword.getBytes(UTF_8));

			// Extract the IV from the beginning of the decoded byte array
			ByteBuffer bb = ByteBuffer.wrap(decode);
			byte[] iv = new byte[IV_LENGTH_BYTE];
			bb.get(iv);

			// Extract the salt immediately following the IV
			byte[] salt = new byte[SALT_LENGTH_BYTE];
			bb.get(salt);

			// The remaining bytes are the actual ciphertext
			byte[] cipherText = new byte[bb.remaining()];
			bb.get(cipherText);

			// Derive the AES key from the username and the extracted salt
			SecretKey aesKeyFromPassword = PasswordUtil.getAESKeyFromPassword(username.toCharArray(), salt);

			// Get the Cipher instance for AES GCM without padding
			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

			// Initialize the cipher for decryption mode with the derived key and GCM
			// parameters (using the extracted IV)
			cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

			// Decrypt the ciphertext
			byte[] plainText = cipher.doFinal(cipherText);

			// Return the decrypted password as a String
			return new String(plainText, UTF_8);
		} catch (Exception ex) {
			Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, "Error during decryption", ex);
			return null;
		}
	}
}