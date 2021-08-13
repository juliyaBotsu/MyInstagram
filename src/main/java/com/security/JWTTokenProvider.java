package com.security;

import com.entity.UserApp;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {

    public static final Logger LOG = LoggerFactory.getLogger(JWTTokenProvider.class);

    public String generateToken(Authentication authentication){
        UserApp userApp = (UserApp) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expireDate = new Date(now.getTime() * SecurityConstants.EXPIRATION_TIME);

        String userId = Long.toString(userApp.getId());

        Map<String,Object> claimsMap = new HashMap<>();
        claimsMap.put("id",userId);
        claimsMap.put("username",userApp.getUsername());
        claimsMap.put("firstName",userApp.getName());
        claimsMap.put("lastName",userApp.getLastname());
        claimsMap.put("password",userApp.getPassword());
        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.SECRET)
                .compact();
    }


    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException| UnsupportedJwtException| ExpiredJwtException|MalformedJwtException|IllegalArgumentException ex){
            LOG.error(ex.getMessage());
            return false;
        }
    }

    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
        String id = (String)claims.get("id");
        return Long.parseLong(id);
    }
}
