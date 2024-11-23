package AP_Assignment3;

import javax.swing.*;
import java.awt.*;

public class AdminScreenUI extends JFrame{
	private static final String FILE_NAME_MENU = "Menu.ser";
	private static final String FILE_NAME_ORDERHISTORY = "OrderHistory.ser";
	private static final String FILE_NAME_ITEMSALES = "ItemSales.ser";
	private static final String FILE_NAME_CUSTOMERHISTORY = "CustomerHistory.ser";
	
	private CustomerHistory customers = CustomerHistory.loadFromFile(FILE_NAME_CUSTOMERHISTORY);
	private Menu menu = Menu.loadFromFile(FILE_NAME_MENU);
	private OrderHistory orderHistory = OrderHistory.loadFromFile(FILE_NAME_ORDERHISTORY);
	
	public AdminScreenUI() {
        setTitle("Admin Screen");
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Welcome Message Panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridLayout(3, 1));
        JLabel welcomeLabel = new JLabel("Welcome, Admin!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        welcomePanel.add(welcomeLabel);

        // Button Panel for Actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3, 10, 10));
        
        JButton menuManagementButton = new JButton("Menu Management");
        JButton orderManagementButton = new JButton("Order Management");
        JButton reportGeneratingButton = new JButton("Report Generator");
        JButton exitButton = new JButton("Exit");
        
        buttonPanel.add(menuManagementButton);
        buttonPanel.add(orderManagementButton);
        buttonPanel.add(reportGeneratingButton);
        buttonPanel.add(exitButton);

        // Add Panels to Frame
        add(welcomePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Button Listeners
        menuManagementButton.addActionListener(e -> menuManagement());
        orderManagementButton.addActionListener(e -> orderManagement());
        reportGeneratingButton.addActionListener(e -> reportGenerator());
        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
	
	private void menuManagement() {
		JFrame menuFrame = new JFrame("Browse Menu");
        menuFrame.setSize(960, 540);
        menuFrame.setLayout(new BorderLayout());
        JTextArea menuDisplay = new JTextArea(menu.showMenu());
        menuDisplay.setEditable(false);

        JPanel filterPanel = new JPanel();
        JButton addButton = new JButton("Add items");
        JButton updateButton = new JButton("Update items");
        JButton removeButton = new JButton("Remove items");
        JButton exitButton = new JButton("Back");

        filterPanel.add(addButton);
        filterPanel.add(updateButton);
        filterPanel.add(exitButton);

        menuFrame.add(new JScrollPane(menuDisplay), BorderLayout.CENTER);
        menuFrame.add(filterPanel, BorderLayout.SOUTH);
        menuFrame.setVisible(true);
        
        // center
        menuFrame.setLocationRelativeTo(null);

        // Add listeners for each button action
        addButton.addActionListener(e -> addItem(menuDisplay, menuFrame));
        updateButton.addActionListener(e -> updateItem(menuDisplay, menuFrame));
        removeButton.addActionListener(e -> removeItem(menuDisplay, menuFrame));
        exitButton.addActionListener(e -> menuFrame.dispose());
	}
	private void addItem(JTextArea display, JFrame menuFrame) {
        String itemName = JOptionPane.showInputDialog(menuFrame, "Enter item name:");
        String priceStr = JOptionPane.showInputDialog(menuFrame, "Enter item price:");
        String category = JOptionPane.showInputDialog(menuFrame, "Enter item category:");

        try {
            float price = Float.parseFloat(priceStr);
            menu.addItem(new FoodItem(itemName, price, category));
            JOptionPane.showMessageDialog(this, "Item added!");
            display.setText(menu.showMenu());
            menu.saveToFile(FILE_NAME_MENU);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(menuFrame, "Invalid input or item not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	private void updateItem(JTextArea display, JFrame menuFrame) {
        String itemName = JOptionPane.showInputDialog(menuFrame, "Enter item name:");

        try {
            FoodItem item = menu.getFoodItem(itemName);
            String choice = JOptionPane.showInputDialog(menuFrame, "Updating Name / Price / Category / Availability:");
            
            if (choice.equalsIgnoreCase("name")) {
            	String name = JOptionPane.showInputDialog(menuFrame, "Enter item name:");
            	item.setName(name);
            } else if (choice.equalsIgnoreCase("price" )) {
            	String priceStr = JOptionPane.showInputDialog(menuFrame, "Enter item price:");
            	float price = Float.parseFloat(priceStr);
            	item.setPrice(price);
            } else if (choice.equalsIgnoreCase("category")) {
            	String category = JOptionPane.showInputDialog(menuFrame, "Enter item category:");
            	item.setCategory(category);
            } else if (choice.equalsIgnoreCase("availability")) {
            	item.setAvailability(!item.isAvailable());
            	JOptionPane.showMessageDialog(this, "Item availability set to " + item.isAvailable());
            }

            JOptionPane.showMessageDialog(this, "Item updated!");
            menu.saveToFile(FILE_NAME_MENU);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(menuFrame, "Invalid input or item not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	private void removeItem(JTextArea display, JFrame menuFrame) {
        String itemName = JOptionPane.showInputDialog(menuFrame, "Enter item name:");
        
        try {
        	menu.removeItem(itemName);
        	orderHistory.setCanceled(itemName);
            JOptionPane.showMessageDialog(this, "Item removed!");
            menu.saveToFile(FILE_NAME_MENU);
            orderHistory.saveToFile(FILE_NAME_ORDERHISTORY);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(menuFrame, "Invalid input or item not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	private void orderManagement() {
		JFrame orderFrame = new JFrame("Order Management");
        orderFrame.setSize(960, 540);
        orderFrame.setLayout(new BorderLayout());

        JTextArea orderDisplay = new JTextArea(orderHistory.viewOrders());
        orderDisplay.setEditable(false);
        
        JPanel trackPanel = new JPanel();
        JButton viewPendingButton = new JButton("View PENDING Order");
        JButton updateStatusButton = new JButton("Update Order Status");
        JButton processRefundButton = new JButton("Process Refunds");
        JButton specialRequestButton = new JButton("Handling Special Request");
        JButton exitButton = new JButton("Back");

        trackPanel.add(viewPendingButton);
        trackPanel.add(updateStatusButton);
        trackPanel.add(processRefundButton);
        trackPanel.add(specialRequestButton);
        trackPanel.add(exitButton);

        orderFrame.add(new JScrollPane(orderDisplay), BorderLayout.CENTER);
        orderFrame.add(trackPanel, BorderLayout.SOUTH);
        orderFrame.setVisible(true);
        
        orderFrame.setLocationRelativeTo(null);

        viewPendingButton.addActionListener(e -> orderDisplay.setText(orderHistory.viewOrders("PENDING")));
        updateStatusButton.addActionListener(e -> updateStatus(orderDisplay, orderFrame));
        processRefundButton.addActionListener(e -> processRefunds(orderDisplay, orderFrame));
        specialRequestButton.addActionListener(e -> orderDisplay.setText(orderHistory.showSpecial()));
        exitButton.addActionListener(e -> orderFrame.dispose());
	}
	private void updateStatus(JTextArea display, JFrame orderFrame) {
		String id = JOptionPane.showInputDialog(orderFrame, "Enter the id of the order to search:");
        if (id != null && !id.isEmpty()) {
            try {
            	display.setText(orderHistory.findOrderById(id).toString());
            	String status = JOptionPane.showInputDialog(orderFrame, "Enter order status (PENDING, CONFIRMED, DELIVERING, DELIVERED, CANCELED):");
            	orderHistory.updateOrderStatus(id, status);
            	JOptionPane.showMessageDialog(this, "Status updated!");
            	orderHistory.saveToFile(FILE_NAME_ORDERHISTORY);
            	
            } catch (OrderNotFoundException e) {
            	JOptionPane.showMessageDialog(orderFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
        }
	}
	private void processRefunds(JTextArea display, JFrame orderFrame) {
		display.setText(orderHistory.viewOrders("CANCLED"));
		String id = JOptionPane.showInputDialog(orderFrame, "Enter the id of the order to search:");
        if (id != null && !id.isEmpty()) {
            try {
            	display.setText(orderHistory.findOrderById(id).toString());
            	orderHistory.processRefund(id);
            	JOptionPane.showMessageDialog(this, "Order refunded!");
            	orderHistory.saveToFile(FILE_NAME_ORDERHISTORY);
            	
            } catch (OrderNotFoundException e) {
            	JOptionPane.showMessageDialog(orderFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
        }
	}

	private void reportGenerator() {
		JFrame reportFrame = new JFrame("Report Generator");
        reportFrame.setSize(960, 540);
        reportFrame.setLayout(new BorderLayout());

        JTextArea reportDisplay = new JTextArea(orderHistory.showItemSales());
        reportDisplay.setEditable(false);
        
        JPanel trackPanel = new JPanel();
        
        JButton exitButton = new JButton("Back");

        
        trackPanel.add(exitButton);

        reportFrame.add(new JScrollPane(reportDisplay), BorderLayout.CENTER);
        reportFrame.add(trackPanel, BorderLayout.SOUTH);
        reportFrame.setVisible(true);
        
        reportFrame.setLocationRelativeTo(null);

        
        exitButton.addActionListener(e -> reportFrame.dispose());
	}
}
