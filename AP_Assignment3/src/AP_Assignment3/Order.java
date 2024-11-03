package AP_Assignment3;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Order implements Serializable{
	private static final long serialVersionUID = 5496248348632862548L;
	private static final String FILE_NAME_ORDERID = "OrderID.ser";
	private String ID;
	private Customer customer;
	private Cart cart;
	private int quantity;
	private double total;
	private String status; // pending, processing, delivering, delivered, cancelled
	private String orderTime;
	private RandomStringGenerator IDGenerator = RandomStringGenerator.loadFromFile(FILE_NAME_ORDERID);
	private String specialRequest;


	public Order(Customer customer, Cart cart, int quantity, double total, String specialRequest) {
		this.ID = IDGenerator.generateRandomString();
		this.customer = customer;
		this.cart = cart;
		this.quantity = quantity;
		this.total = total;
		this.status = "PENDING";
		this.specialRequest = specialRequest;
		setTime();
		
	}
	
	public String getID() {
		return ID;
	}
	public Customer getCustomer() {
		return customer;
	}
	public String getStatus() {
		return status;
	}
	public String getTime() {
		return orderTime;
	}
	public Cart getCart() {
		return cart;
	}
	public int getQuantity() {
		return quantity;
	}
	public double getTotal() {
		return total;
	}
	public String getSpecialRequest() {
		return specialRequest;
	}
	public boolean isVIP() {
		return customer.isVIP();
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public void setTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        orderTime = formattedDateTime;
	}
	public void setSpeacialRequest(String request) {
		this.specialRequest = request;
	}

	public void showItem() {
		cart.showCartItems();
	}
	
	public boolean hasItem(String item){
		CartItem toHave = cart.getCartItem(item);
		return !toHave.equals(null);
	}
	
	public void saveToFile() {
		IDGenerator.saveToFile(FILE_NAME_ORDERID);
	}
	
	@Override
	public String toString() {
		return customer.getName() +  " | Order " + ID + " | Status: " + status + " | Quantity: " + quantity + " | Total: " + total + " | Order Time: " + orderTime + " | Special Request:" + specialRequest; 
	}
	
}
