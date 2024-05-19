package dev.events.authentication.handlers.commands;

import dev.events.authentication.exceptions.BusinessException;
import dev.events.authentication.exceptions.InvalidInputException;
import dev.events.authentication.jpa.entities.UserEntity;
import dev.events.authentication.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RegisterUserHandler {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    public record Command(String email, String name, String password,Date birth, /*String campusId,*/ String studentId, String phone, String address, int scholarship) {
    }

    public void register(Command command) {
        validateRequiredFields(command);
        validateExistingUser(command.email());
        UserEntity user = new UserEntity();
        user.setEmail(command.email());
        user.setName(command.name());
        user.setPassword(encoder.encode(command.password()));
        user.setBirth(command.birth());
        //user.setCampus_id(command.campusId());
        user.setStudent_id(command.studentId());
        user.setPhone(command.phone());
        user.setAddress(command.address());
        user.setScholarship(command.scholarship());
        repository.save(user);
    }

    private void validateExistingUser(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new BusinessException("User already exists");
        }
    }

    private void validateRequiredFields(Command command) {
        if (command.email() == null) {
            throw new InvalidInputException("email");
        }
        if (!command.email.endsWith("@ucr.ac.cr")) {
            throw new BusinessException("El correo debe ser dominio '@ucr.ac.cr'");
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
        /*if (command.campusId() == null) {
            throw new InvalidInputException("campusId");
        }*/
        if (command.studentId() == null) {
            throw new InvalidInputException("studentId");
        }
        if (command.phone() == null) {
            throw new InvalidInputException("phone");
        }
        if (command.address() == null) {
            throw new InvalidInputException("address");
        }
        if (!(command.scholarship() >= 0 && command.scholarship() <= 5)) {
            throw new BusinessException("La beca ingresada no es correcta");
        }
    }
}
