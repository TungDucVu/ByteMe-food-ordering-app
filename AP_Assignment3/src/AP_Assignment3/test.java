package AP_Assignment3;

import java.util.Scanner;

public class test {
	private static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		OrderHistory orderHistory = OrderHistory.loadFromFile("OrderHistory.ser");
		Customer VIP = Customer.loadFromFile("VIP.ser");
		
		orderHistory.showCustomerOrder(VIP);
		orderHistory.viewOrders();
		
	}
}
