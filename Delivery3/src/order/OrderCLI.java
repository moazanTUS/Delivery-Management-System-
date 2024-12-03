package order;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import customer.CustomerCLI;
import publication.PublicationCLI;

public class OrderCLI {

    private OrderMySQLAccess dao;
    private CustomerCLI custCLI;
    private PublicationCLI pubCLI;

    public OrderCLI() throws Exception {
        this.dao = new OrderMySQLAccess();
        this.custCLI = new CustomerCLI();
        this.pubCLI = new PublicationCLI(); 
    }

    private static void displayOrderMenu() {
        System.out.println("\n\t\tOrder Menu:");
        System.out.println("=============================================");
        System.out.println("Please choose ONE of the following options:");
        System.out.println("1. Create Order");
        System.out.println("2. View ALL Orders");
        System.out.println("3. Update Order by ID");
        System.out.println("4. Delete Order by ID");
        System.out.println("99. Return to Main Menu");
        System.out.println("=============================================");
        System.out.println(" ");
    }

    public void run(Scanner scan) {
        String functionNumber;
        boolean keepAppOpen = true;

        while (keepAppOpen) {
            displayOrderMenu();
            System.out.print("Enter Your Selection: ");
            functionNumber = scan.next();

            switch (functionNumber) {
                case "1":
                    createOrder(scan);
                    break;
                case "2":
                    viewAllOrders();
                    break;
                case "3":
                    updateOrder(scan);
                    break;
                case "4":
                    deleteOrder(scan);
                    break;
                case "99":
                    keepAppOpen = false;
                    break;
                default:
                    System.out.println("No Valid Function Selected");
                    break;
            }
        }
    }

    private void createOrder(Scanner scan) {
        try {
        	PublicationCLI pubTable = new PublicationCLI();
        	pubTable.getAllPublications();
            scan.nextLine(); 
            
            System.out.print("Enter Order Date (yyyy-MM-dd): ");
            String dateInput = scan.nextLine();
            Date orderDate = parseDate(dateInput);

            System.out.print("Enter Customer ID: ");
            String custId = scan.nextLine();
            boolean custIdExists = custCLI.getCustById(custId);
            if(!custIdExists) {
            	System.out.println("Error: Customer ID does not exist. Cancelling create order.");
            	return;
            }
            System.out.print("Enter Order Frequency: ");
            String orderFreq = scan.nextLine();
            
            System.out.print("Enter Order Quantity: ");
            String qtyInput = scan.nextLine();
            int orderQty = Integer.parseInt(qtyInput);

            System.out.print("Enter Publication ID: ");
            String pubId = scan.nextLine();
            boolean pubExists = pubCLI.getPubById(pubId);
            if(!pubExists) {
            	System.out.println("Error: Publication ID does not exist. Cancelling create order.");
            	return;
            }

            Order order = new Order(orderDate, custId, orderFreq, orderQty, pubId);
            boolean isInserted = dao.insertOrder(order);
            if (isInserted) {
                System.out.println("Order added successfully.");
            } else {
                System.out.println("ERROR: Order not saved.");
            }
        } catch (Exception e) {
            System.out.println("Error creating order: " + e.getMessage());
        }
    }

    private void viewAllOrders() {
        try (ResultSet rs = dao.retrieveAllOrders()) {
            if (rs != null && rs.isBeforeFirst()) {
                printOrderTable(rs);
            } else {
                System.out.println("No order records found.");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving order records: " + e.getMessage());
        }
    }

    private void updateOrder(Scanner scan) {
    	viewAllOrders();
        System.out.print("Enter Order ID to be updated (-99 to cancel): ");
        String updateOrderId = scan.next();
        
        boolean orderExists = getOrderById(updateOrderId);
        if(!orderExists) {
        	System.out.println("Order ID does not exist. Exiting update process..");
        	return;
        }
        if (!updateOrderId.equals("-99") && orderExists) {
            scan.nextLine(); // Consume any leftover newline
            System.out.print("Enter New Order Date (yyyy-MM-dd): ");
            String dateInput = scan.nextLine();
            Date updateOrderDate;
            try {
                updateOrderDate = parseDate(dateInput);
            } catch (ParseException e) {
                System.out.println("Invalid date format.");
                return;
            }

            System.out.print("Enter New Customer ID: ");
            String updateCustId = scan.nextLine();
            boolean custIdExists = custCLI.getCustById(updateCustId);
            if(!custIdExists) {
            	System.out.println("Error: Customer ID does not exist. Cancelling update order.");
            	return;
            }
            
            System.out.print("Enter New Order Frequency: ");
            String updateOrderFreq = scan.nextLine();
            
            System.out.print("Enter Order Quantity: ");
            String updateQtyInput = scan.nextLine();
            int updateOrderQty = Integer.parseInt(updateQtyInput);

            System.out.print("Enter New Publication ID: ");
            String updatePubId = scan.nextLine();
            boolean pubExists = pubCLI.getPubById(updatePubId);
            if(!pubExists) {
            	System.out.println("Error: Publication ID does not exist. Cancelling update order.");
            	return;
            }
            
            Order updatedOrder = new Order();
            updatedOrder.setOrderId(updateOrderId);
            updatedOrder.setOrderDate(updateOrderDate);
            updatedOrder.setCustomerId(updateCustId);
            updatedOrder.setOrderFreq(updateOrderFreq);
            updatedOrder.setOrderQty(updateOrderQty);
            updatedOrder.setPubId(updatePubId);

            boolean updateResult = dao.updateOrder(updatedOrder);

            if (updateResult) {
                System.out.println("Order Updated Successfully.");
            } else {
                System.out.println("ERROR: Order NOT Updated or Does Not Exist.");
            }
        }
    }

    public boolean getOrderById(String id) {
        try (ResultSet rs = dao.getOrderById(id)) {
            if (rs != null && rs.next()) {
            	rs.close();
                try (ResultSet rsForPrint = dao.getOrderById(id)) { 
                    printOrderTable(rsForPrint);
                }
                return true;
            } else {
                System.out.println("No order with the id = " + id + " found.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving order records: " + e.getMessage());
            return false;
        }
    }
    
    private void deleteOrder(Scanner scan) {
        System.out.print("Enter Order ID to delete (-99 to cancel): ");
        String id = scan.next();
        if ("-99".equals(id)) return;
        
        boolean orderExists = getOrderById(id);
        if(!orderExists) {
        	System.out.println("Order ID does not exist. Exiting update process..");
        	return;
        }
        
        try {
            boolean isDeleted = dao.deleteOrderById(id);
            if (isDeleted) {
                System.out.println("Order deleted successfully.");
            } else {
                System.out.println("ERROR: Order not deleted or does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting order: " + e.getMessage());
        }
    }

    private static void printOrderTable(ResultSet rs) throws Exception {
        System.out.printf("%-10s%-20s%-20s%-20s%-15s%-15s\n", "Order ID", "Order Date", "Cust ID", "Order Frequency", "Quantity", "Publication ID");
        System.out.println("-------------------------------------------------------------------------------------------");

        while (rs.next()) {
            System.out.printf("%-10s%-20s%-20s%-20s%-15s%-15s\n",
                rs.getString("order_id"),
                rs.getDate("order_date").toString(),
                rs.getString("cust_id"),
                rs.getString("order_freq"),
                rs.getInt("order_qty"),
                rs.getString("pub_id"));
        }
        System.out.println("===========================================================================================\n");
    }

    private Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        return dateFormat.parse(dateStr);
    }
}
