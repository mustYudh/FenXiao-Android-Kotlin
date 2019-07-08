package com.nhbs.fenxiao.http.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
    byte[] b = cipher.doFinal(content.getBytes("UTF-8"));
    return  Base64.encodeToString(b, Base64.NO_WRAP);

  }

  public static String encrypt(String content) throws Exception {
    return encrypt(content, KEY);
  }





  public static String aesEncrypt(String sSrc, String encodingFormat, String algorithm, String sKey, String ivParameter) throws Exception {
    Cipher cipher = Cipher.getInstance(algorithm);
    byte[] raw = sKey.getBytes(encodingFormat);
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    //使用CBC模式，需要一个向量iv，可增加加密算法的强度
    IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes(encodingFormat));
    if (algorithm.contains("CBC")) {
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
    } else {
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    }
    byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
    //此处使用BASE64做转码。
    return Base64.encodeToString(encrypted, Base64.DEFAULT);
  }
}