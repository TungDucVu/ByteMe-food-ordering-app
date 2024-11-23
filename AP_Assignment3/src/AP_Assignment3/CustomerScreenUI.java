	package AP_Assignment3;

import javax.swing.*;
import java.awt.*;

public class CustomerScreenUI extends JFrame {
    private static final String FILE_NAME_MENU = "Menu.ser";
    private static final String FILE_NAME_ORDERHISTORY = "OrderHistory.ser";
    private static final String FILE_NAME_CUSTOMERHISTORY = "CustomerHistory.ser";
    
    private CustomerHistory customers = CustomerHistory.loadFromFile(FILE_NAME_CUSTOMERHISTORY);
    private Menu menu = Menu.loadFromFile(FILE_NAME_MENU);
    private OrderHistory orderHistory = OrderHistory.loadFromFile(FILE_NAME_ORDERHISTORY);

    public CustomerScreenUI(Customer customer) {
        setTitle("Customer Screen");
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Welcome Message Panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridLayout(3, 1));
        JLabel welcomeLabel = new JLabel("Welcome, " + customer.getName() + "!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        welcomePanel.add(welcomeLabel);

        // Button Panel for Actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3, 10, 10));
        
        JButton goVip = new JButton(customer.isVIP() ? "You are a VIP member!" : "Not a VIP yet?");
        JButton browseMenuButton = new JButton("Browse Menu");
        JButton cartButton = new JButton("Cart Operations");
        JButton orderTrackingButton = new JButton("Order Tracking");
        JButton itemReviewsButton = new JButton("Item Reviews");
        JButton exitButton = new JButton("Exit");
        
        buttonPanel.add(goVip);
        buttonPanel.add(browseMenuButton);
        buttonPanel.add(cartButton);
        buttonPanel.add(orderTrackingButton);
        buttonPanel.add(itemReviewsButton);
        buttonPanel.add(exitButton);

        // Add Panels to Frame
        add(welcomePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Button Listeners
        if (!customer.isVIP()) {
            goVip.addActionListener(e -> goVip(customer));
        }
        browseMenuButton.addActionListener(e -> openBrowseMenu(customer));
        cartButton.addActionListener(e -> openCartOperations(customer));
        orderTrackingButton.addActionListener(e -> openOrderTracking(customer));
        itemReviewsButton.addActionListener(e -> openItemReviews(customer));
        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
    
    private void goVip(Customer customer) {
    	JFrame vipFrame = new JFrame("Go VIP?");
        vipFrame.setSize(960, 540);
        vipFrame.setLayout(new BorderLayout());
        
        vipFrame.setLocationRelativeTo(null);

        JTextArea vipDisplay = new JTextArea(menu.showMenu());
        vipDisplay.setEditable(false);
        
        int confirm = JOptionPane.showConfirmDialog(this, "Purchase $69 to become a VIP member?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
        	
            JOptionPane.showMessageDialog(this, "Thank you for your purchase!");
            customers.findCustomerByID(customer.getID()).setVIP();
            customers.saveToFile(FILE_NAME_CUSTOMERHISTORY);
            
            vipFrame.dispose();
            new CustomerScreenUI(customer);
        }
    }

    private void openBrowseMenu(Customer customer) {
        JFrame menuFrame = new JFrame("Browse Menu");
        menuFrame.setSize(960, 540);
        menuFrame.setLayout(new BorderLayout());
        JTextArea menuDisplay = new JTextArea(menu.showMenu());
        menuDisplay.setEditable(false);

        JPanel filterPanel = new JPanel();
        JButton searchButton = new JButton("Search");
        JButton addButton = new JButton("Add items");
        JButton filterCategoryButton = new JButton("Filter by Category");
        JButton sortPriceButton = new JButton("Sort by Price");
        JButton cartButton = new JButton("To Cart");
        JButton exitButton = new JButton("Back");

        filterPanel.add(searchButton);
        filterPanel.add(addButton);
        filterPanel.add(filterCategoryButton);
        filterPanel.add(sortPriceButton);
        filterPanel.add(cartButton);
        filterPanel.add(exitButton);

        menuFrame.add(new JScrollPane(menuDisplay), BorderLayout.CENTER);
        menuFrame.add(filterPanel, BorderLayout.SOUTH);
        menuFrame.setVisible(true);
        
        // center
        menuFrame.setLocationRelativeTo(null);

        // Add listeners for each button action
        addButton.addActionListener(e -> addItemToCart(menuDisplay, menuFrame, customer));
        searchButton.addActionListener(e -> searchFoodItem(menuDisplay, menuFrame, customer));
        filterCategoryButton.addActionListener(e -> menuDisplay.setText(menu.showCategory()));
        sortPriceButton.addActionListener(e -> menuDisplay.setText(menu.showPriceUp()));
        cartButton.addActionListener(e -> openCartOperations(customer));
        exitButton.addActionListener(e -> menuFrame.dispose());
    }

    private void searchFoodItem(JTextArea display, JFrame menuFrame, Customer customer) {
        String itemName = JOptionPane.showInputDialog(menuFrame, "Enter the name of the item to search:");
        if (itemName != null && !itemName.isEmpty()) {
            try {
                FoodItem item = menu.getFoodItem(itemName);
                display.setText(item.toString());
            } catch (FoodItemNotFoundException e) {
                JOptionPane.showMessageDialog(menuFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openCartOperations(Customer customer) {
        JFrame cartFrame = new JFrame("Cart Operations");
        cartFrame.setSize(960, 540);
        cartFrame.setLayout(new BorderLayout());

        JTextArea cartDisplay = new JTextArea(customer.getCart().toString());
        cartDisplay.setEditable(false);

        JPanel cartButtons = new JPanel();
        JButton modifyQuantityButton = new JButton("Modify Quantity");
        JButton removeItemButton = new JButton("Remove Item");
        JButton viewTotalButton = new JButton("View Total");
        JButton checkoutButton = new JButton("Checkout");
        JButton exitButton = new JButton("Back");

        cartButtons.add(modifyQuantityButton);
        cartButtons.add(removeItemButton);
        cartButtons.add(viewTotalButton);
        cartButtons.add(checkoutButton);
        cartButtons.add(exitButton);

        cartFrame.add(new JScrollPane(cartDisplay), BorderLayout.CENTER);
        cartFrame.add(cartButtons, BorderLayout.SOUTH);
        cartFrame.setVisible(true);
        
        cartFrame.setLocationRelativeTo(null);

        // Button actions for cart operations
        
        modifyQuantityButton.addActionListener(e -> modifyCartItemQuantity(cartDisplay, cartFrame, customer));
        removeItemButton.addActionListener(e -> removeItemFromCart(cartDisplay, cartFrame, customer));
        viewTotalButton.addActionListener(e -> showTotal(cartDisplay, cartFrame, customer));
        checkoutButton.addActionListener(e -> checkout(cartDisplay, cartFrame, customer));
        exitButton.addActionListener(e -> cartFrame.dispose());
    }

    private void addItemToCart(JTextArea display, JFrame menuFrame, Customer customer) {
        String itemName = JOptionPane.showInputDialog(menuFrame, "Enter item name to add:");
        String quantityStr = JOptionPane.showInputDialog(menuFrame, "Enter quantity:");

        try {
            int quantity = Integer.parseInt(quantityStr);
            FoodItem item = menu.getFoodItem(itemName);
            if (!item.isAvailable() ) {
            	JOptionPane.showMessageDialog(this, "Item is not available");
            }
            customer.addToCart(item, quantity);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(menuFrame, "Invalid input or item not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyCartItemQuantity(JTextArea display, JFrame cartFrame, Customer customer) {
        String itemName = JOptionPane.showInputDialog(cartFrame, "Enter item name to modify:");
        String quantityStr = JOptionPane.showInputDialog(cartFrame, "Enter new quantity:");

        try {
            int quantity = Integer.parseInt(quantityStr);
            CartItem item = customer.getCartItem(itemName);
            item.setQuantity(quantity);
            display.setText(customer.getCart().toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cartFrame, "Invalid input or item not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeItemFromCart(JTextArea display, JFrame cartFrame, Customer customer) {
        String itemName = JOptionPane.showInputDialog(cartFrame, "Enter item name to remove:");
        try {
            CartItem item = customer.getCartItem(itemName);
            customer.removeFromCart(item);
            display.setText(customer.getCart().toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cartFrame, "Item not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showTotal(JTextArea display, JFrame cartFrame, Customer customer) {
        double total = customer.getTotal();
        JOptionPane.showMessageDialog(cartFrame, "Your total is: $" + total);
    }

    private void checkout(JTextArea display, JFrame cartFrame, Customer customer) {
    	showTotal(display, cartFrame, customer);
        int confirm = JOptionPane.showConfirmDialog(cartFrame, "Are you sure you want to checkout?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String specialRequest = JOptionPane.showInputDialog(cartFrame, "Do you have any special requests?", "Special Request", JOptionPane.PLAIN_MESSAGE);

            // If user provides a request, store it; otherwise, set it as an empty string
            if (specialRequest == null) {
                specialRequest = "";  // user pressed cancel
            }

            Order order = new Order(customer, customer.getCart(), customer.getQuantity(), customer.getTotal(), specialRequest);
            orderHistory.addOrder(order);
            
            order.saveToFile();
            
            customers.findCustomerByID(customer.getID()).getCart().clearCart();
            display.setText(customer.getCart().toString());
            JOptionPane.showMessageDialog(this, "Thank you for your order!");
            
            orderHistory.saveToFile(FILE_NAME_ORDERHISTORY);
            customers.saveToFile(FILE_NAME_CUSTOMERHISTORY);
        }
    }


    private void openOrderTracking(Customer customer) {
        JFrame orderFrame = new JFrame("Order Tracking");
        orderFrame.setSize(960, 540);
        orderFrame.setLayout(new BorderLayout());

        JTextArea orderDisplay = new JTextArea(orderHistory.getCustomerOrders(customers.findCustomerByID(customer.getID()), "PENDING"));
        orderDisplay.setEditable(false);
        
        JPanel trackPanel = new JPanel();
        JButton cancelOrderButton = new JButton("Cancel Order");
        JButton viewHistoryButton = new JButton("View Order History");
        JButton exitButton = new JButton("Back");

        trackPanel.add(cancelOrderButton);
        trackPanel.add(viewHistoryButton);
        trackPanel.add(exitButton);

        orderFrame.add(new JScrollPane(orderDisplay), BorderLayout.CENTER);
        orderFrame.add(trackPanel, BorderLayout.SOUTH);
        orderFrame.setVisible(true);
        
        orderFrame.setLocationRelativeTo(null);

        cancelOrderButton.addActionListener(e -> cancelOrder(orderDisplay, orderFrame, customer));
        viewHistoryButton.addActionListener(e -> orderDisplay.setText(orderHistory.getCustomerOrders(customer)));
        exitButton.addActionListener(e -> orderFrame.dispose());
    }
    
    private void cancelOrder(JTextArea display, JFrame orderFrame, Customer customer) {
        String id = JOptionPane.showInputDialog(orderFrame, "Enter the id of the order to search:");
        if (id != null && !id.isEmpty()) {
            try {
                orderHistory.userCanceled(id);
                display.setText(orderHistory.findOrderById(id).toString());
            } catch (OrderNotFoundException e) {
            	JOptionPane.showMessageDialog(orderFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
        }
    }

    private void openItemReviews(Customer customer) {
        JFrame reviewFrame = new JFrame("Item Reviews");
        reviewFrame.setSize(960, 540);
        reviewFrame.setLayout(new BorderLayout());

        JTextArea reviewDisplay = new JTextArea(menu.showReview());
        reviewDisplay.setEditable(false);

        JPanel reviewPanel = new JPanel();
        JButton submitReviewButton = new JButton("Submit Review");
        JButton exitButton = new JButton("Back");

        reviewPanel.add(submitReviewButton);
        reviewPanel.add(exitButton);

        reviewFrame.add(new JScrollPane(reviewDisplay), BorderLayout.CENTER);
        reviewFrame.add(reviewPanel, BorderLayout.SOUTH);
        reviewFrame.setVisible(true);
        
        reviewFrame.setLocationRelativeTo(null);

        submitReviewButton.addActionListener(e -> submitReview(reviewDisplay, reviewFrame));
        exitButton.addActionListener(e -> reviewFrame.dispose());
        
    }

    private void submitReview(JTextArea display, JFrame reviewFrame) {
        String itemName = JOptionPane.showInputDialog(reviewFrame, "Enter item name to review:");
        String review = JOptionPane.showInputDialog(reviewFrame, "Enter your review:");

        if (itemName != null && review != null) {
            try {
            	FoodItem toReview = menu.getFoodItem(itemName);
                menu.addReview(toReview, review);
                JOptionPane.showMessageDialog(reviewFrame, "Thank you for your review!");
                menu.saveToFile(FILE_NAME_MENU);
                display.setText(menu.showReview());
            } catch (FoodItemNotFoundException e) {
                JOptionPane.showMessageDialog(reviewFrame, "Error submitting review.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    
}
