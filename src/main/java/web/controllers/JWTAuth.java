package web.controllers;

import app.database.dto.DTO;
import app.database.dto.exception.ExceptionMessage;
import app.database.dto.users.UsersDTO;
import app.database.dto.users.auth.AuthRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import app.services.JwtTokenUtil;

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
    ) throws AuthenticationException {

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            UsersDTO authenticated = (UsersDTO) auth.getPrincipal();

            final String token = tokenUtil.generateToken(authenticated);

            return ResponseEntity.ok(DTO.mk(token));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ExceptionMessage> onException(Exception ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionMessage(ex.getMessage()));
    }

}