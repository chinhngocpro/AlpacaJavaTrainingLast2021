package vn.alpaca.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.Payload;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import vn.alpaca.common.dto.response.AuthenticationInfo;
import vn.alpaca.security.model.AuthPermission;
import vn.alpaca.security.model.AuthUser;
import vn.alpaca.security.model.RefreshToken;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Configuration
@Log4j2
@RequiredArgsConstructor
public class JwtTokenService {

  @Value("${alpaca.security.jwt.secret:alpaca@@@alpaca@@@alpaca@@@alpaca@@@}")
  private String jwtSecret;

  @Value("${alpaca.security.jwt.expiration:300000}")
  private long jwtExpiration; // 300000ms = 5 minutes as default

  private final RedissonClient redissonClient;

  public final String CACHE_NAME = "user_refresh_token";

  @Value("${alpaca.security.jwt.refresh-token.ttl:7}")
  private long timeToLive;

  private final TimeUnit ttlUnit = TimeUnit.DAYS;

  @Value("${alpaca.security.jwt.refresh-token.max-idle-time:6}")
  private long maxIdleTime;

  private final TimeUnit idleTimeUnit = TimeUnit.HOURS;

  private RMapCache<String, AuthUser> tokenMap;

  @PostConstruct
  public void init() {
    tokenMap = redissonClient.getMapCache(CACHE_NAME);
  }

  public String generateToken(@NonNull AuthUser user) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("id", user.getId());
    payload.put("username", user.getUsername());
    payload.put("role", user.getRole().getName());
    payload.put(
        "authorities",
        user.getRole().getAuthorities().stream()
            .map(AuthPermission::getPermissionName)
            .collect(Collectors.toList()));

    JWTCreator.Builder builder = JWT.create();
    builder.withKeyId(UUID.randomUUID().toString());
    builder.withPayload(payload);
    builder.withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration));

    return builder.sign(Algorithm.HMAC512(jwtSecret));
  }

  public AuthenticationInfo getAuthInfoFromToken(String token) {

    String json = new String(Base64.getDecoder().decode(JWT.decode(token).getPayload()));
    Payload payload = new JWTParser().parsePayload(json);
    Map<String, Claim> claims = payload.getClaims();

    AuthenticationInfo authInfo = new AuthenticationInfo();
    authInfo.setId(claims.get("id").asInt());
    authInfo.setUsername(claims.get("username").asString());
    authInfo.setRoleName(claims.get("role").asString());
    authInfo.setAuthorities(new HashSet<>(claims.get("authorities").asList(String.class)));

    return authInfo;
  }

  public boolean validateToken(String authToken) {
    try {
      JWT.require(Algorithm.HMAC512(jwtSecret)).build().verify(authToken);
      return true;
    } catch (JWTVerificationException | NullPointerException | IllegalArgumentException ex) {
      log.error("VALIDATE TOKEN FAILED: " + ex.getMessage());
      return false;
    }
  }

  public RefreshToken createRefreshToken(AuthUser user) {
    RefreshToken token = new RefreshToken();
    token.setToken(UUID.randomUUID().toString());
    token.setUser(user);

    tokenMap.fastPut(token.getToken(), user, timeToLive, ttlUnit, maxIdleTime, idleTimeUnit);
    log.info("SAVED TOKEN " + token.getToken() + ": " + tokenMap.get(token.getToken()));

    return token;
  }

  public RefreshToken verifyExpiration(String token) {
    if (tokenMap.remainTimeToLive(token) > 0) {
      AuthUser user = tokenMap.get(token);

      return createRefreshToken(user);
    } else {
      throw new TokenExpiredException("Refresh token has been expired");
    }
  }

  public void updateTokenValue(AuthUser user) {
    String token =
        tokenMap.entrySet().stream()
            .distinct()
            .filter(entry -> entry.getValue().getId() == user.getId())
            .findFirst()
            .map(Map.Entry::getKey)
            .toString();
    tokenMap.replace(token, user);
  }

  public void removeRefreshToken(String token) {
    tokenMap.remove(token);
  }

  public void removeRefreshToken(AuthUser user) {
    String token =
        tokenMap.entrySet().stream()
            .distinct()
            .filter(entry -> entry.getValue().getId() == user.getId())
            .findFirst()
            .map(Map.Entry::getKey)
            .toString();
    tokenMap.remove(token);
  }
}
