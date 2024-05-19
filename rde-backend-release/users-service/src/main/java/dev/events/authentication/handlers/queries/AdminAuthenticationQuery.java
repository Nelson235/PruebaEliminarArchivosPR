// AdminAuthenticationQuery.java
package dev.events.authentication.handlers.queries;

import dev.events.authentication.exceptions.BusinessException;
import dev.events.authentication.models.AuthenticatedUser;
import dev.events.authentication.jpa.entities.AdminEntity;
import dev.events.authentication.jpa.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminAuthenticationQuery {

    @Autowired
    private AdminRepository repository;

    public AuthenticatedUser loadAdminByUsername(String username) {
        Optional<AdminEntity> admin = repository.findByEmail(username);

        if (admin.isPresent()) {
            return new AuthenticatedUser(
                    admin.get().getId(),
                    admin.get().getEmail(),
                    admin.get().getPassword(),
                    "ADMIN"
            );
        } else {
            throw BusinessException.exceptionBuilder().message("Admin not found with email: " + username).build();
        }
    }
}
