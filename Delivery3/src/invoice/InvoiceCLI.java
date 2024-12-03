package invoice;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Scanner;

import customer.Customer;
import customer.CustomerMySQLAccess;
import deliveryArea.DeliveryArea;
import deliveryArea.DeliveryAreaMySQLAccess;
import order.Order;
import order.OrderMySQLAccess;

public class InvoiceCLI {

	private InvoiceMySQLAccess daoI;

	public InvoiceCLI() throws Exception {
		this.daoI = new InvoiceMySQLAccess();
	}

	private static void displayInvoiceMenu() {
		System.out.println("\n\t\tInvoice Menu:");
		System.out.println("=============================================");
		System.out.println("Please choose ONE of the following options:");
		System.out.println("1. View ALL Invoices");
		System.out.println("2. View Invoice By Customer ID");
		System.out.println("3. Update Invoice by ID");
		System.out.println("4. Delete Invoice by ID");
		System.out.println("99. Return to Main Menu");
		System.out.println("=============================================");
		System.out.println(" ");
	}

	public void run(Scanner scan) {
		String functionNumber;
		boolean keepAppOpen = true;

		while (keepAppOpen) {
			displayInvoiceMenu();
			System.out.print("Enter Your Selection: ");
			functionNumber = scan.next();

			switch (functionNumber) {
			case "1":
				getAllInvoice();
				break;
			case "2":
				getInvoiceById(scan);
				break;
			case "3":
				updateInvoice(scan);
				break;
			case "4":
				deleteInvoice(scan);
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

	private static void printInvoiceTable(ResultSet rs) throws Exception {
		System.out.printf("%-15s%-20s%-20s%-25s%-15s%-15s%-15s%-15s\n", "Invoice ID", "Customer Name", "Cust Address",
				"Publication Name", "Quantity", "Invoice Date", "Total Payment", "Status");
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------");
		while (rs.next()) {
			System.out.printf("%-15s%-20s%-20s%-25s%-15s%-15s%-15s%-15s\n", rs.getString("invoice_id"),
					rs.getString("cust_name"), rs.getString("cust_address"), rs.getString("pub_name"),
					rs.getInt("order_qty"), rs.getDate("invoice_date").toString(), rs.getDouble("total_payment"),
					rs.getString("invoice_status"));
		}
		System.out.println(
				"=======================================================================================================================\n");
	}

	private void getAllInvoice() {
		try (ResultSet rs = daoI.getAllInvoices()) {
			if (rs != null && rs.isBeforeFirst()) {
				printInvoiceTable(rs);
			} else {
				System.out.println("No invoice records found.");
			}
		} catch (Exception e) {
			System.out.println("Error retrieving invoice records: " + e.getMessage());
		}
	}

	public void getInvoiceById(Scanner scan) {
		getAllInvoice();
		System.out.print("Enter Customer ID to view (-99 to cancel): ");
		String id = scan.next();
		if ("99".equals(id))
			return;
		if (!id.matches("\\d+")) {  
	        System.out.println("Invalid input. Please enter a numeric Invoice ID.");
	        return;
	    }
		try {
			ResultSet rs = daoI.getInvoiceById(id);
			if (rs != null && rs.isBeforeFirst()) {
				printInvoiceTable(rs);
			} else {
				System.out.println("No invoice records found for Invoice ID: " + id);
			}
		} catch (Exception e) {
			System.out.println("Error retrieving invoice records: " + e.getMessage());
		}
	}

	public void updateInvoice(Scanner scan) {
		getAllInvoice();
		System.out.print("Enter Invoice ID to update (-99 to cancel): ");
		String id = scan.next();
		if ("99".equals(id))
			return;
		try {
			System.out.println("Enter Delivery Status (-99 to cancel):");
			System.out.println("1. PAID");
			System.out.println("2. ONGOING");
			System.out.println("3. UNPAID");
			int choice = scan.nextInt();
			String status = null;
			if (choice == 99) {
				System.out.println("Update cancelled.");
				return;
			}
			switch (choice) {
			case 1:
				status = "PAID";
				break;
			case 2:
				status = "ONGOING";
				break;
			case 3:
				status = "UNPAID";
				break;
			default:
				System.out.println("Invalid choice. Update cancelled.");
				return;
			}
			boolean result = daoI.updateInvoiceByID(id, status);
			if (result) {
				System.out.println("Invoice status updated successfully.");
			} else {
				System.out.println("Failed to update invoice status.");
			}
		} catch (Exception e) {
			System.out.println("Error deleting invoice: " + e.getMessage());
		}

	}

	private void deleteInvoice(Scanner scan) {
		getAllInvoice();
		System.out.print("Enter Invoice ID to delete (-99 to cancel): ");
		String id = scan.next();
		if ("-99".equals(id))
			return;

		try {
			boolean isDeleted = daoI.deleteInvoiceById(id);
			if (isDeleted) {
				System.out.println("Invoice deleted successfully.");
			} else {
				System.out.println("ERROR: Invoice not deleted or does not exist.");
			}
		} catch (Exception e) {
			System.out.println("Error deleting invoice: " + e.getMessage());
		}
	}

}
