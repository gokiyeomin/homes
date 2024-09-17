package team.gokiyeonmin.imacheater.global.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.gokiyeonmin.imacheater.domain.auth.dto.res.JwtResponse;
import team.gokiyeonmin.imacheater.domain.user.Role;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;
import team.gokiyeonmin.imacheater.global.exception.TokenException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    public static final String USERNAME = "username";
    public static final String ROLES = "roles";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    private final SecretKey key;

    private final Long accessTokenExpirePeriod;

    protected JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expire-period}") Long accessTokenExpirePeriod
    ) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenExpirePeriod = accessTokenExpirePeriod;
    }

    public String extractToken(final String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(JwtUtil.BEARER)) {
            throw new TokenException(ErrorCode.MISSING_TOKEN);
        }

        return authorizationHeader.substring(7);
    }

    public Claims parse(String token) {
        try {
            return Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (IllegalArgumentException e) {
            throw new TokenException(ErrorCode.ILLEGAL_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new TokenException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new TokenException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch (MalformedJwtException e) {
            throw new TokenException(ErrorCode.MALFORMED_TOKEN);
        } catch (JwtException e) {
            throw new TokenException(ErrorCode.UNKNOWN_TOKEN);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public JwtResponse issueToken(String username, List<Role> roles) {
        String accessToken = generateToken(username, roles, accessTokenExpirePeriod);
        return new JwtResponse(accessToken);
    }


    private String generateToken(String username, List<Role> roles, Long expirePeriod) {
        return Jwts.builder()
                .claim(JwtUtil.USERNAME, username)
                .claim(JwtUtil.ROLES, roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirePeriod))
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }
}
