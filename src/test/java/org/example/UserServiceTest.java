package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService userService;
//    private Map<String, User> userService.getUserDatabase();

    @Before
    public void setUp() {
//        userService.getUserDatabase() = new HashMap<>();
        userService = new UserService();
    }

    @Test
    public void testRegisterUser_Success() {
        // Arrange
        User user = new User("john", "password", "john@example.com");

        // Act
        boolean result = userService.registerUser(user);

        // Assert
        assertTrue(result);
        assertTrue(userService.getUserDatabase().containsKey("john"));
        assertEquals(user, userService.getUserDatabase().get("john"));
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        // Arrange
        User existingUser = new User("john", "password", "john@example.com");
        userService.getUserDatabase().put("john", existingUser);
        User newUser = new User("john", "new_password", "john@example.com");

        // Act
        boolean result = userService.registerUser(newUser);

        // Assert
        assertFalse(result);
        assertTrue(userService.getUserDatabase().containsKey("john"));
        assertEquals(existingUser, userService.getUserDatabase().get("john"));
    }

    @Test
    public void testLoginUser_Success() {
        // Arrange
        User user = new User("john", "password", "john@example.com");
        userService.getUserDatabase().put("john", user);

        // Act
        User result = userService.loginUser("john", "password");

        // Assert
        assertEquals(user, result);
    }

    @Test
    public void testLoginUser_UserNotFound() {
        // Arrange

        // Act
        User result = userService.loginUser("john", "password");

        // Assert
        assertNull(result);
    }

    @Test
    public void testLoginUser_WrongPassword() {
        // Arrange
        User user = new User("john", "password", "john@example.com");
        userService.getUserDatabase().put("john", user);

        // Act
        User result = userService.loginUser("john", "wrong_password");

        // Assert
        assertNull(result);
    }

    // Existing user updates profile with unique username, new password, and new email
    @Test
    public void testUpdateUserProfile_Success() {
        // Arrange
        User existingUser = new User("john", "password", "john@example.com");
        userService.getUserDatabase().put("john", existingUser);
        String newUsername = "john_doe";
        String newPassword = "new_password";
        String newEmail = "john.doe@example.com";

        // Act
        boolean result = userService.updateUserProfile(existingUser, newUsername, newPassword, newEmail);

        // Assert
        assertTrue(result);
        assertEquals(newUsername, existingUser.getUsername());
        assertEquals(newPassword, existingUser.getPassword());
        assertEquals(newEmail, existingUser.getEmail());
        assertTrue(userService.getUserDatabase().containsKey(newUsername));
        assertEquals(existingUser, userService.getUserDatabase().get(newUsername));
        assertFalse(userService.getUserDatabase().containsKey("john")); // Old username should no longer exist in the database
    }



    // Existing user tries to update profile with a username that already exists
    @Test
    public void testUpdateUserProfile_UsernameAlreadyExists() {
        // Arrange
        User existingUser1 = new User("john", "password", "john@example.com");
        userService.getUserDatabase().put("john", existingUser1);
        User existingUser2 = new User("jane", "password", "jane@example.com");
        userService.getUserDatabase().put("jane", existingUser2);
        String newUsername = "jane"; // Attempt to update with an existing username
        String newPassword = "new_password";
        String newEmail = "john.doe@example.com";

        // Act
        boolean result = userService.updateUserProfile(existingUser1, newUsername, newPassword, newEmail);

        // Assert
        assertFalse(result);
        assertEquals("john", existingUser1.getUsername()); // Username should remain unchanged
        assertNotEquals(newPassword, existingUser1.getPassword()); // Password should not be updated
        assertNotEquals(newEmail, existingUser1.getEmail()); // Email should not be updated
        assertTrue(userService.getUserDatabase().containsKey("john")); // User with old username still exists
    }

    // Non-existing user tries to update profile
    @Test
    public void testUpdateUserProfile_UserNotFound() {
        // Arrange
        User nonExistingUser = new User("john", "password", "john@example.com");
        String newUsername = "john_doe";
        String newPassword = "new_password";
        String newEmail = "john.doe@example.com";

        // Act
        boolean result = userService.updateUserProfile(nonExistingUser, newUsername, newPassword, newEmail);

        // Assert
        assertFalse(result);
        assertFalse(userService.getUserDatabase().containsKey(newUsername)); // User should not be added to the database
    }

}