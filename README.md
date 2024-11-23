ByteMe Food Ordering application:
1. CLI-Based
2. Help students browse the canteen menu, place orders, and track their delivery without leaving their comfy hostel rooms.
3. Enable canteen staff to manage menu items and process orders efficiently.
4. Maintain order histories (so you can remember what you ate during that 3 AM coding session).
5. Use collections to organise and sort data to make this process efficient!

To start:
1. Run AP_Assignment3\src\AP_Assignment3\Load.java once
2. Run AP_Assignment3\src\AP_Assignment3\Main.java and simply follow the instruction

ByteMe has already been loaded with 9 different food items on the menu, as well as 3 different login roles:
1. Admin: to keep track of food items and customers orders
2. Customer: to interact with the application: view menu, order food, track order, view and give reviews on food item
There are two types of customers: VIP and Normal. Orders from VIP customers will appear first in the System's Order Queue and will be handled first.

Assumption:
1. Assume there will be 3 main user roles: Admin, VIP customer, and Normal customer. They cannot get access to each other’s accounts. So that a login system is not included
2. The order status: Pending, Processing, Delivering, Delivered, and Canceled.
  Pending: Newly generated orders
  Processing: Order being processed
  Delivering: Order out for delivery
  Delivered: Order delivered to customer
  Canceled: Order canceled due to customer canceling, admin canceling, or items being removed from the menu.


***Assignment 4 Update***
1. 100% GUI instead of CLI
2. Added a login system
3. JUnit testing on Ordering out-of-stock items and Invalid Login attempts
4. I/O stream management: Serializable
  Serializable classes: 
  Menu -> Menu.ser
  CustomerHistory -> CustomerHistory.ser
  OrderHistory -> OrderHistory.ser
  
  Special Serialized files:
  CustomerID -> Storing unique ID of each Customer
  OrderID -> Storing unique ID of each Order

