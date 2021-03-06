package com.project.Rentingaccommodation.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserPrivileges;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {
	
	@Autowired
	private UserService userService;

    public String generateUser(JwtUser jwtUser) {
    	Claims claims = Jwts.claims();
        User user = userService.findOne(jwtUser.getId());
        if(user != null) {
        	claims.put("id", jwtUser.getId());
        	claims.put("email", jwtUser.getEmail());
    		claims.put("role", UserRoles.USER);
    		claims.put("status", user.getStatus());
    		claims.put("privilege", UserPrivileges.READ_WRITE_PRIVILEGE);
    		return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
                    .signWith(SignatureAlgorithm.HS512, "SECRETKEY")
                    .compact();
    		
        }
        return "User with email " + jwtUser.getEmail() + " not found.";
    }

	public String generateAdmin(JwtUser jwtUser) {
    	Claims claims = Jwts.claims();
    	claims.put("id", jwtUser.getId());
    	claims.put("email", jwtUser.getEmail());
		claims.put("role", UserRoles.ADMIN);
		claims.put("privilege", UserPrivileges.READ_WRITE_PRIVILEGE);
		return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
                .signWith(SignatureAlgorithm.HS512, "SECRETKEY")
                .compact();
	}
	
	public String generateAgent(JwtAgent jwtAgent) {
    	Claims claims = Jwts.claims();
    	claims.put("id", jwtAgent.getId());
    	claims.put("email", jwtAgent.getEmail());
		claims.put("role", UserRoles.AGENT);
		claims.put("privilege", UserPrivileges.READ_WRITE_PRIVILEGE);
		return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "SECRETKEY")
                .compact();
	}
}
