package dev.events.authentication.handlers.queries;


import dev.events.authentication.exceptions.BusinessException;
import dev.events.authentication.models.AuthenticatedUser;
import dev.events.authentication.jpa.entities.UserEntity;
import dev.events.authentication.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthenticationQuery {

    @Autowired
    private UserRepository repository;

    public AuthenticatedUser loadUserByUsername(String username) {
        Optional<UserEntity> user = repository.findByEmail(username);

        if (user.isPresent()) {
            return new AuthenticatedUser(
                    user.get().getId(),
                    user.get().getEmail(),
                    user.get().getPassword(),
                    "USER"
            );
        } else {
            throw BusinessException.exceptionBuilder().message("User not found with email: " + username).build();
        }
    }
}

