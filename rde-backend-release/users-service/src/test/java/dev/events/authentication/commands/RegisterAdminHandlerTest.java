package dev.events.authentication.commands;

import dev.events.authentication.exceptions.BusinessException;
import dev.events.authentication.exceptions.InvalidInputException;
import dev.events.authentication.handlers.commands.RegisterAdminHandler;
import dev.events.authentication.jpa.entities.AdminEntity;
import dev.events.authentication.jpa.repositories.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RegisterAdminHandlerTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterAdminHandler registerAdminHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterAdminSuccessfully() {

        var command = new RegisterAdminHandler.Command(
                "valid@ucr.ac.cr",
                "Admin Name",
                "Password1@",
                new Date(),
                "12345678"
        );

        var adminCaptor = ArgumentCaptor.forClass(AdminEntity.class);
        when(adminRepository.save(adminCaptor.capture()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        registerAdminHandler.register(command);

        AdminEntity capturedAdmin = adminCaptor.getValue();
        Assertions.assertEquals(capturedAdmin.getEmail(), "valid@ucr.ac.cr");
        Assertions.assertEquals(capturedAdmin.getName(), "Admin Name");
        Assertions.assertEquals(capturedAdmin.getPhone(), "12345678");

        Mockito.verify(adminRepository, Mockito.times(1)).save(any());
    }


    @Test
    public void testRegisterAdminWithExistingEmailThrowsBusinessException() {
        String existingEmail = "valid@ucr.ac.cr";
        RegisterAdminHandler.Command command = new RegisterAdminHandler.Command(existingEmail, "Existing Admin", "Password1@", new Date(), "12345678");
        AdminEntity existingAdmin = new AdminEntity();
        existingAdmin.setEmail(existingEmail);
        when(adminRepository.findByEmail(existingEmail)).thenReturn(Optional.of(existingAdmin));

        assertThrows(BusinessException.class, () -> registerAdminHandler.register(command));
        Mockito.verify(adminRepository, Mockito.times(0)).save(any());
    }


    @Test
    public void testRegisterAdminWithInvalidPasswordThrowsBusinessException() {
        String invalidPassword = "invalidpassword";
        RegisterAdminHandler.Command command = new RegisterAdminHandler.Command("valid@ucr.ac.cr", "Admin Name", invalidPassword, new Date(), "12345678");

        assertThrows(BusinessException.class, () -> registerAdminHandler.register(command));
        Mockito.verify(adminRepository, Mockito.times(0)).save(any());
    }


    @Test
    public void testRegisterAdminWithNullEmailThrowsInvalidInputException() {
        RegisterAdminHandler.Command command = new RegisterAdminHandler.Command(null, "Admin Name", "Password1@", new Date(), "12345678");

        assertThrows(InvalidInputException.class, () -> registerAdminHandler.register(command));
        Mockito.verify(adminRepository, Mockito.times(0)).save(any());
    }

    @Test
    public void testRegisterAdminWithNullNameThrowsInvalidInputException() {
        RegisterAdminHandler.Command command = new RegisterAdminHandler.Command("valid@ucr.ac.cr", null, "Password1@", new Date(), "12345678");

        assertThrows(InvalidInputException.class, () -> registerAdminHandler.register(command));
        Mockito.verify(adminRepository, Mockito.times(0)).save(any());
    }

    @Test
    public void testRegisterAdminWithNullPasswordThrowsInvalidInputException() {
        RegisterAdminHandler.Command command = new RegisterAdminHandler.Command("valid@ucr.ac.cr", "Admin Name", null, new Date(), "12345678");

        assertThrows(InvalidInputException.class, () -> registerAdminHandler.register(command));
        Mockito.verify(adminRepository, Mockito.times(0)).save(any());
    }

    @Test
    public void testRegisterAdminWithNullBirthThrowsInvalidInputException() {
        RegisterAdminHandler.Command command = new RegisterAdminHandler.Command("valid@ucr.ac.cr", "Admin Name", "Password1@", null, "12345678");

        assertThrows(InvalidInputException.class, () -> registerAdminHandler.register(command));
        Mockito.verify(adminRepository, Mockito.times(0)).save(any());
    }

    @Test
    public void testRegisterAdminWithNullPhoneThrowsInvalidInputException() {
        RegisterAdminHandler.Command command = new RegisterAdminHandler.Command("valid@ucr.ac.cr", "Admin Name", "Password1@", new Date(), null);

        assertThrows(InvalidInputException.class, () -> registerAdminHandler.register(command));
        Mockito.verify(adminRepository, Mockito.times(0)).save(any());
    }

}