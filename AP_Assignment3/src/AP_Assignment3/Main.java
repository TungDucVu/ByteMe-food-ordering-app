package AP_Assignment3;

import java.util.Scanner;

public class Main {
	AdminScreen AdminScreen;
	CustomerScreen CustomerScreen;
	Scanner scanner;
	Customer VIP;
	Customer Normal;
	private static final String FILE_NAME_VIP = "VIP.ser";
	private static final String FILE_NAME_NORMAL = "Normal.ser";
	
	
	Main() throws FoodItemNotFoundException, OrderNotFoundException {
		AdminScreen = new AdminScreen();
		CustomerScreen = new CustomerScreen();
		scanner = new Scanner(System.in);
		VIP = Customer.loadFromFile(FILE_NAME_VIP);
		Normal = Customer.loadFromFile(FILE_NAME_NORMAL);
		
		int role = chooseRole();
		if (role == 1) {
			AdminScreen.printAdmin();
		}
		else if (role == 2) {
			CustomerScreen.printCustomerScreen(VIP);
		}
		else if (role == 3) {
			CustomerScreen.printCustomerScreen(Normal);
		}
		
	}
	
	private int chooseRole() {
		System.out.println("****************************************");
		System.out.println("1. ADMIN");
		System.out.println("2. VIP CUSTOMER");
		System.out.println("3. NORMAL CUSTOMER");
		System.out.println("4. EXIT");
		int choice;
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
		return choice;
	}
	
	
	
	public static void main(String[] args) throws FoodItemNotFoundException, OrderNotFoundException {
		new Main();
	}
}
