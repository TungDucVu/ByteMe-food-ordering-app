package AP_Assignment3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Customer implements Serializable{
	private static final long serialVersionUID = -4117302641878897184L;
	private static final String FILE_NAME_CUSTOMERID = "CustomerID.ser";
	private String name;
	private boolean isVIP;
	private Cart cart;
	private String ID;
	private RandomStringGenerator IDGenerator = RandomStringGenerator.loadFromFile(FILE_NAME_CUSTOMERID);
	
	public Customer(String name, boolean isVIP) {
		this.name = name;
		this.isVIP = isVIP;
		this.cart = new Cart();
		this.ID = IDGenerator.generateRandomString();
	}
	
	public String getName() {
		return name;
	}	
	public boolean isVIP() {
		return isVIP;
	}
	public Cart getCart() {
		return cart;
	}
	public String getID() {
		return ID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setVIP() {
		this.isVIP = true;
	}
	public void unVIP() {
		this.isVIP = false;
	}
	
	public void addToCart(FoodItem item, int quantity) {
		cart.addFoodItem(item, quantity);
	}
	public void showCart() {
		cart.showCartItems();
	}
	public void removeFromCart(CartItem item) {
		cart.removeFoodItem(item.getFoodItem());
		
	}
	public CartItem getCartItem(String itemName) throws FoodItemNotFoundException{
	    return cart.getCartItem(itemName);
	}
	
	public double getTotal() {
		return cart.getTotal();
	}
	public int getQuantity() {
		return cart.getQuantity();
	}
	
	public void saveToFile() {
		IDGenerator.saveToFile(FILE_NAME_CUSTOMERID);
	}

	// Save Course list to a file
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Customer save to file " + filename);
        System.out.println();
    }
    
    // Load Course list from a file
    public static Customer loadFromFile(String filename) {

    	System.out.println("Loading file " + filename + " as Customer");
    	System.out.println();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Customer) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Customer("Dafault name", false); // Return a new instance if file not found or error occurs
        }
    }
}
