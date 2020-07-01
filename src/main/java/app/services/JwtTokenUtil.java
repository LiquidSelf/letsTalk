package app.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import app.database.dto.users.UsersDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    //seconds
    public static final long JWT_TOKEN_EXPIRATION = 3000;
    public static final String AUTH_HEADER = "Authorization";

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return JWT.decode(token).getClaim("username").asString();
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

    public String generateToken(@NonNull UsersDTO dto){
        if(dto == null || StringUtils.isEmpty(dto.getUsername()))
        throw new JWTCreationException(String.format("exception on token generation: from null"), null);

        Set<String> authoritiesSet = new HashSet<String>();

        if(dto.getAuthorities() != null)
            dto.getAuthorities().forEach(new Consumer<GrantedAuthority>() {
                @Override
                public void accept(GrantedAuthority grantedAuthority) {
                    authoritiesSet.add(grantedAuthority.getAuthority());
                }
            });

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("username", dto.getUsername());
        claims.put("age", dto.getAge());
        claims.put("authorities", authoritiesSet.toArray(new String[authoritiesSet.size()]));
        claims.put("accountNonExpired", dto.isAccountNonExpired());
        claims.put("accountNonLocked", dto.isAccountNonLocked());
        claims.put("credentialsNonExpired", dto.isCredentialsNonExpired());
        claims.put("enabled", dto.isEnabled());

        final String token = generateToken(claims);

        return token;
    }

    public String generateToken(Map<String, Object> claims) {
        return doGenerateToken(claims);
    }

    private String doGenerateToken(Map<String, Object> claims) {

        JWTCreator.Builder builder = JWT.create();

        if (claims != null && claims.size() > 0) {
            claims.forEach(new BiConsumer<String, Object>() {
                @Override
                public void accept(String name, Object value) {

                    if(value == null) builder.withClaim(name, "null");

                    else if(value instanceof String  ) builder.withClaim(name, (String ) value);
                    else if(value instanceof Long    ) builder.withClaim(name, (Long   ) value);
                    else if(value instanceof Integer ) builder.withClaim(name, (Integer) value);
                    else if(value instanceof Date    ) builder.withClaim(name, (Date   ) value);
                    else if(value instanceof Boolean ) builder.withClaim(name, (Boolean) value);

                    else if(value instanceof Long    []) builder.withArrayClaim(name, (Long   []) value);
                    else if(value instanceof Integer []) builder.withArrayClaim(name, (Integer[]) value);
                    else if(value instanceof String  []) builder.withArrayClaim(name, (String []) value);
                    else {
                        throw new JWTCreationException(
                                String.format("wrong claim object type [%s]", value.getClass()), null);
                    }
                }
            });
        }

        return builder.
                withIssuedAt(new Date(System.currentTimeMillis())).
                withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_EXPIRATION * 1000)).
                sign(Algorithm.HMAC512(secret));
    }

    public void validateToken(String token) throws JWTVerificationException {
        if(StringUtils.isEmpty(token)) throw new JWTVerificationException("token is empty");
        JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
    }

    public boolean isVld(String token){
        if(token == null) return false;
        try {
            validateToken(token);
            return true;
        }catch (JWTVerificationException ex){
            System.out.println(ex);
            return false;
        }
    }

    public String getTokenFromRequest(@NonNull HttpServletRequest request){
        try{
            return request.getHeader(AUTH_HEADER).replaceFirst("Bearer ","");
        }catch (Exception ex){
            System.out.println("getTokenFromRequest ex: " + ex.getMessage());
            return null;
        }
    }

//    /**@see {@link JWTCreator} types*/
//    private void validateClaims(Map<String, Object> claims){
//        if(claims == null) return;
//        if(claims.size() == 0) return;
//
//        Collection value = claims.values();
//
//        value.forEach(new Consumer() {
//            @Override
//            public void accept(Object o) {
//                boolean ok = false;
//                if(o instanceof String) ok = true;
//                if(o instanceof String) ok = true;
//                if(o instanceof String) ok = true;
//                if(o instanceof String[]) ok = true;
//                if(o instanceof Long[]) ok = true;
//                if(o instanceof Integer[]) ok = true;
//            }
//        });
//    }
}