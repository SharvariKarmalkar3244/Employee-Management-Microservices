//package com.auth.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class JwtUtil {
//
//    public static final String SECRET_KEY = "secretKey";
//
//    public String generateToken(String username) {
//
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("email", "satish@gmail.com");
//
//        return Jwts.builder()
//                .subject(username)
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
//                .claims(claims)
//                .signWith(getKey())
//                .compact();
//    }
//
//    private Key getKey() {
//        byte[] bytes = Base64.getDecoder().decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(bytes);
//
//    }
//
//    private Claims getClaims(String token){
//        return Jwts.parser().verifyWith((SecretKey) getKey())
//                .build().parseSignedClaims(token)
//                .getPayload();
//
//    }
//
//    public Date getExpirationDate(String token){
//        return getClaims(token).getExpiration();
//    }
//}

package com.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Must be at least 32 characters for HS256
    private static final String SECRET_KEY =
            "ThisIsMySecretKeyForJwtAuthentication12345";

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(String username) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", "satish@gmail.com");

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(getKey())
                .compact();
    }

    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }
}
