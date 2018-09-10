package com.coder520.common.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JackWangon[www.aiprogram.top] 2017/6/17.
 */
public class MD5Utils {



    public static String encryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        //消息摘要 Java内置
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String result = base64Encoder.encode(md5.digest(password.getBytes("utf-8")));

        return result;
    }

    public  static boolean  checkPassword(String inputPwd,String dbPwd) throws UnsupportedEncodingException, NoSuchAlgorithmException {
         String result = encryptPassword(inputPwd);
         if(result.equals(dbPwd)){
             return true;
         }else {
             return false;
         }
    }
}
