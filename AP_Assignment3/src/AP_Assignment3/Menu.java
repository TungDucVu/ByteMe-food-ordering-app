package AP_Assignment3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.TreeMap;

public class Menu implements Serializable{
	private static final long serialVersionUID = -7127092267264127076L;
	private TreeMap<String, FoodItem> menu;
	private TreeMap<FoodItem, String> category;
	private TreeMap<FoodItem, Double> priceUp;
	
	public Menu() {
		menu = new TreeMap<>();
		category = new TreeMap<>(new CategoryComparator());
		priceUp = new TreeMap<>(new PriceComparator());
	}

	
	public void showMenu() {
		menu.forEach((itemName, foodItem) -> {
		    System.out.println(foodItem);
		});
	}
	public void showCategory() {
		category.forEach((foodItem, category) -> {
		    System.out.println("Category: " + category + ": " + foodItem);
		});
	}
	public void showPriceUp() {
		priceUp.forEach((foodItem, price) -> {
		    System.out.println("Price: " + price + ": " + foodItem);
		});
	}
	public void showReview() {
		menu.forEach((itemName, foodItem) -> {
		    foodItem.showReview();
		});
	}
	
	public FoodItem getFoodItem(String itemName) throws FoodItemNotFoundException {
	    FoodItem item = menu.get(itemName);
	    if (item == null) {
	        throw new FoodItemNotFoundException("The food item '" + itemName + "' does not exist in the menu.");
	    }
	    
	    return item;
	}
	
	public void addItem(FoodItem food) {
		menu.put(food.getName(), food);
		category.put(food, food.getCategory());
		priceUp.put(food, food.getPrice());
	}
	
	public void addReview(FoodItem food, String review) {
		food.addReview(review);
	}

	public FoodItem removeItem(String itemName) throws FoodItemNotFoundException{
		FoodItem toRemove = getFoodItem(itemName);
		menu.remove(toRemove.getName());
		return toRemove;
	}
	
	// Save Course list to a file
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Menu save to file " + filename);
        System.out.println();
    }
    
    // Load Course list from a file
    public static Menu loadFromFile(String filename) {
    	System.out.println("Loading file " + filename + " as courseList");
    	System.out.println();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Menu) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Menu(); // Return a new instance if file not found or error occurs
        }
    }
}
