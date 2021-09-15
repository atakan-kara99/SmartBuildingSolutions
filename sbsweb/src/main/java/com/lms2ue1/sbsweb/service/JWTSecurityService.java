package com.lms2ue1.sbsweb.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lms2ue1.sbsweb.backend.security.SBSUserDetails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * The heart of the jwt security system. Here everything related to the form of the jwt is declared or specified.
 * @author Luca Anthony Schwarz (sunfl0w)
 */
@Service
public class JWTSecurityService implements Serializable {
    // Validity of 5 minutes
    public final long JWT_TOKEN_VALIDITY = 5 * 60;

    // Secret key set in application properties
    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Gets the username by parsing a jwt
     * @param jwt The jwt to parse
     * @return The username that corresponds to the jwt
     * @throws SignatureException
     */
    public String getUsername(String jwt) throws SignatureException {
        return getAllClaimsFromToken(jwt).getSubject();
    }

    /**
     * Gets the password by parsing a jwt
     * @param jwt The jwt to parse
     * @return The password that corresponds to the jwt
     * @throws SignatureException
     */
    public String getPassword(String jwt) throws SignatureException {
        return (String) getAllClaimsFromToken(jwt).get("password");
    }

    /**
     * Gets the expiration date of a jwt
     * @param jwt The jst to use
     * @return The expiration date of the jwt
     * @throws SignatureException
     */
    public Date getExpirationDate(String jwt) throws SignatureException {
        return getAllClaimsFromToken(jwt).getExpiration();
    }

    /**
     * Gets all the claims of a jwt so the claims can be parsed very easilly
     * @param jwt jwt to het the claims of
     * @return The claims of the provided jwt
     * @throws SignatureException
     */
    private Claims getAllClaimsFromToken(String jwt) throws SignatureException {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
    }

    /**
     * Checks whether the jwt is expired
     * @param jwt jwt to test
     * @return True is the jwt is expired
     */
    private Boolean isTokenExpired(String jwt) {
        Date expirationDate = getExpirationDate(jwt);
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    /**
     * Generates a token using an instance of SBSUserDetails
     * @param userDetails The user details to get a token for
     * @return A valid jwt for the given user details
     */
    public String generateToken(SBSUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).claim("password", userDetails.getPassword())
        .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    /**
     * Plainly checks the validity of a jwt by comparing to the presumably related user details
     * @param jwt jwt as a String
     * @param userDetails User details
     * @return True when the jwt is valid
     */
    public Boolean checkTokenValidity(String jwt, SBSUserDetails userDetails) {
        String username = getUsername(jwt);
        return !isTokenExpired(jwt) && (username.equals(userDetails.getUsername()));
    }
}
