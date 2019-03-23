package com.wende.spring.boot.wenblog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public class ParseTool {
    private static final String SECRET = UUID.randomUUID().toString();

    public static Date parseStringToDate(String format, String date){
        if(date == null || date.equals("")){
            return null;
        }
        if(format == null || format.equals("")){
            format="yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date result = null;
        try {
            result = sdf.parse(date);
        }catch (ParseException e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    public static Date parseStringToDate(String date){
        return parseStringToDate("",date);
    }

    public static boolean isDataInRange(String date,int range){
        return isDataInRange(parseStringToDate(date),range);
    }

    public static boolean isDataInRange(Date date, int range){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR,range);
        Calendar equalCalc = Calendar.getInstance();
        equalCalc.setTime(date);
        return calendar.before(equalCalc);
    }

    public static Date timestampToDate(Timestamp timestamp){
        return new Date(timestamp.getTime());
    }

    public static Timestamp dateToTimestamp(Date date){
        return new Timestamp(date.getTime());
    }

    public static String createToken(long userId){
        String token = null;
        if(userId != 0 ){
            Map<String,Object> header = new HashMap<String, Object>();
            header.put("alg","HS256");
            header.put("typ","JWT");
            Date issueAt = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH,3);
            Date expiresAt = calendar.getTime();
            token = JWT.create().withHeader(header).
                        withClaim("id",userId+"").
                        withExpiresAt(expiresAt).withIssuedAt(issueAt).
                        sign(Algorithm.HMAC256(SECRET));
        }
        return token;
    }

    public static String verifyToken(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = null;
        try{
            decodedJWT = verifier.verify(token);
        }catch (TokenExpiredException e){
            e.printStackTrace();
        }finally {
            if(decodedJWT == null){
                return null;
            }else {
                return decodedJWT.getClaim("id").asString();
            }
        }
    }

    /**
     *<p>Return string's length when contain Chinese
     *<p>But other languages with a single character length greater than 1 are not considered.
     * @param s
     * @return string's length when contain Chinese
     */
    public static long getStringByteLength(String s){
        long length = 0;
        char[] chars = s.toCharArray();
        for(int i=0;i<s.length();i++){
            char c = chars[i];
            if(c >= 0x0391 && c <= 0xFFE5){
                length += 2;
            }else {
                length += 1;
            }
        }
        return length;
    }

    public static String encodePassword(String password){
        String encodePassword = null;
        byte[] base64Pwd = Base64.getEncoder().encode(password.getBytes());

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] md5Pwd = messageDigest.digest(base64Pwd);
            encodePassword = "";
            for(byte b:md5Pwd){
                encodePassword+=b;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }finally {
            return encodePassword;
        }
    }

    public static boolean verifyPassword(String password){
        return verifyPassword(password,"(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}");
    }

    public static boolean verifyPassword(String password,String regular){
        return Pattern.matches(regular,password);
    }
}
