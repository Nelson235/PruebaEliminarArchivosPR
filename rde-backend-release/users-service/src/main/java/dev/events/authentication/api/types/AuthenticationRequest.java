package dev.events.authentication.api.types;


public record AuthenticationRequest(
        String email,
        String password
) {
}