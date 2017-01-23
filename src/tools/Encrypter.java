package tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {
	//用MD5算法加密字符串
	public static String md5Encrypt(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		//获得支持MD5算法的MessageDigest
		MessageDigest md5=MessageDigest.getInstance("MD5");
		//获得BASE64Encoder对象，用Base64格式对字符串进行编码
		sun.misc.BASE64Encoder base64Encoder=new sun.misc.BASE64Encoder();
		//对字符串进行加密，并将加密后的字符串按Base64格式进行编码，并将结果返回
		return base64Encoder.encode(md5.digest(s.getBytes("utf-8")));
	}
	
	

}
