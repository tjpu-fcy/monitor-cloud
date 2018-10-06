package com.jwt;


import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtToken {

    //加密密钥
    private static final String secret = "fdway.fcy.ssm";

    //sbject为用户账户，id为保存在redis中的用户key
    public static String createJWT(String subject,String id) {
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)    //创建时间
                .setSubject(subject)  //用户账户
                .setId(id)             //redisKey
                .signWith(signatureAlgorithm, signingKey);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    //解析Token，同时也能验证Token
    public static String parseJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(jwt).getBody();
       // System.out.println("Subject: " + claims.getSubject());
       // System.out.println("time: " + claims.getIssuedAt());
       // System.out.println("redisKey: " + claims.getId());
        String redisKey = claims.getId();
        return redisKey;

    }
}
