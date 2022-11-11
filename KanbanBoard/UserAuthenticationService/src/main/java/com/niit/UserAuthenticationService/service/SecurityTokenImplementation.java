package com.niit.UserAuthenticationService.service;

import com.niit.UserAuthenticationService.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityTokenImplementation implements SecurityTokenGenerator {
    @Override
    public Map<String, String> generateToken(User user) {
        String jwtToken = null;
        jwtToken = Jwts.builder().setSubject(user.getUserEmail()).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"security").compact();

        Map<String,String> map = new HashMap<>();
        map.put("token",jwtToken);
        map.put("message","User Successfully logged in");
        map.put(user.getUserEmail(),"userEmail");
        return map;
    }

}
