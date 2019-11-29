package controllers;

import dto.JwtRequest;
import dto.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import services.JwtTokenUtil;

import java.util.*;
import java.util.function.Consumer;

@RestController
@CrossOrigin
public class JWTAuth {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil tokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtRequest authenticationRequest
    ) throws Exception {

        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
            );

            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            Set<String> authoritiesSet = new HashSet<String>();

            authorities.forEach(new Consumer<GrantedAuthority>() {
                @Override
                public void accept(GrantedAuthority grantedAuthority) {
                   authoritiesSet.add(grantedAuthority.getAuthority());
                }
            });

            //todo переделать роли, не безопасно
            Map<String, Object> claims = new HashMap<String, Object>();
            claims.put("roles", authoritiesSet.toArray(new String[authoritiesSet.size()]));

            final String token = tokenUtil.generateToken(claims, auth.getPrincipal().toString());

            return ResponseEntity.ok(new JwtResponse(token));

        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.UNAUTHORIZED);
        }
    }

}