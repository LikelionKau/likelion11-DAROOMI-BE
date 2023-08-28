package com.likelion.daroomi.nuroomi.config;

import com.likelion.daroomi.nuroomi.dto.LoginRequestDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private static final String jwtSecretKey = "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";

    //TODO Payload 내용 더 추가하기
    public static String generateJwtToken(LoginRequestDto loginRequestDto) {

        // 사용자 시퀀스를 기준으로 JWT 토큰을 발급하여 반환해줍니다.
        JwtBuilder builder = Jwts.builder()
            .setHeader(createHeader())
            .setClaims(createClaims(loginRequestDto))
            .setSubject(String.valueOf(loginRequestDto.getLoginId()))
            .signWith(SignatureAlgorithm.HS256, createSignature())
            .setExpiration(createExpiredDate());
        return builder.compact();
    }

    public static String parseTokenToUserInfo(String token, String jwtSecretKey) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
            .setSigningKey(jwtSecretKey)
            .build()
            .parseClaimsJws(token);

        Claims body = claimsJws.getBody();
        return body.getSubject();
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);

            return true;
        } catch (JwtException | NullPointerException exception) {
            return false;
        }
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    private static Date createExpiredDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 30);     // 30분
        return c.getTime();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private static Map<String, Object> createClaims(LoginRequestDto loginRequestDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", loginRequestDto.getLoginId());
        return claims;
    }

    private static Key createSignature() {
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(apiKeySecretBytes);
    }

    private static Claims getClaimsFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(
            Base64.getDecoder().decode(jwtSecretKey));
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims;
    }

    public static String getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("userId").toString();
    }
}
