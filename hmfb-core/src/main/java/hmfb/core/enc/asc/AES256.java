package hmfb.core.enc.asc;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import hmfb.core.exception.ErrorCode;
import hmfb.core.exception.HmfbException;

/**

 * @FileName : AES256.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : AES256 클레스

 * @변경이력 :

 */

public class AES256 {
	
	@Autowired
    private MessageSource messageSource;
	
	public static String alg = "AES/CBC/PKCS5Padding";
    private String key;
    private String iv;
    
    /**
	
	  * @Method Name : AES256
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : AES256 생성자
	
	  * @변경이력 : 
	
	  */
    public AES256(String key) {
		this.key = key;
		iv = key.substring(0, 16); // 16byte
	}
    
    /**
	
	  * @Method Name : encrypt
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : AES256 암호화 처리
	
	  * @변경이력 : 
	
	  */
    public String encrypt(String text) throws HmfbException {
    	String encryptText = "";
		try {
			Cipher cipher = Cipher.getInstance(alg);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
	        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
	        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
	        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
	        encryptText =  Base64.getEncoder().encodeToString(encrypted);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			throw new HmfbException(messageSource.getMessage(ErrorCode.E105.getMessage(), new Object[] {}, Locale.getDefault()));
		}
        return encryptText;
    }
    
    /**
	
	  * @Method Name : encrypt
	
	  * @작성자 : 송원호
	
	  * @작성일 : 2022. 12. 28
	
	  * @Method 설명 : AES256 복호화 처리
	
	  * @변경이력 : 
	
	  */
    public String decrypt(String cipherText) throws HmfbException {
        String decryptText = "";
		try {
			Cipher cipher = Cipher.getInstance(alg);
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
	        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
			byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
	        byte[] decrypted = cipher.doFinal(decodedBytes);
	        decryptText =  new String(decrypted, "UTF-8");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			throw new HmfbException(messageSource.getMessage(ErrorCode.E106.getMessage(), new Object[] {}, Locale.getDefault()));
		}
        return decryptText;
    }
    
}
