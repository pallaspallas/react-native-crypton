
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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//test

public class RNCryptonModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static final String Algoritm_1 = "PBKDF2WithHmacSHA1";
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

public static byte[] generateSalt(int lenght) {
    byte[] b = new byte[lenght];
    random.nextBytes(b);
    return b;
}

  public static SecretKey createKey(byte[] salt, String password){
      try{
          int key_lenght = 256 ;
          int iteration = 1000;
          SecretKeyFactory factory = SecretKeyFactory.getInstance(Algoritm_1);
          KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iteration, key_lenght);
          byte[] keyBytes = factory.generateSecret(spec).getEncoded();
          return new SecretKeySpec(keyBytes, "AES");
      }catch (Exception e) {
          e.printStackTrace();
          return null;
      }
  }

  public static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                             + Character.digit(s.charAt(i+1), 16));
    }
    return data;
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
    public static void AES_CBC_256_decryption(String itext ,String ikey ,String iiv ,Promise promise){
        try{
            byte[] iv           = iiv.getBytes("UTF-8");
            byte[] text         = hexStringToByteArray(itext);
            byte[] key          = ikey.getBytes("UTF-8");
            SecretKeySpec secretKey = new SecretKeySpec(key,"AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] DecyrptTextBytes = null;
            DecyrptTextBytes = cipher.doFinal( text );
            String finalText = new String(DecyrptTextBytes);
            promise.resolve( finalText );
        }catch(Exception e){
              promise.reject("error");
              e.printStackTrace();
        }
    }

    @ReactMethod
    public static void AES_CBC_256_pbkdf2_Decrypt(String ciphertext, String password, String splitter, Promise promise) {
      if (ciphertext != null) {
          try {
              String my_splitter = "";
              if(splitter != null){
                my_splitter = splitter;
              }else{
                my_splitter = "]";
              }
              String[] fields     = ciphertext.split(my_splitter);
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
        
    @ReactMethod
    public static void AES_CBC_256_pbkdf2_Encrypt(String plaintext, String password, String splitter, Promise promise) {
      if (plaintext != null && password != null && splitter != null){
          try {
              byte[] salt = generateSalt(PKCS5_SALT_LENGTH);
              byte[] iv   = generateIv(16);
              byte[] text = plaintext.getBytes("UTF-8");
              SecretKey key = createKey(salt, password);
              Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
              cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
              byte[] EnecyrptTextBytes = null;
              EnecyrptTextBytes = cipher.doFinal( text );
              String final_res  = Base64.encodeToString(EnecyrptTextBytes , Base64.DEFAULT);
              String final_iv   = Base64.encodeToString(iv, Base64.DEFAULT);
              String final_salt = Base64.encodeToString(salt, Base64.DEFAULT);

              if (salt != null) {
                  promise.resolve( String.format("%s%s%s%s%s", final_salt, splitter,final_iv, splitter, final_res) );
              }else{
                  promise.resolve( String.format("%s%s%s"    , final_iv, splitter, final_res                     ) );
              }
              
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
  }
  

    //==================== HASHING ==================

    @ReactMethod
    public static void getHash(String text, String methode,Promise promise) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        promise.resolve(generateHash(text, methode));
    }

    @ReactMethod
    private static String generateHash(String text,String methode) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        try{
            MessageDigest md = MessageDigest.getInstance(methode);
            byte[] textBytes = text.getBytes("iso-8859-1");
            md.update(textBytes, 0, textBytes.length);
            byte[] hash = md.digest();
            return bytesToHex(hash) ;
        }catch(Exception e){
            e.printStackTrace();
            return "error";
        } 
    }


  @Override
  public String getName() {
      return "RNCrypton";
  }

}