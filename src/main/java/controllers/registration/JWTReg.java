package controllers;

import dto.DTO;
import dto.users.registration.RegRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import services.JwtTokenUtil;

import static configuration.Constants.ROLE_USER;

@RestController
@CrossOrigin
public class JWTReg {

    @Autowired
    private JwtTokenUtil tokenUtil;
    @Autowired
    private UserDetailsManager udm;

    protected final Log logger = LogFactory.getLog(getClass());

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody RegRequest regRequest
    ) throws Exception {
        if(regRequest == null)
            return new ResponseEntity(DTO.mk("empty request"), HttpStatus.UNAUTHORIZED);
        try {

            String
            username = regRequest.getUsername(),
            pass     = regRequest.getPassword(),
            passRep  = regRequest.getPassRepeat();

            if(StringUtils.isEmpty(username)) throw new BadCredentialsException("Username cannot be empty");
            if(StringUtils.isEmpty(pass) || (!pass.equals(passRep))) throw new BadCredentialsException("Pass does not match");

            UserDetails user = User.builder()
                    .username(username)
                    .password(pass)
                    .roles(ROLE_USER)
                    .build();

            udm.createUser(user);

            return ResponseEntity.ok(DTO.mk(String.format("wellcome %s!", username)));

        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.UNAUTHORIZED);
        }
    }
}