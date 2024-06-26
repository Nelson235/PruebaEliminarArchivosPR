package dev.events.authentication.handlers.commands;

import dev.events.authentication.exceptions.BusinessException;
import dev.events.authentication.handlers.queries.UserAuthenticationQuery;
import dev.events.authentication.http.JwtService;
import dev.events.authentication.models.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationHandler {

    @Autowired
    private UserAuthenticationQuery userAuthenticationQuery;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateWithJwt(String username, String password) {
        AuthenticatedUser authenticated = authenticate(username, password);
        return jwtService.generateToken(authenticated);
    }

    private AuthenticatedUser authenticate(String username, String password) {
        var user = userAuthenticationQuery.loadUserByUsername(username);
        if (user == null)
            throw new BusinessException("User not provided");
        if (user.password() == null)
            throw new BusinessException("Password not provided");

        if (!passwordEncoder.matches(password, user.password()))
            throw new BusinessException("Invalid user");

        return user;
    }
}
