package AP_Assignment3;

import java.io.Serializable;
import java.util.Comparator;

public class PriceComparator implements Serializable, Comparator<FoodItem>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7784458675664187322L;

		@Override
	    public int compare(FoodItem item1, FoodItem item2) {
	        // First, compare by price in ascending order
	        int priceComparison = Double.compare(item1.getPrice(), item2.getPrice());
	        if (priceComparison != 0) {
	            return priceComparison; // Different prices
	        }
	        // If prices are the same, compare by name in ascending order
	        return item1.getName().compareTo(item2.getName());
	    }
	

}
