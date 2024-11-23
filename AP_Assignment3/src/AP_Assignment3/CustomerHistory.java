package AP_Assignment3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class CustomerHistory implements Serializable{

	private static final long serialVersionUID = -1133139840401709608L;

	private ArrayList<Customer> customers;
	
	public CustomerHistory() {
		customers = new ArrayList<>();
	}
	
	public void addCustomer(Customer customer) {
		customers.add(customer);
	}
	
	public Customer findCustomerByID(String ID) {
		for (Customer c : customers) {
			if (c.getID().equals(ID)) {
				return c;
			}
		}
		return null;
	}
	public Customer findCustomerByUsername(String username) {
		for (Customer c : customers) {
			if (c.getUsername().equals(username)) {
				return c;
			}
		}
		return null;
	}
	public boolean validateCustomer(String username, String password) {
		// Check for special admin credentials
	    if ("admin".equals(username) && "1".equals(password)) {
	        return true;
	    }
		
		Customer toFind = findCustomerByUsername(username);
		if (toFind != null && toFind.getPassword().equals(password)) {
			return true;
		}
		return false;
	}
	
	// Save Course list to a file
	public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Customer History save to file " + filename);
        System.out.println();
    }
    
    // Load Course list from a file
    public static CustomerHistory loadFromFile(String filename) {
    	System.out.println("Loading file " + filename + " as Customer History");
    	System.out.println();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (CustomerHistory) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new CustomerHistory(); // Return a new instance if file not found or error occurs
        }
    }
}
