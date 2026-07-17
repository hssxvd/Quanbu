package com.qst.financing.security.util;

import com.qst.financing.security.entity.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class JwtTokenUtil {
    private static final String SECRET_STRING = "qst12345678901234567890123456789012345678901234567890123456789012";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
    private static final Long expiration = 3600000L;
    private static final String header = "Authorization";

    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public static String getHeader() {
        return header;
    }

    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println("JwtTokenUtil - 解析Token失败: " + e.getClass().getName() + " - " + e.getMessage());
            return null;
        }
    }

    public String generateToken(UserDetails userDetails) {
        JwtUser jwtUser = (JwtUser) userDetails;
        Map<String, Object> claims = new HashMap<>(4);
        claims.put(Claims.SUBJECT, userDetails.getUsername());
        claims.put(Claims.ISSUED_AT, new Date());
        claims.put("username", jwtUser.getUsername());
        claims.put("nickname", jwtUser.getNickname());
        claims.put("role", jwtUser.getAuthorities());
        return generateToken(claims);
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims != null ? claims.getSubject() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return true;
            }
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public String refreshToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            claims.put(Claims.ISSUED_AT, new Date());
            return generateToken(claims);
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        String username = getUsernameFromToken(token);
        return username != null && username.equals(user.getUsername()) && !isTokenExpired(token);
    }
}