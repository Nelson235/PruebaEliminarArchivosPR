package dev.events.authentication.api;

import dev.events.authentication.api.types.AuthenticationRequest;
import dev.events.authentication.api.types.AuthenticationResponse;
import dev.events.authentication.handlers.commands.AdminAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/public/auth/admin")
public class AdminAuthenticationController {
    @Autowired
    private AdminAuthenticationHandler handler;

    @PostMapping(value = "/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        String token = handler.authenticateWithJwt(authenticationRequest.email(), authenticationRequest.password());
        return new AuthenticationResponse(token);
    }

}
