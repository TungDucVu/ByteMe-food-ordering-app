package AP_Assignment3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JUnitOutOfStock {
	private Menu menu;
	private Customer customer;
	
	@BeforeEach
    void setUp() {
		menu = Menu.loadFromFile("Menu.ser");
		customer = CustomerHistory.loadFromFile("CustomerHistory.ser").findCustomerByUsername("vip");
        
    }
	// same as in customer screen, but modify to return a string instead of void
	private String addItemToCart(String name, int quantity, Customer customer) {
        try {
            FoodItem item = menu.getFoodItem(name);
            if (!item.isAvailable() ) {
            	return "Not available";
            }
            customer.addToCart(item, quantity);
            return "Item added to customer cart";
        } catch (Exception e) {
            return "Invalid input or Item not found";
        }
    }

	@Test
	void notavailable() {
		String result = addItemToCart("Soft Drink", 3, customer);
		Assertions.assertEquals("Not available", result);
	}
	
	@Test
	void available() {
		String result = addItemToCart("Burger", 3, customer);
		Assertions.assertEquals("Item added to customer cart", result);
	}
	
	@Test
	void notFound() {
		String result = addItemToCart("Pineapple pizza", 3, customer);
		Assertions.assertEquals("Invalid input or Item not found", result);
	}

}
