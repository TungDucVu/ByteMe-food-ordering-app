package AP_Assignment3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JUnitLoginTest {

    private CustomerHistory customerHistory;

    @BeforeEach
    void setUp() {
    	customerHistory = CustomerHistory.loadFromFile("CustomerHistory.ser");
        
    }

    @Test
    void testValidLogin() {
        // Act
        boolean result = customerHistory.validateCustomer("vip", "1");
        
        // Assert
        assertTrue(result, "Valid login should return true");
        
    }

    @Test
    void testInvalidUsername() {
        // Act
        boolean result = customerHistory.validateCustomer("vip ", "password1");
        
        // Assert
        assertFalse(result, "Invalid username should return false");
    }

    @Test
    void testInvalidPassword() {
        // Act
        boolean result = customerHistory.validateCustomer("vip", "0");
        
        // Assert
        assertFalse(result, "Invalid password should return false");
    }

    @Test
    void testNullUsername() {
        // Act
        boolean result = customerHistory.validateCustomer(null, "password1");
        
        // Assert
        assertFalse(result, "Null username should return false");
    }

    @Test
    void testNullPassword() {
        // Act
        boolean result = customerHistory.validateCustomer("vip", null);
        
        // Assert
        assertFalse(result, "Null password should return false");
    }

    @Test
    void testAdminLogin() {
        // Act
        boolean result = customerHistory.validateCustomer("admin", "1");
        
        // Assert
        assertTrue(result, "Admin login with correct credentials should return true");
    }

    @Test
    void testInvalidAdminLogin() {
        // Act
        boolean result = customerHistory.validateCustomer("admin", "wrongPassword");
        
        // Assert
        assertFalse(result, "Invalid admin password should return false");
    }
}