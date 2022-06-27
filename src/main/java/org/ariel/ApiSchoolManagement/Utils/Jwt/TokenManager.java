package org.ariel.ApiSchoolManagement.Utils.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class TokenManager {
    
    @Autowired
    private Key key;
    
    public static final long TOKEN_VALIDITY = 1800;

    public String generateToken(UserDetails userD){
        Map<String, Object> claims = new HashMap<>();
   
        String token = Jwts.builder()
                        .setClaims(claims)
                        .setSubject(userD.getUsername())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                        .signWith(key)
                        .compact();
        return token;
    }

     public boolean validateToken(UserDetails userDetail, String token){
        String username = getUsernameFromToken(token);
        final Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        boolean isTokenExpired = claims.getExpiration().before(new Date());
        return ( (username.equals(claims.getSubject())) && !isTokenExpired );
     }

    public String getUsernameFromToken(String token){
        final Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
