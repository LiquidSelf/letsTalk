package services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_EXPIRATION = 60;
    public static final String AUTH_HEADER = "Authorization";

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return JWT.decode(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return JWT.decode(token).getExpiresAt();
    }

    private Map<String, Claim> getAllClaimsFromToken(String token) {
        return JWT.decode(token).getClaims();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<String, String>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, String> claims, String subject) {

        JWTCreator.Builder builder = JWT.create();

        if (claims != null && claims.size() > 0) {
            claims.forEach(new BiConsumer<String, String>() {
                @Override
                public void accept(String s, String o) {
                    builder.withClaim(s, o);
                }
            });
        }
        return builder.
                withSubject(subject).
                withIssuedAt(new Date(System.currentTimeMillis())).
                withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_EXPIRATION * 1000)).
                sign(Algorithm.HMAC512(secret));
    }

    public void validateToken(String token) throws JWTVerificationException {
        JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
    }

    public boolean isVld(String token){
        try {
            validateToken(token);
            return true;
        }catch (JWTVerificationException ex){
            System.out.println(ex);
            return false;
        }
    }

    public String getTokenFromRequest(HttpServletRequest request){
        try{
            return request.getHeader(AUTH_HEADER).replaceFirst("Bearer ","");
        }catch (Exception ex){
            System.out.println("getTokenFromRequest ex: " + ex.getMessage());
            return null;
        }
    }
}