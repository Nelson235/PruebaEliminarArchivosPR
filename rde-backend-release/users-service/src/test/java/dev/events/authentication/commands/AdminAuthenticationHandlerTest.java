package dev.events.authentication.commands;

import dev.events.authentication.exceptions.BusinessException;
import dev.events.authentication.handlers.commands.AdminAuthenticationHandler;
import dev.events.authentication.http.JwtService;
import dev.events.authentication.handlers.queries.AdminAuthenticationQuery;
import dev.events.authentication.models.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AdminAuthenticationHandlerTest {

    @Mock
    private AdminAuthenticationQuery adminAuthenticationQuery;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminAuthenticationHandler adminAuthenticationHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateWithJwtSuccessfully() {

        String username = "valid@ucr.ac.cr";
        String password = "Password1@";
        String encodedPassword = "encodedPassword";
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(UUID.randomUUID(), username, encodedPassword, "ADMIN");

        when(adminAuthenticationQuery.loadAdminByUsername(username)).thenReturn(authenticatedUser);
        when(passwordEncoder.matches(password, authenticatedUser.password())).thenReturn(true);
        when(jwtService.generateToken(authenticatedUser)).thenReturn("generatedToken");

        String token = adminAuthenticationHandler.authenticateWithJwt(username, password);

        assertEquals("generatedToken", token);
    }

    @Test
    public void testAuthenticateWithInvalidAdminThrowsBusinessException() {

        String username = "invalid@ucr.ac.cr";
        String password = "Password1@";

        when(adminAuthenticationQuery.loadAdminByUsername(username)).thenThrow(new BusinessException("Invalid admin"));

        assertThrows(BusinessException.class, () -> adminAuthenticationHandler.authenticateWithJwt(username, password));
    }

    @Test
    public void testAuthenticateWithInvalidPasswordThrowsBusinessException() {

        String username = "valid@ucr.ac.cr";
        String password = "invalidPassword";
        String encodedPassword = "encodedPassword";
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(UUID.randomUUID(), username, encodedPassword, "ADMIN");

        when(adminAuthenticationQuery.loadAdminByUsername(username)).thenReturn(authenticatedUser);
        when(passwordEncoder.matches(password, authenticatedUser.password())).thenReturn(false);

        assertThrows(BusinessException.class, () -> adminAuthenticationHandler.authenticateWithJwt(username, password));
    }

    @Test
    public void testAuthenticateWithNonExistentAdminThrowsBusinessException() {

        String nonExistentAdmin = "invalid@ucr.ac.cr";
        String validPassword = "Password1@";

        when(adminAuthenticationQuery.loadAdminByUsername(nonExistentAdmin)).thenThrow(new BusinessException("Non-existent admin"));

        assertThrows(BusinessException.class, () -> adminAuthenticationHandler.authenticateWithJwt(nonExistentAdmin, validPassword));
    }

    @Test
    public void testAuthenticateWithNullAdminThrowsBusinessException() {

        String validPassword = "Password1@";

        assertThrows(BusinessException.class, () -> adminAuthenticationHandler.authenticateWithJwt(null, validPassword));
    }

    @Test
    public void testAuthenticateWithNullPasswordThrowsBusinessException() {

        String validAdmin = "valid@ucr.ac.cr";

        assertThrows(BusinessException.class, () -> adminAuthenticationHandler.authenticateWithJwt(validAdmin, null));
    }

}