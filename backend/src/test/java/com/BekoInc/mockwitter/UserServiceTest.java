package com.BekoInc.mockwitter;

import com.BekoInc.mockwitter.repository.UserRepository;
import com.BekoInc.mockwitter.serviceImplementation.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("User should be created successfully with its essential fields.")
    public void testCreateUser () {
//        Casual user = new Casual();
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setEmail("john.doe@example.com");
//        user.setUserName("john_doe48");
//        user.setPassword("aaaBBB$25");
//
//        when(userRepository.save(user)).thenReturn(user);
//        User createdUser = userService.createUser(user);
//        assertEquals("John", createdUser.getFirstName());
//        assertEquals("Doe",  createdUser.getLastName());
//        assertEquals("john_doe48",  createdUser.getUserName());
//        assertEquals("aaaBBB$25",  createdUser.getPassword());
//        assertTrue(createdUser.getUserRoles().contains(UserRoleType.CASUAL));

    }

    // Diğer test metodları...
}