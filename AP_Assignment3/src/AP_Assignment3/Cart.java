package AP_Assignment3;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
    private static final long serialVersionUID = 2L; // Unique ID for serialization
    private ArrayList<CartItem> items; // List of items in the cart

    public Cart() {
        this.items = new ArrayList<>();
    }
    
    public ArrayList<CartItem> getItems() {
    	return items;
    }


    public void addFoodItem(FoodItem foodItem, int quantity) {
        for (CartItem cartItem : items) {
            if (cartItem.getFoodItem().getName().equals(foodItem.getName())) {
                cartItem.addQuantity(quantity);
                return;
            }
        }
        items.add(new CartItem(foodItem, quantity));
    }

    // Method to display all items in the cart
    public void showCartItems() {
        for (CartItem cartItem : items) {
            System.out.println(cartItem);
        }
    }
    
    public void removeFoodItem(FoodItem item){
        // Use an iterator to safely remove items while iterating
        items.removeIf(cartItem -> cartItem.getFoodItem().equals(item));
    }
    
    public void clearCart() {
    	items.clear();
    }
    
    public CartItem getCartItem(String itemName){
	    CartItem toGet = null;
	    for (CartItem item : items) {
	    	if (item.getFoodItem().getName().equals(itemName) ) {
	    		toGet = item;
	    		break;
	    	}
	    }
	    return toGet;
	}
    
    public double getTotal() {
    	double sum = 0;
    	for (CartItem item : items) {
    		sum += item.getFoodItem().getPrice() * item.getQuantity();
    	}
    	return sum;
    }
    
    public int getQuantity() {
    	int sum = 0;
    	for (CartItem item : items) {
    		sum += item.getQuantity();
    	}
    	return sum;
    }

    
}

