package com.nhbs.fenxiao.http.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import android.util.Base64;

public class AesEncryptUtils {

  //可配置到Constant中，并读取配置文件注入,16位,自己定义
  private static final String KEY = "nhbs123456789123";

  //参数分别代表 算法名称/加密模式/数据填充方式
  private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

  /**
   * 加密
   * @param content 加密的字符串
   * @param encryptKey key值
   * @return
   * @throws Exception
   */
  public static String encrypt(String content, String encryptKey) throws Exception {
    KeyGenerator kgen = KeyGenerator.getInstance("AES");
    kgen.init(128);
    Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
    cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
    byte[] b = cipher.doFinal(content.getBytes("utf-8"));
    // 采用base64算法进行转码,避免出现中文乱码
    return  Base64.encodeToString(b, Base64.DEFAULT);

  }

  public static String encrypt(String content) throws Exception {
    return encrypt(content, KEY);
  }
  //public static String decrypt(String encryptStr) throws Exception {
  //  return decrypt(encryptStr, KEY);
  //}


  //public static void main(String[] args) throws Exception {
  //  Map map=new HashMap<String,String>();
  //  map.put("mobile","13534376664");
  //  String content = JSONObject.toJSONString(map);
  //  System.out.println("加密前：" + content);
  //
  //  String encrypt = encrypt(content, KEY);
  //  System.out.println("加密后：" + encrypt);
  //
  //  String decrypt = decrypt(encrypt, KEY);
  //  System.out.println("解密后：" + decrypt);
  //}
}