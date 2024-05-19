package dev.events.authentication.commands;

import dev.events.authentication.exceptions.BusinessException;
import dev.events.authentication.handlers.commands.AuthenticationHandler;

import dev.events.authentication.http.JwtService;
import dev.events.authentication.jpa.repositories.UserRepository;
import dev.events.authentication.handlers.queries.UserAuthenticationQuery;
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

public class AuthenticationHandlerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserAuthenticationQuery userAuthenticationQuery;

    @InjectMocks
    private AuthenticationHandler authenticationHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateWithJwtSuccessfully() {

        String username = "valid@ucr.ac.cr";
        String password = "Password1@";
        String encodedPassword = "encodedPassword";
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(UUID.randomUUID(), username, encodedPassword, "USER");
        when(userAuthenticationQuery.loadUserByUsername(username)).thenReturn(authenticatedUser);
        when(passwordEncoder.matches(password, authenticatedUser.password())).thenReturn(true);
        when(jwtService.generateToken(authenticatedUser)).thenReturn("generatedToken");

        when(userAuthenticationQuery.loadUserByUsername(username)).thenReturn(authenticatedUser);
        when(passwordEncoder.matches(password, authenticatedUser.password())).thenReturn(true);
        when(jwtService.generateToken(authenticatedUser)).thenReturn("generatedToken");

        String token = authenticationHandler.authenticateWithJwt(username, password);

        assertEquals("generatedToken", token);
    }

    @Test
    public void testAuthenticateWithInvalidPasswordThrowsBusinessException() {

        String username = "valid@ucr.ac.cr";
        String password = "invalidPassword";
        String encodedPassword = "encodedPassword";
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(UUID.randomUUID(), username, encodedPassword, "USER");

        when(userAuthenticationQuery.loadUserByUsername(username)).thenReturn(authenticatedUser);
        when(passwordEncoder.matches(password, authenticatedUser.password())).thenReturn(false);

        assertThrows(BusinessException.class, () -> authenticationHandler.authenticateWithJwt(username, password));

    }

    @Test
    public void testAuthenticateWithNonExistentUserThrowsBusinessException() {
        String nonExistentUser = "nonexistentUser@ucr.ac.cr";
        String validPassword = "Password1@";
        when(userRepository.findByEmail(nonExistentUser)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> authenticationHandler.authenticateWithJwt(nonExistentUser, validPassword));
    }

    @Test
    public void testAuthenticateWithNullUserThrowsBusinessException() {
        String validPassword = "Password1@";

        assertThrows(BusinessException.class, () -> authenticationHandler.authenticateWithJwt(null, validPassword));
    }

    @Test
    public void testAuthenticateWithNullPasswordThrowsBusinessException() {
        String validUser = "valid@ucr.ac.cr";

        assertThrows(BusinessException.class, () -> authenticationHandler.authenticateWithJwt(validUser, null));
    }
}