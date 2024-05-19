package dev.events.authentication.queries;

import dev.events.authentication.exceptions.BusinessException;
import dev.events.authentication.jpa.entities.UserEntity;
import dev.events.authentication.jpa.repositories.UserRepository;
import dev.events.authentication.handlers.queries.UserAuthenticationQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserAuthenticationQueryTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserAuthenticationQuery query;

    // scenarios
    // 1. get valid username
    // 2. username doesn't exist

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsernameDoesNotThrowsBusinessException(){

        // Arrange
        UserEntity user= new UserEntity();
        user.setId(UUID.randomUUID());
        user.setEmail("juan.perezduran@ucr.ac.cr");
        user.setBirth(new Date(90, 4, 15));
        user.setStudent_id("C15233");
        user.setName("Juan");
        user.setPassword("IF-7100-2024");
        user.setAddress("Taras, Cartago");
        user.setScholarship(1);
        user.setPhone("70389359");

        Mockito.when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Optional<UserEntity> foundAdminOptional = repository.findByEmail(user.getEmail());

        // Assert
        assertTrue(foundAdminOptional.isPresent());
    }

    @Test
    public void testLoadUserByUsernameThrowsBusinessException(){

        // Arrange
        UserEntity user= new UserEntity();
        user.setId(UUID.randomUUID());
        user.setEmail("maria.torresguillen@gmail.com");
        user.setBirth(new Date(2009, 4, 15));
        user.setStudent_id("B96611");
        user.setName("Maria");
        user.setPassword("Mar1a123!!");
        user.setAddress("San Rafael, Cartago");
        user.setScholarship(0);
        user.setPhone("52201477");

        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // Assert
        Assertions.assertThrows(BusinessException.class, () -> query.loadUserByUsername(user.getEmail()));
    }
}
