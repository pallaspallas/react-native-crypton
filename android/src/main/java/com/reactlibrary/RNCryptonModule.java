
package com.reactlibrary;

import com.facebook.react.bridge.Promise;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;


//test
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.IvParameterSpec;

import java.io.UnsupportedEncodingException;
import android.util.Base64;

import java.security.SecureRandom;

//test

public class RNCryptonModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static final String Algoritm = "PBKDF2WithHmacSHA1";
  private static final String spl = "]";
  private static final int PKCS5_SALT_LENGTH = 8;
  private static SecureRandom random = new SecureRandom();
  private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
  //test
  public RNCryptonModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  public static byte[] generateIv(int length) {
    byte[] b = new byte[length];
    random.nextBytes(b);
    return b;
}

public static byte[] generateSalt() {
    byte[] b = new byte[PKCS5_SALT_LENGTH];
    random.nextBytes(b);
    return b;
}

  public static SecretKey createKey(byte[] salt, String password){
      try{
          int key_lenght = 256 ;
          int iteration = 1000;
          SecretKeyFactory factory = SecretKeyFactory.getInstance(Algoritm);
          KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iteration, key_lenght);
          byte[] keyBytes = factory.generateSecret(spec).getEncoded();
          return new SecretKeySpec(keyBytes, "AES");
      }catch (Exception e) {
          throw new RuntimeException(e);
      }
    
  }
  
  public static String bytesToHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for ( int j = 0; j < bytes.length; j++ ) {
        int v = bytes[j] & 0xFF;
        hexChars[j * 2] = hexArray[v >>> 4];
        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
}

  @ReactMethod
  public static void AES_CBC_256_encryption(String itext, String ikey, String iiv, Promise promise){
    try{
        byte[] iv           = iiv.getBytes("UTF-8");
        byte[] text         = itext.getBytes("UTF-8");
        byte[] key          = ikey.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(key,"AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] EnecyrptTextBytes = null;
        EnecyrptTextBytes = cipher.doFinal( text );
        String finalText = bytesToHex(EnecyrptTextBytes);
        promise.resolve( finalText );
    }catch(Exception e){
        promise.reject("error");
        e.printStackTrace();
    }
  }

  


  @ReactMethod
  public static void AES_CBC_256_pbkdf2_Decrypt(String ciphertext, String password, Promise promise) {
    if (ciphertext != null) {
        try {
            String[] fields     = ciphertext.split(spl);
            byte[] salt         = Base64.decode(fields[0] , Base64.DEFAULT);
            byte[] iv           = Base64.decode(fields[1] , Base64.DEFAULT);
            byte[] cipherBytes  = Base64.decode(fields[2] , Base64.DEFAULT);
            SecretKey key       = createKey(salt, password);
            Cipher cipher       = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] decyrptTextBytes = null;
            decyrptTextBytes = cipher.doFinal(cipherBytes);
            String text = new String(decyrptTextBytes);
            promise.resolve( text );
        } catch (Exception e) {
            promise.reject("err");
            e.printStackTrace();
        }
    }
}

  @Override
  public String getName() {
      return "RNCrypton";
  }

  @ReactMethod
  public static void Answer(String answer, Promise promise){
    promise.resolve(answer);
  }
}