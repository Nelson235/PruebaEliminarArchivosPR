package dev.events.authentication.commands;

import dev.events.authentication.exceptions.BusinessException;
import dev.events.authentication.handlers.commands.RegisterUserHandler;
import dev.events.authentication.jpa.entities.UserEntity;
import dev.events.authentication.jpa.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

public class RegisterUserHandlerTest {
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private RegisterUserHandler handler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUserSuccessfully() {

        // Arrange
        var command = new RegisterUserHandler.Command(
                "valid@ucr.ac.cr",
                "name",
                "Password1@",
                new Date(),
                "studentId",
                "12345678",
                "address",
                1
        );

        var userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        Mockito.when(repository.save(userCaptor.capture()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        handler.register(command);

        UserEntity capturedUser = userCaptor.getValue();
        Assertions.assertEquals(capturedUser.getEmail(), "valid@ucr.ac.cr");
        Assertions.assertEquals(capturedUser.getName(), "name");
        Assertions.assertEquals(capturedUser.getStudent_id(), "studentId");
        Assertions.assertEquals(capturedUser.getPhone(), "12345678");
        Assertions.assertEquals(capturedUser.getAddress(), "address");
        Assertions.assertEquals(capturedUser.getScholarship(), 1);
        Mockito.verify(repository, Mockito.times(1)).save(any());
    }

    @Test
    public void testRegisterInvalidEmailDomainThrowsBusinessException() {

        RegisterUserHandler.Command command = new RegisterUserHandler.Command("invalid@domain.com", "name", "Password1@", new Date(), "studentId", "12345678", "address", 1);

        assertThrows(BusinessException.class, () -> handler.register(command));

        Mockito.verify(repository, Mockito.times(0)).save(any());
    }

    @Test
    public void testRegisterWithPasswordWithoutNumberThrowsBusinessException() {

        RegisterUserHandler.Command command = new RegisterUserHandler.Command("valid@ucr.ac.cr", "name", "Password@", new Date(), "studentId", "12345678", "address", 1);

        assertThrows(BusinessException.class, () -> handler.register(command));

        Mockito.verify(repository, Mockito.times(0)).save(any());

    }

    @Test
    public void testRegisterWithPasswordWithoutSpecialCharacterThrowsBusinessException() {

        RegisterUserHandler.Command command = new RegisterUserHandler.Command("valid@ucr.ac.cr", "name", "Password1", new Date(), "studentId", "12345678", "address", 1);

        assertThrows(BusinessException.class, () -> handler.register(command));

        Mockito.verify(repository, Mockito.times(0)).save(any());

    }

    @Test
    public void testRegisterWithPasswordWithoutUppercaseThrowsBusinessException() {

        RegisterUserHandler.Command command = new RegisterUserHandler.Command("valid@ucr.ac.cr", "name", "password1@", new Date(), "studentId", "12345678", "address", 1);

        assertThrows(BusinessException.class, () -> handler.register(command));

        Mockito.verify(repository, Mockito.times(0)).save(any());

    }

    @Test
    public void testRegisterWithPasswordWithoutLowercaseThrowsBusinessException() {

        RegisterUserHandler.Command command = new RegisterUserHandler.Command("valid@ucr.ac.cr", "name", "PASSWORD1@", new Date(), "studentId", "12345678", "address", 1);

        assertThrows(BusinessException.class, () -> handler.register(command));

        Mockito.verify(repository, Mockito.times(0)).save(any());

    }

    @Test
    public void testRegisterWithPasswordLessThan8CharactersThrowsBusinessException() {

        RegisterUserHandler.Command command = new RegisterUserHandler.Command("valid@ucr.ac.cr", "name", "Pass1@", new Date(), "studentId", "12345678", "address", 1);

        assertThrows(BusinessException.class, () -> handler.register(command));

        Mockito.verify(repository, Mockito.times(0)).save(any());

    }


    @Test
    public void testRegisterWithScholarshipOutOfRangeThrowsBusinessException() {
        RegisterUserHandler.Command command = new RegisterUserHandler.Command("valid@ucr.ac.cr", "name", "Password1@", new Date(), "studentId", "12345678", "address", 6);

        assertThrows(BusinessException.class, () -> handler.register(command));

        Mockito.verify(repository, Mockito.times(0)).save(any());
    }

    @Test
    public void testRegisterWithExistingEmailThrowsBusinessException() {
        String existingEmail = "existing@ucr.ac.cr";
        RegisterUserHandler.Command command = new RegisterUserHandler.Command(existingEmail, "name", "Password1@", new Date(), "studentId", "12345678", "address", 1);
        UserEntity existingUser = new UserEntity();
        existingUser.setEmail(existingEmail);
        when(repository.findByEmail(existingEmail)).thenReturn(Optional.of(existingUser));

        assertThrows(BusinessException.class, () -> handler.register(command));
        Mockito.verify(repository, Mockito.times(0)).save(any());
    }

}