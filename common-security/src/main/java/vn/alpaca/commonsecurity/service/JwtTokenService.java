package vn.alpaca.commonsecurity.service;

import io.jsonwebtoken.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.alpaca.commonsecurity.object.SecurityUserDetail;

import java.util.Date;

@Service
public class JwtTokenService {

    @Value( "${alpaca.security.jwt.secret:alpaca@@@alpaca@@@alpaca@@@alpaca@@@}" )
    private String jwtSecret;

    @Value( "${alpaca.security.jwt.expiration:84000000}" )
    private Long jwtExpiration;

    public String generateToken(@NonNull SecurityUserDetail user) {

        return Jwts.builder()
                .setId(Integer.toString(user.getId()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public int extractUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Integer.parseInt(claims.getId());
    }

    public boolean validateToken(String authToken) {
        if (!StringUtils.hasText(authToken)) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            return false;
        }
    }
}
