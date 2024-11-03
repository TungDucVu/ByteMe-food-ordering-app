package AP_Assignment3;

import java.io.Serializable;
import java.util.Comparator;

public class CategoryComparator implements Serializable, Comparator<FoodItem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1764200539798519878L;

	@Override
	public int compare(FoodItem item1, FoodItem item2) {
        // First, compare by category
        int categoryComparison = item1.getCategory().compareTo(item2.getCategory());
        if (categoryComparison != 0) {
            return categoryComparison; // Different categories
        }
        // If categories are the same, compare by name (or you can use price if needed)
        return item1.getName().compareTo(item2.getName());
    }

}
