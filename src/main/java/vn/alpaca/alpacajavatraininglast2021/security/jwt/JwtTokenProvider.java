package vn.alpaca.alpacajavatraininglast2021.security.jwt;

import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value( "${alpaca.security.jwt.secret:alpaca@@@alpaca@@@alpaca@@@alpaca@@@}" )
    private String jwtSecret;

    @Value( "${alpaca.security.jwt.expiration:84000000L}" )
    private Long jwtExpiration;

    public String generateToken(@NonNull User user) {

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
            ex.printStackTrace();
        }
        return false;
    }
}
