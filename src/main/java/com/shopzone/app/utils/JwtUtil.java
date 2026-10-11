package com.shopzone.app.utils;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.shopzone.app.entity.User;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private final String SecretKey = "whateveritis";


    // =====================================================
    // GENERATE TOKEN
    // =====================================================

    public String generateToken(User user) {

    	JwtBuilder builder = Jwts.builder()
    	        .setSubject(user.getUsername())
    	        .claim("role", user.getRole());
        // =================================================
        // MERCHANT ID
        // =================================================

        if (user.getMerchant() != null) {

            builder.claim(
                    "merchantId",
                    user.getMerchant().getId()
            );
        }


        // =================================================
        // ISSUED TIME
        // =================================================

        builder.setIssuedAt(new Date());


        // =================================================
        // ROLE BASED EXPIRATION
        // =================================================

        String role = user.getRole();

        if ("ADMIN".equalsIgnoreCase(role)
                || "MERCHANT_ADMIN".equalsIgnoreCase(role)
                || "DELIVERY_BOY".equalsIgnoreCase(role)) {

            builder.setExpiration(
                    new Date(
                            System.currentTimeMillis()
                                    + 1000L * 60L * 30L
                    )
            );
        }


        // =================================================
        // SIGN TOKEN
        // =================================================

        return builder
                .signWith(
                        SignatureAlgorithm.HS256,
                        SecretKey
                )
                .compact();
    }


    // =====================================================
    // EXTRACT USERNAME
    // =====================================================

    public String extractUsername(String token) {

        return Jwts.parser()
                .setSigningKey(SecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    // =====================================================
    // EXTRACT ROLE
    // =====================================================

    public String extractRole(String token) {

        return Jwts.parser()
                .setSigningKey(SecretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }


    // =====================================================
    // EXTRACT MERCHANT ID
    // =====================================================

    public Long extractMerchantId(String token) {

        Object merchantId =
                Jwts.parser()
                        .setSigningKey(SecretKey)
                        .parseClaimsJws(token)
                        .getBody()
                        .get("merchantId");

        if (merchantId == null) {
            return null;
        }

        return ((Number) merchantId).longValue();
    }


    // =====================================================
    // VALIDATE TOKEN
    // =====================================================

    public boolean validateToken(
            String token,
            UserDetails userDetails) {

        return extractUsername(token)
                .equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }


    // =====================================================
    // CHECK TOKEN EXPIRATION
    // =====================================================

    private boolean isTokenExpired(String token) {

        Date expiration =
                Jwts.parser()
                        .setSigningKey(SecretKey)
                        .parseClaimsJws(token)
                        .getBody()
                        .getExpiration();


        // ================================================
        // NORMAL USER
        // No expiration claim
        // ================================================

        if (expiration == null) {
            return false;
        }


        // ================================================
        // ADMIN / MERCHANT ADMIN / DELIVERY BOY
        // ================================================

        return expiration.before(new Date());
    }
}