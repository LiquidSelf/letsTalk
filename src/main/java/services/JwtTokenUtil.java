package services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    //seconds
    public static final long JWT_TOKEN_EXPIRATION = 60 * 30;
    public static final String AUTH_HEADER = "Authorization";

    @Autowired
    private UsersValidation_Service validate;

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

    public String generateToken(Map<String, Object> claims, String userName) {
        Assert.notNull(userName, "cant generate token for null username");
        return doGenerateToken(null, userName);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        validate.assertUsersBase(userDetails);
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

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