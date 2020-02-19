package controllers;

import dto.DTO;
import dto.users.UsersDTO;
import dto.users.auth.AuthRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import services.JwtTokenUtil;

import java.util.*;
import java.util.function.Consumer;

@RestController
@CrossOrigin
public class JWTAuth {
    private static final Log logger = LogFactory.getLog(JWTAuth.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil tokenUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthRequest authenticationRequest
    ) throws Exception {

        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
            );

            UsersDTO authenticated = (UsersDTO) auth.getPrincipal();

            final String token = tokenUtil.generateToken(authenticated);

            return ResponseEntity.ok(DTO.mk(token));

        } catch (Exception e) {
            logger.info(e);
            return new ResponseEntity(e, HttpStatus.UNAUTHORIZED);
        }
    }
}