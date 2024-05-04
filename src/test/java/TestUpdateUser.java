import org.danielsa.proiect_ps.utils.DatabaseService;
import org.danielsa.proiect_ps.model.UserModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestUpdateUser {
    DatabaseService databaseService = new DatabaseService();
    @Test
    public void testUpdateUser() {
        double rand = Math.random() * LocalDateTime.now().getSecond();
        String username = "testUser" + rand ;
        String password = "testPassword";
        String userType = "PLAYER";
        databaseService.register(username, password, userType);

        String newUsername = "updatedTestUser" + rand;
        String newPassword = "updatedTestPassword";
        String newUserType = "ADMIN";
        UserModel updatedUser = databaseService.updateUser(username, newUsername, newPassword, newUserType);

        assertNotNull(updatedUser);
        assertEquals(newUsername, updatedUser.getUserName());
        assertEquals(newUserType, updatedUser.getUserType().toString());

        assertTrue(databaseService.deleteUser(newUsername));

        System.out.println("Test : Update User");
    }
}
