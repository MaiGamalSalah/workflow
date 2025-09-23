package com.example.Mai.sProject.security.JWT;

import com.example.Mai.sProject.user_config.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JWTUtils {
    //create token (expire date ,( secret key , claim )
    private static final int JWT_EXP = 86400000;//24H
    Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    public String generateToken(UserDetails userDetails) {
        Map<String,Object>claimsObjectMap=new HashMap<>();
        UserInfo userInfo=(UserInfo) userDetails;
        claimsObjectMap.put("role", userInfo.getUser().getRole().name());
        claimsObjectMap.put("userId", userInfo.getUser().getId());
        return createToken(claimsObjectMap, userDetails.getUsername());
    }


    private String createToken(Map<String, Object> claims, String subject) {
        Date currentTime = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + JWT_EXP);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(currentTime)
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }
    //validateToken user and date
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);
    }
    private Claims getALLClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsFunction) {
        Claims claims = getALLClaimsFromToken(token);
        return claimsFunction.apply(claims);
    }
    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        Date exp = getExpirationDateFromToken(token);
        return exp.before(new Date());
    }

}
