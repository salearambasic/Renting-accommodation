package com.project.Rentingaccommodation.security;


import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtValidator {

    private String secret = "secretKey";

    public JwtUser validate(String token) {
    	JwtUser jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            jwtUser = new JwtUser((String) body.get("email"));

        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return jwtUser;
    }
}