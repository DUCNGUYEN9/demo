package com.ngocduc.projectspringboot.jwt;

import com.ngocduc.projectspringboot.security.CustomUserDetail;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private  String JWT_SECRET;
    @Value(("${jwt.expiration}"))
    private int JWT_EXPIRATION ;
    //Tao jwt tu thong tin cua User
    public String generateToken(CustomUserDetail customUserDetails){
        Date now = new Date();
        Date dateExpired = new Date(now.getTime()+JWT_EXPIRATION);
        //Tao chuoi JWT tu userName
        return Jwts.builder().setSubject(customUserDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(dateExpired)
                .signWith(SignatureAlgorithm.HS512,JWT_SECRET).compact();
    }
//    Lay thong tin user tu jwt
    public String getUserNameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        //tra lai thong tin username
        return claims.getSubject();
    }
    //Validate thong tin cua JWT
    public boolean validateToken(String token) throws UnsupportedJwtException {
        try {
            Jwts
                    .parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    ;
            return true;
        }catch (MalformedJwtException ex){
            log.error("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            log.error("Expired JWT Token");
        } catch (IllegalArgumentException ex){
            log.error("JWT claims String is empty");
        }
        return false;
    }
    public Claims getClaimsFromToken(String token) {
        // Kiểm tra token có bắt đầu bằng tiền tố
        if (token == null) return null;

        try {
            return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }
}
