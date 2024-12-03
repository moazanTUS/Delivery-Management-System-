package customer;

import java.sql.ResultSet;
import java.util.Scanner;

public class CustomerCLI {
    
    private CustomerMySQLAccess dao;

    public CustomerCLI() throws Exception {
        this.dao = new CustomerMySQLAccess();
    }

    private static void displayCustomerMenu() {
        System.out.println("\n\t\tCustomer Menu:");
        System.out.println("=============================================");
        System.out.println("Please choose ONE of the following options:");
        System.out.println("1. Create Customer Account");
        System.out.println("2. View ALL Customer Records");
        System.out.println("3. Update Customer Record by ID");
        System.out.println("4. Delete Customer Record by ID");
        System.out.println("99. Return to Main Menu");
        System.out.println("=============================================");
        System.out.println(" ");
    }

    public void run(Scanner scan) {
        String functionNumber;
        boolean keepAppOpen = true;
        
        while (keepAppOpen) {
            displayCustomerMenu();
            System.out.print("Enter Your Selection: ");
            functionNumber = scan.next();

            switch (functionNumber) {
                case "1":
                    createCustomer(scan);
                    break;
                case "2":
                    viewAllCustomers();
                    break;
                case "3":
                    updateCustomer(scan);
                    break;
                case "4":
                    deleteCustomer(scan);
                    break;
                case "99":
                    keepAppOpen = false; // Go back to main menu
                    break;
                default:
                    System.out.println("No Valid Function Selected");
                    break;
            }
        }
    }

    private void createCustomer(Scanner scan) {
        try {
        	scan.nextLine();
        	System.out.println("Enter Customer Full Name:");
            String custName = scan.nextLine(); 
            
            System.out.print("Enter Customer Address: ");
            String address = scan.nextLine();
            
            System.out.print("Enter Customer Phone Number: ");
            String phone = scan.nextLine();
            if(!phone.matches("\\d+")) {
            	System.out.println("Error: Phone number can only contain numbers.");
            	return;
            }
            long custPhone = Long.parseLong(phone);

            String areaId = dao.getAreaIdByAddress(address);
            if(areaId==null) {
            	System.out.println("Creating customer is cancelled.");
            	return;
            }
            String status = "Active"; //default customer status
            
            Customer customer = new Customer(custName, custPhone, address, status, areaId);
            boolean isInserted = dao.insertCustomerDetailsAccount(customer);
            if (isInserted) {
                System.out.println("Customer details added successfully.");
            } else {
                System.out.println("ERROR: Customer details not saved.");
            }
        } catch (Exception e) {
            System.out.println("Error creating customer: " + e.getMessage());
        }
    }

    private void viewAllCustomers() {
        try (ResultSet rs = dao.retrieveAllCustomerAccounts()) {
            if (rs != null && rs.isBeforeFirst()) {
                printCustomerTable(rs);
            } else {
                System.out.println("No customer records found.");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving customer records: " + e.getMessage());
        }
    }

    private void updateCustomer(Scanner scan) {
    	viewAllCustomers();
    	System.out.println("Enter Customer Id to be updated (-99 to cancel):");
	    String updateCustId = scan.next();
	    boolean customerExists = getCustomerById(updateCustId);
	    if (!customerExists) {
	        System.out.println("Customer ID does not exist. Exiting update process.");
	        return;
	    }
	    
	    if (!updateCustId.equals("-99") && customerExists) {
	    	System.out.println("Enter Customer New First Name: ");
			String updateFName = scan.next();
			System.out.println("Enter Customer New Last Name: ");
			String updateLName = scan.next();
	        String newCustName = updateFName + " " + updateLName;
	        scan.nextLine();
	        
	        System.out.println("Enter new Customer Address: ");
	        String newCustAddress = scan.nextLine(); 
	        
	        System.out.println("Enter new Customer Phone:");
	        long newCustPhone = scan.nextLong();
	        
	        System.out.println("Enter new Customer Status: ");
	        String newCustStatus = scan.next();  
	        
	        String newAreaId = dao.getAreaIdByAddress(newCustAddress);
	        
	        Customer updatedCustomer = new Customer();
	        updatedCustomer.setCustId(updateCustId);
	        updatedCustomer.setCustName(newCustName);
	        updatedCustomer.setCustAddress(newCustAddress);
	        updatedCustomer.setCustPhone(newCustPhone);
	        updatedCustomer.setCustStatus(newCustStatus);
	        updatedCustomer.setAreaId(newAreaId);
	        
	        boolean updateResult = dao.updateCustomerDetailsAccount(updatedCustomer);
	        
	        if (updateResult) {
	            System.out.println("Customer Details Updated Successfully.");
	        } else {
	            System.out.println("ERROR: Customer Details are NOT Updated.");
	        }
	    }

    }
    
    public boolean getCustomerById(String id) {
        try (ResultSet rs = dao.getCustomerById(id)) {
            if (rs != null && rs.next()) {
            	rs.close();
                try (ResultSet rsForPrint = dao.getCustomerById(id)) { 
                    printCustomerTable(rsForPrint);
                }
                return true;
            } else {
                System.out.println("No customer with the id = " + id + " found.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving customer records: " + e.getMessage());
            return false;
        }
    }
    
    public boolean getCustById(String id) {
        try (ResultSet rs = dao.getCustomerById(id)) {
            if (rs != null && rs.next()) {
                return true;
            } else {
                System.out.println("No customer with the id = " + id + " found.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving customer records: " + e.getMessage());
            return false;
        }
    }
    
    private void deleteCustomer(Scanner scan) {
        System.out.print("Enter Customer ID to delete (-99 to cancel): ");
        String id = scan.next();
        if ("-99".equals(id)) return;
        boolean customerExists = getCustomerById(id);
	    if (!customerExists) {
	        System.out.println("Customer ID does not exist. Exiting update process.");
	        return;
	    }
        System.out.println("Confirm delete this customer from the system? (Y/N)");
        String confirmDelete = scan.next();
        
        if(confirmDelete.equals("Y") || confirmDelete.equals("y")) {
        	try {
                boolean isDeleted = dao.deleteCustomerById(id);
                if (isDeleted) {
                    System.out.println("Customer deleted successfully.");
                } else {
                    System.out.println("ERROR: Customer not deleted or does not exist.");
                }
            } catch (Exception e) {
                System.out.println("Error deleting customer: " + e.getMessage());
            }
        }else {
        	System.out.println("Deletion cancelled.");
        }
        
    }

    private static void printCustomerTable(ResultSet rs) throws Exception {
        System.out.printf("%-6s%-20s%-30s%-15s%-10s%-10s\n", "ID", "Name", "Address", "Phone", "Status", "Area ID");
        System.out.println("-----------------------------------------------------------------------------------------");

        while (rs.next()) {
            System.out.printf("%-6d%-20s%-30s%-15s%-10s%-10s\n",
                rs.getInt("cust_id"),
                rs.getString("cust_name"),
                rs.getString("cust_address"),
                rs.getString("cust_phone"),
                rs.getString("cust_status"),
                rs.getString("area_id"));
        }
        System.out.println("=========================================================================================\n");
    }
}

