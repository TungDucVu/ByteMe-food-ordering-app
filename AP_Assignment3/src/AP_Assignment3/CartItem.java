package AP_Assignment3;

import java.io.Serializable;

public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L; // Unique ID for serialization
    private FoodItem foodItem; // Reference to the FoodItem
    private int quantity; // Quantity of this FoodItem in the cart

    public CartItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    }
    
    @Override
    public String toString() {
    	return foodItem.getName() + " | Price: " + foodItem.getPrice() + " | Category: " + foodItem.getCategory() + " | Quantity: " + quantity;
    }

}

