package AP_Assignment3;

import java.util.PriorityQueue;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class OrderHistory implements Serializable{
	private static final long serialVersionUID = -4918703967689965515L;
	private PriorityQueue<Order> orderQueue;
	
	public OrderHistory() {
		orderQueue = new PriorityQueue<>(new VIPComparator());

    }
	
    public PriorityQueue<Order> getOrderQueue() {
    	return orderQueue;
    }
	
	public void addOrder(Order order) {
        orderQueue.offer(order);
    }

    public Order processNextOrder() {
        Order nextOrder = orderQueue.poll();
        if (nextOrder != null) {
            nextOrder.setStatus("Completed");
        }
        return nextOrder;
    }
    public void showCustomerOrder(Customer customer) {
    	for (Order order : orderQueue) {
			if (order.getCustomer().getID().equals(customer.getID())) {
				System.out.println(order);
				order.getCart().showCartItems();
			}
		}
    }
    public void showCustomerOrder(Customer customer, String status) {
    	for (Order order : orderQueue) {
			if (order.getCustomer().getID().equals(customer.getID()) && order.getStatus().equals(status)) {
				System.out.println(order);
				order.getCart().showCartItems();
			}
		}
    }
    //fix bug order status
    public Order findOrderById(String id) throws OrderNotFoundException{
    	Order toFind= null;
        for (Order order : orderQueue) {
            if (order.getID().equals(id)) { // Assuming Order has a getId() method
                toFind = order;
            }
        }
        if (toFind == null) {
        	throw new OrderNotFoundException("The Order '" + id + "' does not exist in your history.");
        }
        return toFind; // Return null if the order is not found
    }
    public void updateOrderStatus(String id, String newStatus) throws OrderNotFoundException {
        Order order = findOrderById(id); // Locate the order in OrderHistory
        if (order != null) {
            order.setStatus(newStatus);  // Update status in OrderHistory
            
        } else {
            System.out.println("Order ID not found in OrderHistory.");
        }
    }

    public void processRefund(String orderId) {
        for (Order order : orderQueue) {
            if (order.getID().equals(orderId)) {
                order.setStatus("CANCELED");
                System.out.println("Refund processed for Order ID: " + orderId);
                break;
            }
        }
    }

    public void viewOrders(String status) {
        for (Order order : orderQueue) {
        	if (order.getStatus().equals(status)) {
        		System.out.println(order);
        	}
            
        }
    }
    
    public void viewOrders() {
        for (Order order : orderQueue) {
    		System.out.println(order);
        }
    }
    
    public void setCanceled(String name) {
    	for (Order order : orderQueue) {
            for (CartItem item : order.getCart().getItems()) {
            	if (item.getFoodItem().getName().equals(name)) {
            		order.setStatus("CANCELED");
            	}
            }
        }
    }
    
    public void userCanceled(String id) {
    	for (Order order : orderQueue) {
    		if (order.getID().equals(id)) {
    			order.setStatus("CANCELED");
    			break;
    		}
    	}
    }

    public void showSpecial() {
        for (Order order : orderQueue) {
            if (!order.getSpecialRequest().equalsIgnoreCase("x")) {
                System.out.println(order);
            }
        }
    }

 // Save Course list to a file
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Order History save to file " + filename);
        System.out.println();
    }
    
    // Load Course list from a file
    public static OrderHistory loadFromFile(String filename) {
    	System.out.println("Loading file " + filename + " as courseList");
    	System.out.println();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (OrderHistory) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new OrderHistory(); // Return a new instance if file not found or error occurs
        }
    }
}
