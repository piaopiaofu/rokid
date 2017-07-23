package com.rokid.soa.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: peltason
 * Date: 12-5-9
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 */
public class MD5Util {

    private static final Log logger = LogFactory.getLog(MD5Util.class);

    private static final int LO_BYTE = 0x0f;
    private static final int MOVE_BIT = 4;
    private static final int HI_BYTE = 0xf0;

    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            buf.append(byteToHexString(b[i]));
        }
        return buf.toString();
    }

    private static String byteToHexString(byte b) {
        return HEX_DIGITS[(b & HI_BYTE) >> MOVE_BIT] + HEX_DIGITS[b & LO_BYTE];
    }

    /**
     * md5
     *
     * @param origin
     * @return
     */
    public static String MD5(String origin) {

        String resultString = null;

        resultString = new String(origin);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString
                    .getBytes()));
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error in MD5Util.md5:" + e);
        }
        return resultString;

    }


    /**
     * hmac 加密
     * @param data
     * @param secret
     * @return
     * @throws IOException
     */
    public static String hmac(String data, String secret) throws IOException {
        byte[] bytes = null;
        try{
            SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse){
            logger.error(gse);
            throw new IOException(gse);
        }
        return byte2hex(bytes);
    }

    /**
     *  把二进制转化为大写的十六进制
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes){
        StringBuilder sign = new StringBuilder();
        for(int i = 0;i < bytes.length; i++){
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() == 1){
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }


    public static void main(String[] args){
        try {
            System.out.println("的是非得失法");
            System.out.println(hmac("secret","admin"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
