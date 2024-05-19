// AdminAuthenticationHandler.java
package dev.events.authentication.handlers.commands;

import dev.events.authentication.exceptions.BusinessException;
import dev.events.authentication.http.JwtService;
import dev.events.authentication.models.AuthenticatedUser;
import dev.events.authentication.handlers.queries.AdminAuthenticationQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthenticationHandler {

    @Autowired
    private AdminAuthenticationQuery adminAuthenticationQuery;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateWithJwt(String username, String password) {
        AuthenticatedUser authenticated = authenticate(username, password);
        return jwtService.generateToken(authenticated);
    }

    private AuthenticatedUser authenticate(String username, String password) {
        var admin = adminAuthenticationQuery.loadAdminByUsername(username);
        if (admin == null)
            throw new BusinessException("Admin not provided");
        if (admin.password() == null)
            throw new BusinessException("Password not provided");

        if (!passwordEncoder.matches(password, admin.password()))
            throw new BusinessException("Invalid admin");

        return new AuthenticatedUser(admin.id(), admin.username(), admin.password(), "ADMIN");
    }
}
