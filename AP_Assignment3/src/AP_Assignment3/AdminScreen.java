package AP_Assignment3;

import java.util.Scanner;

public class AdminScreen {
	private static final String FILE_NAME_MENU = "Menu.ser";
	private static final String FILE_NAME_ORDERHISTORY = "OrderHistory.ser";
	private static final String FILE_NAME_ITEMSALES = "ItemSales.ser";
	private static final String FILE_NAME_VIP = "VIP.ser";
	private static final String FILE_NAME_NORMAL = "Normal.ser";
	private Menu menu = Menu.loadFromFile(FILE_NAME_MENU);
	private OrderHistory orderHistory = OrderHistory.loadFromFile(FILE_NAME_ORDERHISTORY);
	private Customer VIP = Customer.loadFromFile(FILE_NAME_VIP);
	private Customer Normal = Customer.loadFromFile(FILE_NAME_NORMAL);
	
	public void printAdmin() throws FoodItemNotFoundException, OrderNotFoundException {
		Scanner scanner = new Scanner(System.in);
	    int choice;

	    // Main loop 
	    while (true) {
	        // Display the main menu
	    	System.out.println("****************************************");
	        System.out.println("****************************************");
	        System.out.println("WELCOME ADMIN  ");
	        System.out.println("1. MENU MANAGEMENT");
	        System.out.println("2. ORDER MANAGEMENT");
	        System.out.println("3. REPORT GENERATOR");
	        System.out.println("4. EXIT");

	        // Validate the main menu choice
	        do {
	            while (!scanner.hasNextInt()) {
	                System.out.println("Invalid input. Please enter a number between 1 and 4.");
	                scanner.next();
	            }
	            choice = scanner.nextInt();
	            scanner.nextLine(); // Consume the newline character

	            if (choice < 1 || choice > 4) {
	                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
	            }
	        } while (choice < 1 || choice > 4);

	        // Handle the valid choice
	        switch (choice) {
	            case 1:
	                menuManagement(scanner); 
	                break;

	            case 2:
	                orderManagement(scanner);
	                break;

	            case 3:
	                reportGenerator(scanner, orderHistory);
	                break;

	            case 4:
	                System.out.println("Exiting...Goodbye");
	                return; // Exit the loop and end the program
	        }
	    }
	}
	
