package AP_Assignment3;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodItem implements Serializable{
	private static final long serialVersionUID = 5932805617255617499L;
	private String name;
	private double price;
	private String category;
	private boolean availability;
	private ArrayList<String> reviews;
	
	public FoodItem(String name, double price, String category) {
		this.name = name;
		this.price = price;
		this.category = category;
		this.availability = true;
		this.reviews = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	public String getCategory() {
		return category;
	}
	public boolean isAvailable() {
		return availability;
	}
	public ArrayList<String> getReview() {
		return reviews;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
	public void addReview(String review) {
		reviews.add(review);
	}
	
	@Override
	public String toString() {
		return name + " | Price: " + price + " | Category: " + category + " | Availability: " + availability;
	}
	
	public void showReview() {
		System.out.print(this.getName() + ": ");
		reviews.forEach(review -> {
		    System.out.print(review + ", ");
		});
		//System.out.println("\n");
	}
	
	

}
