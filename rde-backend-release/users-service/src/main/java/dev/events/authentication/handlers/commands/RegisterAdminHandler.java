// RegisterAdminHandler.java
package dev.events.authentication.handlers.commands;

import dev.events.authentication.exceptions.BusinessException;
import dev.events.authentication.exceptions.InvalidInputException;
import dev.events.authentication.jpa.entities.AdminEntity;
import dev.events.authentication.jpa.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RegisterAdminHandler {
    @Autowired
    private AdminRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    public record Command(String email, String name, String password, Date birth, String phone) {
    }

    public void register(Command command) {
        validateRequiredFields(command);
        validateExistingAdmin(command.email());
        AdminEntity admin = new AdminEntity();
        admin.setEmail(command.email());
        admin.setName(command.name());
        admin.setPassword(encoder.encode(command.password()));
        admin.setBirth(command.birth());
        admin.setPhone(command.phone());
        repository.save(admin);
    }

    private void validateExistingAdmin(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new BusinessException("Admin already exists");
        }
    }

    private void validateRequiredFields(Command command) {
        if (command.email() == null) {
            throw new InvalidInputException("email");
        }
        if (command.name() == null) {
            throw new InvalidInputException("name");
        }
        if (command.password() == null) {
            throw new InvalidInputException("password");
        }
        if (!command.password.matches("^(?=.*[0-9]).*$")) {
            throw new BusinessException("La contraseña debe contener al menos un número");
        }
        if (!command.password.matches("^(?=.*[a-z]).*$")) {
            throw new BusinessException("La contraseña debe contener al menos una letra minúscula");
        }
        if (!command.password.matches("^(?=.*[A-Z]).*$")) {
            throw new BusinessException("La contraseña debe contener al menos una letra mayúscula");
        }
        if (!command.password.matches("^(?=.*[@#$%^&+=.;:]).*$")) {
            throw new BusinessException("La contraseña debe contener al menos un caractér especial");
        }
        if (command.password.length() < 8) {
            throw new BusinessException("La contraseña debe tener al menos 8 caracteres");
        }
        if (command.birth() == null) {
            throw new InvalidInputException("birth");
        }
        if (command.phone() == null) {
            throw new InvalidInputException("phone");
        }
    }
}
