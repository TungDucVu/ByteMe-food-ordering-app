package AP_Assignment3;

import java.util.Scanner;

public class CustomerScreen {
	private static final String FILE_NAME_MENU = "Menu.ser";
	private static final String FILE_NAME_ORDERHISTORY = "OrderHistory.ser";
	private static final String FILE_NAME_VIP = "VIP.ser";
	private static final String FILE_NAME_NORMAL = "Normal.ser";
	private Menu menu = Menu.loadFromFile(FILE_NAME_MENU);
	private OrderHistory orderHistory = OrderHistory.loadFromFile(FILE_NAME_ORDERHISTORY);
	
	public void printCustomerScreen(Customer customer) throws FoodItemNotFoundException, OrderNotFoundException {
		Scanner scanner = new Scanner(System.in);
	    int choice;

	    // Main loop 
	    while (true) {
	        // Display the main menu
	    	System.out.println("****************************************");
	        System.out.println("****************************************");
	        System.out.println("WELCOME CUSTOMER  ");
	        if (customer.isVIP()) {
	        	System.out.println("YOU ARE A VIP MEMBER!");
	        }
	        System.out.println("1. BROWSE MENU");
	        System.out.println("2. CART OPERATIONS");
	        System.out.println("3. ORDER TRACKING");
	        System.out.println("4. ITEM REVIEWS");
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
	                browseMenu(scanner); 
	                break;

	            case 2:
	                cartOperations(scanner, customer);
	                break;

	            case 3:
	                orderTracking(scanner, customer);
	                break;

	            case 4:
	                itemReviews(scanner, customer, menu);
	                break;

	            case 5:
	                System.out.println("Exiting...Goodbye");
	                return; // Exit the loop and end the program
	        }
	    }
	}
	private void browseMenu(Scanner scanner) throws FoodItemNotFoundException {
		int choice;

	    // Main loop 
	    while (true) {
	        // Display the main menu
	    	System.out.println("****************************************");
	        System.out.println("****************************************");
	        System.out.println("1. VIEW ALL ITEMS");
	        System.out.println("2. SEARCH");
	        System.out.println("3. FILTER BY CATEGORY");
	        System.out.println("4. SORT BY PRICE");
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
	                view(menu); 
	                break;

	            case 2:
	                search(scanner, menu);
	                break;

	            case 3:
	                filterCategory(menu);
	                break;

	            case 4:
	                sortPrice(menu);
	                break;

	            case 5:
	                System.out.println("Exiting...Goodbye");
	                return; // Exit the loop and end the program
	        }
	    }
	}
	private void view(Menu menu) {
		menu.showMenu();
	}
	private void search(Scanner scanner, Menu menu) throws FoodItemNotFoundException{
		String name = "";
		do {
		    name = enterName(scanner);
		    if (name.isEmpty()) continue;
		} while (name.isEmpty());
		
		FoodItem toSearch = null;
		
		try {
			toSearch = menu.getFoodItem(name);
			System.out.println(toSearch);
		} catch (FoodItemNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}
	private void filterCategory(Menu menu) {
		menu.showCategory();
	}
	private void sortPrice(Menu menu) {
		menu.showPriceUp();
	}
	
	
	private void cartOperations(Scanner scanner, Customer customer) throws FoodItemNotFoundException {
		int choice;

	    // Main loop 
	    while (true) {
	        // Display the main menu
	    	System.out.println("****************************************");
	        System.out.println("****************************************");
	        System.out.println("1. ADD ITEMS");
	        System.out.println("2. MODIFY QUANTITIES");
	        System.out.println("3. REMOVE ITEMS");
	        System.out.println("4. VIEW TOTAL");
	        System.out.println("5. CHECK OUT");
	        System.out.println("6. EXIT");

	        // Validate the main menu choice
	        do {
	            while (!scanner.hasNextInt()) {
	                System.out.println("Invalid input. Please enter a number between 1 and 6.");
	                scanner.next();
	            }
	            choice = scanner.nextInt();
	            scanner.nextLine(); // Consume the newline character

	            if (choice < 1 || choice > 6) {
	                System.out.println("Invalid choice. Please enter a number between 1 and 6.");
	            }
	        } while (choice < 1 || choice > 6);

	        // Handle the valid choice
	        switch (choice) {
	            case 1:
	                addItems(scanner, menu, customer); 
	                break;

	            case 2:
	                modifyQuantities(scanner, customer);
	                break;

	            case 3:
	                removeItems(scanner, customer);
	                break;

	            case 4:
	                viewTotal(customer);
	                break;
	                
	            case 5:
	                checkOut(scanner, customer);
	                break;

	            case 6:
	                System.out.println("Exiting...Goodbye");
	                return; // Exit the loop and end the program
	        }
	    }
	}
	private void addItems(Scanner scanner, Menu menu, Customer customer) throws FoodItemNotFoundException {
		menu.showMenu();
		String name = "";
		do {
		    name = enterName(scanner);
		    if (name.isEmpty()) continue;
		} while (name.isEmpty());
		
		int quantity = 0;
		do {
			quantity = enterQuantity(scanner);
			if (quantity <= 0) continue;
		} while (quantity <= 0);
		
		FoodItem toAdd = null;
		
		
		try {
			toAdd = menu.getFoodItem(name);
			customer.addToCart(toAdd, quantity);
		} catch (FoodItemNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		if (customer.isVIP()) {
			customer.saveToFile(FILE_NAME_VIP);
		} else {
			customer.saveToFile(FILE_NAME_NORMAL);
		}
		
		
		
	}
	private void modifyQuantities(Scanner scanner, Customer customer) throws FoodItemNotFoundException {
		customer.showCart();
		String name = "";
		do {
		    name = enterName(scanner);
		    if (name.isEmpty()) continue;
		} while (name.isEmpty());
		
		CartItem toMod = null;
		try {
			int quantity = 0;
			do {
				quantity = enterQuantity(scanner);
				if (quantity <= 0) continue;
			} while (quantity <= 0);
			
			toMod = customer.getCartItem(name);
			toMod.setQuantity(quantity);
		} catch (FoodItemNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		if (customer.isVIP()) {
			customer.saveToFile(FILE_NAME_VIP);
		} else {
			customer.saveToFile(FILE_NAME_NORMAL);
		}
		
		
	}
	private void removeItems(Scanner scanner, Customer customer) throws FoodItemNotFoundException {
		customer.showCart();
		String name = "";
		do {
		    name = enterName(scanner);
		    if (name.isEmpty()) continue;
		} while (name.isEmpty());
		
		CartItem toRemove = null; 
		
		try {
			toRemove = customer.getCartItem(name);
			customer.removeFromCart(toRemove);
		} catch (FoodItemNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		if (customer.isVIP()) {
			customer.saveToFile(FILE_NAME_VIP);
		} else {
			customer.saveToFile(FILE_NAME_NORMAL);
		}
		
	}
	private void viewTotal(Customer customer) {
		customer.showCart();
		System.out.println("****************************************");
		System.out.println("YOUR TOTALL: $" + customer.getTotal());
	}
	private void checkOut(Scanner scanner, Customer customer) {
		viewTotal(customer);
		System.out.println("****************************************");
		System.out.println("SPECIAL REQUEST TO THE CHEF? (SPECIFY THE RQUEST, ELSE: X");
		String request = "";
		do {
			request = enterRequest(scanner);
			if (request.isEmpty()) continue;
		} while (request.isEmpty());

		
		System.out.println("PURCHASE?");
		System.out.println("1. SURE");
		System.out.println("2. NOT YET");
		
		int choice;
		do {
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 2.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice < 1 || choice > 2) {
                System.out.println("Invalid choice. Please enter a number between 1 and 2.");
            }
        } while (choice < 1 || choice > 2);

        // Handle the valid choice
        switch (choice) {
            case 1:
                System.out.println("THANK YOU FOR ORDERING WITH US");
                //
                Order order = new Order(customer, customer.getCart(), customer.getQuantity(), customer.getTotal(), request);
        		orderHistory.addOrder(order);
        		
        		order.saveToFile();
        		orderHistory.saveToFile(FILE_NAME_ORDERHISTORY);
        		customer.getCart().clearCart();
        		if (customer.isVIP()) {
        			customer.saveToFile(FILE_NAME_VIP);
        		} else {
        			customer.saveToFile(FILE_NAME_NORMAL);
        		}
                
                break;
            case 2:
            	System.out.println("Exiting...Goodbye");
                return; // Exit the loop and end the program
		
        }
		
	}
	
	
	private void orderTracking(Scanner scanner, Customer customer) throws OrderNotFoundException {
		int choice;

	    // Main loop 
	    while (true) {
	        // Display the main menu
	    	System.out.println("****************************************");
	        System.out.println("****************************************");
	        System.out.println("1. VIEW LAST ORDER");
	        System.out.println("2. CANCEL ORDER");
	        System.out.println("3. ORDER HISTORY");
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
	                viewOrderStatus(scanner, customer, orderHistory); 
	                break;

	            case 2:
	                cancelOrder(scanner, customer, orderHistory);
	                break;

	            case 3:
	                orderHistory(scanner, customer);
	                break;
	                
	            case 4:
	                System.out.println("Exiting...Goodbye");
	                return; // Exit the loop and end the program
	        }
	    }
	}
	private void viewOrderStatus(Scanner scanner, Customer customer, OrderHistory orderHistory) {
		orderHistory.showCustomerOrder(customer);
	}
	private void cancelOrder(Scanner scanner, Customer customer, OrderHistory orderHistory) throws OrderNotFoundException {
		orderHistory.showCustomerOrder(customer, "PENDING");
		String id = "";
		do {
			id = enterID(scanner);
			if (id.isEmpty()) continue;
		} while (id.isEmpty());
		Order toCancel = null; 
		
		try {
			toCancel = orderHistory.findOrderById(id);
			orderHistory.userCanceled(id);
			
			toCancel.saveToFile();
			orderHistory.saveToFile(FILE_NAME_ORDERHISTORY);
		} catch (OrderNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
	private void orderHistory(Scanner scanner, Customer customer) throws OrderNotFoundException {
		orderHistory.showCustomerOrder(customer);
		System.out.println("****************************************");
		System.out.println("REORDERING?");
		System.out.println("1. YEAH");
		System.out.println("2. NAH");
		int choice;
		do {
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 2.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice < 1 || choice > 2) {
                System.out.println("Invalid choice. Please enter a number between 1 and 2.");
            }
        } while (choice < 1 || choice > 2);

        // Handle the valid choice
        switch (choice) {
            case 1:
            	String id = "";
        		do {
        			id = enterID(scanner);
        			if (id.isEmpty()) continue;
        		} while (id.isEmpty());
        		Order toOrder = null; 
        		
        		try {
        			toOrder = orderHistory.findOrderById(id);
            		Order newOrder = new Order(toOrder.getCustomer(), toOrder.getCart(), toOrder.getQuantity(), toOrder.getTotal(), toOrder.getSpecialRequest());
            		orderHistory.addOrder(newOrder);
            		
            		toOrder.saveToFile();
            		orderHistory.saveToFile(FILE_NAME_ORDERHISTORY);
        		} catch (OrderNotFoundException e) {
        			System.out.println(e.getMessage());
        		}
        		
                break;
            case 2:
            	System.out.println("Exiting...Goodbye");
                return; // Exit the loop and end the program
		
        }
		
		
	}
	
	
	private void itemReviews(Scanner scanner, Customer customer, Menu menu) throws FoodItemNotFoundException, OrderNotFoundException {
		int choice;

	    // Main loop 
	    while (true) {
	        // Display the main menu
	    	System.out.println("****************************************");
	        System.out.println("****************************************");
	        System.out.println("1. GIVE AND VIEW REVIEWS");
	        System.out.println("2. EXIT");

	        // Validate the main menu choice
	        do {
	            while (!scanner.hasNextInt()) {
	                System.out.println("Invalid input. Please enter a number between 1 and 2.");
	                scanner.next();
	            }
	            choice = scanner.nextInt();
	            scanner.nextLine(); // Consume the newline character

	            if (choice < 1 || choice > 2) {
	                System.out.println("Invalid choice. Please enter a number between 1 and 2.");
	            }
	        } while (choice < 1 || choice > 2);

	        // Handle the valid choice
	        switch (choice) {
	            case 1:
	                giveReview(scanner, customer, menu, orderHistory); 
	                break;

	            case 2:
	            	System.out.println("Exiting...Goodbye");
	                return; // Exit the loop and end the program
	        }
	    }

	}
	private void giveReview(Scanner scanner, Customer customer, Menu menu, OrderHistory orderHistory) throws FoodItemNotFoundException, OrderNotFoundException {
		System.out.println("****************************************");
		menu.showReview();
		System.out.println("****************************************");
		orderHistory.showCustomerOrder(customer, "DELIVERED");
		String id = "";
		do {
			id = enterID(scanner);
			if (id.isEmpty()) continue;
		} while (id.isEmpty());
		
		Order toFind = orderHistory.findOrderById(id);
		toFind.showItem();
		
		String name = "";
		do {
			name = enterName(scanner);
			if (name.isEmpty()) continue;
		} while (name.isEmpty());
		
		FoodItem toReview = null; 
		
		try {
			toReview = menu.getFoodItem(name);
			
			String review = "";
			do {
				review = enterReview(scanner);
				if (review.isEmpty()) continue;
			} while (review.isEmpty());
			
			menu.addReview(toReview, review);
			menu.saveToFile(FILE_NAME_MENU);
		} catch (FoodItemNotFoundException e) {
			
			System.out.println(e.getMessage());
		}
		
		
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
	private String enterID(Scanner scanner) {
	    System.out.println("****************************************");
	    System.out.println("ENTER ID:");
	    String id = scanner.nextLine().trim();
	    if (id.isEmpty()) {
	        System.out.println("Invalid ID. Please enter the ID again.");
	    }
	    return id;
	}
	private String enterRequest(Scanner scanner) {
	    System.out.println("****************************************");
	    System.out.println("ENTER REQUEST:");
	    String name = scanner.nextLine().trim();
	    if (name.isEmpty()) {
	        System.out.println("Invalid name. Please enter the name again.");
	    }
	    return name;
	}
	private int enterQuantity(Scanner scanner) {
	    System.out.println("****************************************");
	    System.out.println("ENTER QUANTITY:");
	    String input = scanner.nextLine().trim();
	    int quantity = -1;

	    try {
	        quantity = Integer.parseInt(input);
	        if (quantity <= 0) throw new IllegalArgumentException();
	    } catch (Exception e) {
	        System.out.println("Invalid quantity. Please enter the quantity again.");
	    }
	    return quantity;
	}
	private String enterReview(Scanner scanner) {
	    System.out.println("****************************************");
	    System.out.println("ENTER REVIEW (YOUR REIVEW, ELSE X):");
	    String name = scanner.nextLine().trim();
	    if (name.isEmpty()) {
	        System.out.println("Invalid Review. Please enter the Review again.");
	    }
	    return name;
	}
}
