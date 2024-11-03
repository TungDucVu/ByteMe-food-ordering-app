package AP_Assignment3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class ItemSales implements Serializable{
	private static final long serialVersionUID = -8195680529039757495L;
	private TreeMap<FoodItem, Integer> itemSales;
	
	public ItemSales() {
		itemSales = new TreeMap<>();
	}
	
	public void generateItemSalesReport(OrderHistory orderHistory) {

	    for (Order order : orderHistory.getOrderQueue()) {
	        for (CartItem item : order.getCart().getItems()) {
	        	itemSales.put(item.getFoodItem(), itemSales.getOrDefault(item.getFoodItem(), 0) + 1);
	        }
	    }

	    System.out.println("Item Sales Report:");
	    for (Map.Entry<FoodItem, Integer> entry : itemSales.entrySet()) {
	        System.out.println(entry.getKey() + ": " + entry.getValue() + " sold");
	    }
	}
	
	public void show() {
		for (Map.Entry<FoodItem, Integer> entry : itemSales.entrySet()) {
	        System.out.println(entry.getKey() + ": " + entry.getValue() + " sold");
	    }
	}
	
	// Save Course list to a file
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Item Sales save to file " + filename);
        System.out.println();
    }
    
    // Load Course list from a file
    public static ItemSales loadFromFile(String filename) {
    	System.out.println("Loading file " + filename + " as courseList");
    	System.out.println();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ItemSales) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ItemSales(); // Return a new instance if file not found or error occurs
        }
    }

}
 