package com.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static String signKey = "vliQsTzi8aKxoDDtK4zYVwAhcUCWcQ3fwB8G6EursIQ=";
    private static Long expire = 43200000L; //有效时间为12个小时


    /**
     * 根据传入的payload构建jwt令牌
     * @param claims
     * @return String 一个字符串对象
     */
    public static String generateJwt(Map<String, Object> claims) {
        //初始化密钥
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(signKey));
        //开始构建JWT令牌
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,key)//设置JWT的签名算法部分：一个算法名称，一个算法密钥值
                .setClaims(claims)//设置JWT的载荷payload部分
                .setExpiration(new Date(System.currentTimeMillis() + expire ))//设置JWT令牌有效期为12个小时
                .compact();
        return jwt;
    }

    public static Claims parseJwt(String jwt) {
        //初始化密钥
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(signKey));
        //开始解析JWT令牌
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key) //设置验证签名的密钥
                .build() //构建解析器
                .parseClaimsJws(jwt); //解析并验证JWS（JWS即带签名的JWT）
        Claims claims = jws.getBody();
        return claims;
    }
}
