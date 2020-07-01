package app.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

public class JwtAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

            Object principal = authenticationToken.getPrincipal();
            Object creds     = authenticationToken.getCredentials();

            String login    = principal.toString();
            String password = principal.toString();

            UserDetails authenticated = null;

            authenticated = userDetailsManager.loadUserByUsername(login);

            if(passEncoder.matches(password, authenticated.getPassword()))
                return createSuccessAuthentication(authenticated, authentication, authenticated);
            else
                throw new BadCredentialsException("auth error");
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return super.supports(authentication);
    }
}
