package dev.events.authentication.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record AuthenticatedUser(

        UUID id,
        String username,
        String password,

        String role
) {

    public Map<String, Object> toMap() {
        Map<String, Object> user = new HashMap<>();

        user.put("id", id.toString());
        user.put("email", username);
        user.put("role", role);
        CharSequence authorities;
        return user;

    }
}
