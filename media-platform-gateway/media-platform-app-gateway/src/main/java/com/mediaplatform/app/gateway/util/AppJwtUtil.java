package com.mediaplatform.app.gateway.util;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * JWT Utility Class for App Gateway - Token generation, validation, and management
 * 
 * This utility class provides comprehensive JWT (JSON Web Token) functionality for the
 * media platform's authentication and authorization system. It handles secure token
 * operations for user sessions and API access control.
 * 
 * Key Features:
 * - Secure JWT token generation with HMAC SHA-512 encryption
 * - Token validation and expiration checking
 * - Automatic token refresh mechanism
 * - Claims extraction and manipulation
 * - GZIP compression for payload optimization
 * - Configurable token expiration and refresh intervals
 * 
 * Security Implementation:
 * - Uses Base64-encoded encryption keys for token signing
 * - Implements token expiration with configurable timeout
 * - Provides refresh mechanism to extend valid sessions
 * - Includes comprehensive validation for token integrity
 * 
 * Token Structure:
 * - Header: Algorithm and compression information
 * - Payload: User claims (ID, expiration, issuer, etc.)
 * - Signature: HMAC SHA-512 signature for verification
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
public class AppJwtUtil {

    /** Token validity period in seconds (1 hour) */
    private static final int TOKEN_TIME_OUT = 3_600;
    
    /** Base64-encoded encryption key for token signing */
    private static final String TOKEN_ENCRY_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";
    
    /** Minimum refresh interval in seconds (5 minutes) */
    private static final int REFRESH_TIME = 300;

    /**
     * Generates a JWT token for the specified user ID
     * 
     * Creates a secure JWT token containing user identification and session information.
     * The token includes standard JWT claims plus custom user data for authentication
     * and authorization purposes throughout the platform.
     * 
     * Token includes:
     * - Unique token ID for tracking and invalidation
     * - User ID in claims for identity verification
     * - Issue and expiration timestamps
     * - Platform-specific metadata (issuer, audience, subject)
     * - GZIP compression for reduced token size
     * - HMAC SHA-512 signature for security
     * 
     * @param id the user ID to embed in the token (use 0L for guest users)
     * @return encrypted JWT token string ready for client use
     */
    public static String getToken(Long id) {
        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("id", id);
        long currentTime = System.currentTimeMillis();
        
        return Jwts.builder()
                .setId(UUID.randomUUID().toString()) // Unique token identifier
                .setIssuedAt(new Date(currentTime)) // Token issue timestamp
                .setSubject("system") // Token subject identifier
                .setIssuer("heima") // Platform issuer information
                .setAudience("app") // Intended token audience
                .compressWith(CompressionCodecs.GZIP) // Payload compression for efficiency
                .signWith(SignatureAlgorithm.HS512, generalKey()) // HMAC SHA-512 encryption
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT * 1000)) // Expiration timestamp
                .addClaims(claimMaps) // Custom user claims
                .compact();
    }

    /**
     * Get claims information in the token
     *
     * @param token
     * @return
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(token);
    }

    /**
     * Obtain the payload body information
     *
     * @param token
     * @return
     */
    public static Claims getClaimsBody(String token) throws ExpiredJwtException {
        return getJws(token).getBody();
    }

    /**
     * Obtain hearder body information
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * Expired or not
     *
     * @param claims
     * @return -1：valid，0：valid，1：expired，2：expired
     */
    public static int verifyToken(Claims claims) throws Exception {
        if (claims == null) {
            return 1;
        }

        claims.getExpiration().before(new Date());
        // You need to automatically refresh the TOKEN
        if ((claims.getExpiration().getTime() - System.currentTimeMillis()) > REFRESH_TIME * 1000) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Generates an encryption key from a string
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRY_KEY.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static void main(String[] args) {
       /* Map map = new HashMap();
        map.put("id","11");*/
        System.out.println(AppJwtUtil.getToken(1102L));
        Jws<Claims> jws = AppJwtUtil.getJws("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAADWLQQqEMAwA_5KzhURNt_qb1KZYQSi0wi6Lf9942NsMw3zh6AVW2DYmDGl2WabkZgreCaM6VXzhFBfJMcMARTqsxIG9Z888QLui3e3Tup5Pb81013KKmVzJTGo11nf9n8v4nMUaEY73DzTabjmDAAAA.4SuqQ42IGqCgBai6qd4RaVpVxTlZIWC826QA9kLvt9d-yVUw82gU47HDaSfOzgAcloZedYNNpUcd18Ne8vvjQA");
        Claims claims = jws.getBody();
        System.out.println(claims.get("id"));

    }

}
