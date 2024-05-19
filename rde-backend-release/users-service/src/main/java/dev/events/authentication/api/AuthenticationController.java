package dev.events.authentication.api;


import dev.events.authentication.api.types.AuthenticationRequest;
import dev.events.authentication.api.types.AuthenticationResponse;
import dev.events.authentication.handlers.commands.AuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private AuthenticationHandler handler;
    @PostMapping(value = "/api/public/auth/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        String token = handler.authenticateWithJwt(authenticationRequest.email(), authenticationRequest.password());
        return new AuthenticationResponse(token);
    }

}