	private void menuManagement(Scanner scanner) throws FoodItemNotFoundException {
		int choice;

	    // Main loop 
	    while (true) {
	        // Display the main menu
	    	System.out.println("****************************************");
	        System.out.println("****************************************");
	        System.out.println("1. ADD NEW ITEMS");
	        System.out.println("2. UPDATE EXISTING ITEMS");
	        System.out.println("3. REMOVE ITEMS");
	        System.out.println("4. EXIT");

	        // Validate the main menu choice
	        do {
	            while (!scanner.hasNextInt()) {
	                System.out.println("Invalid input. Please enter a number between 1 and 4.");
	                scanner.next();
	            }
	            choice = scanner.nextInt();
	            scanner.nextLine(); // Consume the newline character

	            if (choice < 1 || choice > 4) {
	                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
	            }
	        } while (choice < 1 || choice > 4);

	        // Handle the valid choice
	        switch (choice) {
	            case 1:
	                addItem(scanner, menu); 
	                break;

	            case 2:
	                updateItem(scanner, menu);
	                break;

	            case 3:
	                removeItem(scanner, menu, orderHistory);
	                break;

	            case 4:
	                System.out.println("Exiting...Goodbye");
	                return; // Exit the loop and end the program
	        }
	    }
	}
	private void addItem(Scanner scanner, Menu menu) {
		System.out.println("****************************************");
		menu.showMenu();
	    String name = "";
	    float price = 0;
	    String category = "";

	    do {
	        name = enterName(scanner);
	        if (name.isEmpty()) continue;

	        price = enterPrice(scanner);
	        if (price < 0) continue;

	        category = enterCategory(scanner);
	        if (category.isEmpty()) continue;


	    } while (name.isEmpty() || category.isEmpty() || price < 0);

	    FoodItem item = new FoodItem(name, price, category);
	    menu.addItem(item);
	    System.out.println("Item added successfully!");
	    
	    menu.saveToFile(FILE_NAME_MENU);
	}
	private void updateItem(Scanner scanner, Menu menu) throws FoodItemNotFoundException {
		System.out.println("****************************************");
		menu.showMenu();
		String name = "";
		do {
		    name = enterName(scanner);
		    if (name.isEmpty()) continue;
		} while (name.isEmpty());
		
		FoodItem item = menu.getFoodItem(name);
		
		int choice;
		while (true) {
	        System.out.println("****************************************");
	        System.out.println("1. UPDATE NAME");
	        System.out.println("2. UPDATE PRICE");
	        System.out.println("3. UPDATE CATEGORY");
	        System.out.println("4. EXIT");

	        // Validate the main menu choice
	        do {
	            while (!scanner.hasNextInt()) {
	                System.out.println("Invalid input. Please enter a number between 1 and 4.");
	                scanner.next();
	            }
	            choice = scanner.nextInt();
	            scanner.nextLine(); // Consume the newline character

	            if (choice < 1 || choice > 4) {
	                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
	            }
	        } while (choice < 1 || choice > 4);

		switch (choice) {
	        case 1:
	        	String newName = "";
	        	do {
	        		newName = enterName(scanner);
	        		if (newName.isEmpty()) continue;
	        	} while (newName.isEmpty());
	        	item.setName(newName);
	        	menu.saveToFile(FILE_NAME_MENU);
	            break;
	
	        case 2:
	            float newPrice = 0;
	            do {
	            	newPrice = enterPrice(scanner);
	            	if (newPrice < 0) continue;
	            } while (newPrice < 0);
	            item.setPrice(newPrice);
	            menu.saveToFile(FILE_NAME_MENU);
	            break;
	
	        case 3:
	        	String newCategory = "";
	        	do {
	        		newCategory = enterCategory(scanner);
	        		if (newCategory.isEmpty()) continue;
	        	} while (newCategory.isEmpty());
	        	item.setCategory(newCategory);
	        	menu.saveToFile(FILE_NAME_MENU);
	            break;
	            
	        case 4:
	            System.out.println("Exiting...Goodbye");
	            return; // Exit the loop and end the program
			}
		
		}
	}
	private void removeItem(Scanner scanner, Menu menu, OrderHistory orderHistory) throws FoodItemNotFoundException {
		System.out.println("****************************************");
		menu.showMenu();
		String name = "";
    	do {
    		name = enterName(scanner);
    		if (name.isEmpty()) continue;
    	} while (name.isEmpty());
    	
    	menu.removeItem(name);
    	orderHistory.setCanceled(name);
    	
    	menu.saveToFile(FILE_NAME_MENU);
    	orderHistory.saveToFile(FILE_NAME_ORDERHISTORY);
    	VIP.saveToFile(FILE_NAME_VIP);
    	Normal.saveToFile(FILE_NAME_NORMAL);
	}
	
	
	private void orderManagement(Scanner scanner) throws OrderNotFoundException {
		int choice;

	    // Main loop 
	    while (true) {
	        // Display the main menu
	    	System.out.println("****************************************");
	        System.out.println("****************************************");
	        System.out.println("1. VIEW PENDING ORDERS");
	        System.out.println("2. UPDATE ORDER STATUS");
	        System.out.println("3. PROCESS REFUNDS");
	        System.out.println("4. HANDLE SPEACIAL REQUEST");
	        System.out.println("5. EXIT");

	        // Validate the main menu choice
	        do {
	            while (!scanner.hasNextInt()) {
	                System.out.println("Invalid input. Please enter a number between 1 and 5.");
	                scanner.next();
	            }
	            choice = scanner.nextInt();
	            scanner.nextLine(); // Consume the newline character

	            if (choice < 1 || choice > 5) {
	                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
	            }
	        } while (choice < 1 || choice > 5);

	        // Handle the valid choice
	        switch (choice) {
	            case 1:
	                viewPending(scanner, orderHistory); 
	                break;

	            case 2:
	                updateStatus(scanner, orderHistory);
	                break;

	            case 3:
	                processRefunds(scanner, orderHistory);
	                break;
	                
	            case 4:
	            	handleSpecial(scanner, orderHistory);
	            	break;
	            	
	            case 5:
	                System.out.println("Exiting...Goodbye");
	                return; // Exit the loop and end the program
	        }
	    }
	}
	private void viewPending(Scanner scanner, OrderHistory orderHistory) {
		orderHistory.viewOrders("PENDING");
	}
	private void updateStatus(Scanner scanner, OrderHistory orderHistory) throws OrderNotFoundException {
		orderHistory.viewOrders();
		
		System.out.println("ENTER ID: ");
		String id = "";
		do {
		    id = scanner.nextLine().trim();
		    if (id.isEmpty()) {
		    	System.out.println("Invalid ID. Please enter the ID again");
		    	continue;
		    }
		} while (id.isEmpty());
		
		System.out.println("ENTER NEW STATUS: (PENDING, CONFIRMED, DELIVERING, DELIVERED, CANCELED)");
		String newStatus = "";
		do {
		    newStatus = scanner.nextLine().trim();
		    if (newStatus.isEmpty() || (!newStatus.equals("PENDING") && (!newStatus.equals("CONFIRMED") && !newStatus.equals("PROCESSING") && !newStatus.equals("DELIVERING") && !newStatus.equals("DELIVERED") && !newStatus.equals("CANCELED")))) {
		        System.out.println("Invalid Status. Please enter the Status again");
		    }
		} while (newStatus.isEmpty() || (!newStatus.equals("PENDING") && (!newStatus.equals("CONFIRMED") && !newStatus.equals("PROCESSING") && !newStatus.equals("DELIVERING") && !newStatus.equals("DELIVERED") && !newStatus.equals("CANCELED"))));
		
		try {			
			orderHistory.updateOrderStatus(id, newStatus);
			orderHistory.saveToFile(FILE_NAME_ORDERHISTORY);
		} catch (OrderNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	private void processRefunds(Scanner scanner, OrderHistory orderHistory) {
		orderHistory.viewOrders("CANCELED");
		
		System.out.println("PROCESSING REFUNDS? ENTER ORDER ID");
		String id = "";
		do {
		    id = scanner.nextLine().trim();
		    if (id.isEmpty()) {
		    	System.out.println("Invalid ID. Please enter the ID again");
		    	continue;
		    }
		} while (id.isEmpty());
		
		orderHistory.processRefund(id);
		
		orderHistory.saveToFile(FILE_NAME_ORDERHISTORY);
		VIP.saveToFile(FILE_NAME_VIP);
		Normal.saveToFile(FILE_NAME_NORMAL);
	}
	private void handleSpecial(Scanner scanner, OrderHistory orderHistory) {
		orderHistory.showSpecial();
	}
	
	private void reportGenerator(Scanner scanner, OrderHistory orderHistory) {
		orderHistory.viewOrders();
		
		ItemSales itemSales = new ItemSales();
		itemSales.generateItemSalesReport(orderHistory);
		
		itemSales.saveToFile(FILE_NAME_ITEMSALES);
	}
	
	private String enterName(Scanner scanner) {
	    System.out.println("****************************************");
	    System.out.println("ENTER NAME:");
	    String name = scanner.nextLine().trim();
	    if (name.isEmpty()) {
	        System.out.println("Invalid name. Please enter the name again.");
	    }
	    return name;
	}
	private float enterPrice(Scanner scanner) {
	    System.out.println("****************************************");
	    System.out.println("ENTER PRICE:");
	    String input = scanner.nextLine().trim();
	    float price = -1;

	    try {
	        price = Float.parseFloat(input);
	        if (price < 0) throw new IllegalArgumentException();
	    } catch (Exception e) {
	        System.out.println("Invalid price. Please enter the price again.");
	    }
	    return price;
	}
	private String enterCategory(Scanner scanner) {
	    System.out.println("****************************************");
	    System.out.println("ENTER CATEGORY:");
	    String category = scanner.nextLine().trim();
	    if (category.isEmpty()) {
	        System.out.println("Invalid category. Please enter the category name again.");
	    }
	    return category;
	}
	

}
