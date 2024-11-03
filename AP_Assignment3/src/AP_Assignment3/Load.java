package AP_Assignment3;

public class Load {
	private static final String FILE_NAME_MENU = "Menu.ser";
	private static final String FILE_NAME_ORDERHISTORY = "OrderHistory.ser";
	private static final String FILE_NAME_ORDERID = "OrderID.ser";
	private static final String FILE_NAME_CUSTOMERID = "CustomerID.ser";
	private static final String FILE_NAME_VIP = "VIP.ser";
	private static final String FILE_NAME_NORMAL = "Normal.ser";
	
	public static void main(String[] args) {
		Menu menu = new Menu();
		OrderHistory orderHistory = new OrderHistory();
		RandomStringGenerator orderID = new RandomStringGenerator();
		RandomStringGenerator customerID = new RandomStringGenerator();
		
		Customer VIP = new Customer("VIP", true);
		Customer Normal = new Customer("Normal", false);
		
		menu.addItem(new FoodItem("Pizza", 3.0, "Hot Meal"));
		menu.addItem(new FoodItem("Burger", 4.5, "Hot Meal"));
		menu.addItem(new FoodItem("Pasta", 5.0, "Hot Meal"));

		menu.addItem(new FoodItem("Coffee", 2.0, "Drink"));
		menu.addItem(new FoodItem("Soft Drink", 1.5, "Drink"));
		menu.addItem(new FoodItem("Iced Tea", 2.0, "Drink"));

		menu.addItem(new FoodItem("French Fries", 2.5, "Snack"));
		menu.addItem(new FoodItem("Chocolate Chip Cookie", 1.0, "Snack"));
		menu.addItem(new FoodItem("Potato Chips", 1.5, "Snack"));
		
		menu.saveToFile(FILE_NAME_MENU);
		orderHistory.saveToFile(FILE_NAME_ORDERHISTORY);
		orderID.saveToFile(FILE_NAME_ORDERID);
		customerID.saveToFile(FILE_NAME_CUSTOMERID);
		VIP.saveToFile(FILE_NAME_VIP);
		Normal.saveToFile(FILE_NAME_NORMAL);
		
		System.out.println("LOAD COMPLETE");

		
	}
}
