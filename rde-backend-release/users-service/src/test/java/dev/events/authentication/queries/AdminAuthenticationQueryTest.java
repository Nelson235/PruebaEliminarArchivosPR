package dev.events.authentication.queries;

import dev.events.authentication.exceptions.BusinessException;
import dev.events.authentication.handlers.queries.AdminAuthenticationQuery;
import dev.events.authentication.jpa.entities.AdminEntity;
import dev.events.authentication.jpa.repositories.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminAuthenticationQueryTest {

    @Mock
    private AdminRepository repository;

    @InjectMocks
    private AdminAuthenticationQuery query;

    // scenarios
    // 1. get valid username
    // 2. username doesn't exist

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. get valid username
    @Test
    public void testLoadAdminByUsernameDoesNotThrowsBusinessException (){

        // Arrange
        AdminEntity admin= new AdminEntity();
        admin.setId(UUID.randomUUID());
        admin.setEmail("urganizalo.gestion@gmail.com");
        admin.setBirth(new Date(90, 4, 15));
        admin.setName("Juan");
        admin.setPassword("IF-7100-2024");
        admin.setPhone("70389359");

        Mockito.when(repository.findByEmail(admin.getEmail())).thenReturn(Optional.of(admin));
        Optional<AdminEntity> foundAdminOptional = repository.findByEmail(admin.getEmail());

        // Assert
        assertTrue(foundAdminOptional.isPresent());
    }

    // 2. username doesn't exist
    @Test
    public void testLoadAdminByUsernameThrowsBusinessException(){

        // Arrange
        AdminEntity admin= new AdminEntity();
        admin.setId(UUID.randomUUID());
        admin.setEmail("sarachacon@hotmail.com");
        admin.setBirth(new Date(2009, 01, 19));
        admin.setName("Sara");
        admin.setPassword("SaraCh10!");
        admin.setPhone("81447877");

        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // Assert
        Assertions.assertThrows(BusinessException.class, () -> query.loadAdminByUsername(admin.getEmail()));
    }
}

