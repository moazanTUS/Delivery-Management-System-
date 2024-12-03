package deliveryArea;

import java.sql.ResultSet;
import java.util.Scanner;

public class DeliveryAreaCLI {

	private DeliveryAreaMySQLAccess dao;

	public DeliveryAreaCLI() throws Exception {
		this.dao = new DeliveryAreaMySQLAccess();
	}

	private static void displayDeliveryAreaMenu() {
		System.out.println("\n\t\tDelivery Area Menu:");
		System.out.println("=============================================");
		System.out.println("Please choose ONE of the following options:");
		System.out.println("1. Create Delivery Area");
		System.out.println("2. View ALL Delivery Areas");
		System.out.println("3. Update Delivery Area by ID");
		System.out.println("4. Delete Delivery Area by ID");
		System.out.println("99. Return to Main Menu");
		System.out.println("=============================================");
		System.out.println(" ");
	}

	public void run(Scanner scan) {
		String functionNumber;
		boolean keepAppOpen = true;

		while (keepAppOpen) {
			displayDeliveryAreaMenu();
			System.out.print("Enter Your Selection: ");
			functionNumber = scan.next();

			switch (functionNumber) {
			case "1":
				createDeliveryArea(scan);
				break;
			case "2":
				viewAllDeliveryAreas();
				break;
			case "3":
				updateDeliveryArea(scan);
				break;
			case "4":
				deleteDeliveryArea(scan);
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

	private void createDeliveryArea(Scanner scan) {
		try {
			scan.nextLine();
			System.out.print("Enter Delivery Area Address: ");
			String address = scan.nextLine().trim();

			System.out.print("Enter Number of Customers: ");
			String numCustomersInput = scan.nextLine().trim();
			// error handling for numCustomers as the constructor takes only int
			int numCustomers;
			try {
				numCustomers = Integer.parseInt(numCustomersInput);
			} catch (NumberFormatException e) {
				System.out.println("Number of customers must be a valid numeric value.");
				return;
			}

			DeliveryArea deliveryArea = new DeliveryArea(address, numCustomers);
			boolean isInserted = dao.insertDeliveryArea(deliveryArea);

			if (isInserted) {
				System.out.println("Delivery Area added successfully.");
			} else {
				System.out.println("Delivery Area not saved.");
			}
		} catch (Exception e) {
			System.out.println("Unexpected error creating delivery area: " + e.getMessage());
		}
	}

	private void viewAllDeliveryAreas() {
		try (ResultSet rs = dao.retrieveAllDeliveryArea()) {
			if (rs != null && rs.isBeforeFirst()) {
				printDeliveryAreaTable(rs);
			} else {
				System.out.println("No delivery area records found.");
			}
		} catch (Exception e) {
			System.out.println("Error retrieving delivery area records: " + e.getMessage());
		}
	}

	private void updateDeliveryArea(Scanner scan) {
		System.out.println("Enter Area Id to be updated(-99 to cancel):");
		String updateAreaId = scan.next();
		// Check if id exist
		boolean areaExists = getDeliveryAreaById(updateAreaId);
		if (!isValidAreaUpdate(updateAreaId, areaExists)) {
			return;
		}
		scan.nextLine();
		System.out.println("Enter New Delivery Area Address: ");
		String updateDelAddress = scan.nextLine().trim();

		System.out.println("Enter New number of customers: ");
		String numCustomersInput = scan.nextLine().trim();
		int updateNumCust;
		try {
			updateNumCust = Integer.parseInt(numCustomersInput);
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid numeric value for the number of customers.");
			return;
		}

		try {
			DeliveryArea updatedDeliveryArea = new DeliveryArea();
			updatedDeliveryArea.setAreaId(updateAreaId);
			updatedDeliveryArea.setAreaAddress(updateDelAddress);
			updatedDeliveryArea.setNumOfCustomer(updateNumCust);

			boolean updateResult = dao.updateDeliveryArea(updatedDeliveryArea);
			if (updateResult) {
				System.out.println("Delivery Area Updated Successfully.");
			} else {
				System.out.println("ERROR: Delivery Area NOT updated or does not exist.");
			}
		} catch (DeliveryAreaExceptionHandler e) {
			System.out.println("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("An unexpected error occurred while updating the delivery area.");
			System.out.println("Error Details: " + e.getMessage());
		}

	}

	public boolean getDeliveryAreaById(String id) {
		try (ResultSet rs = dao.getDeliveryAreaById(id)) {
			if (rs != null && rs.next()) {
				rs.close();
				try (ResultSet rsForPrint = dao.getDeliveryAreaById(id)) {
					printDeliveryAreaTable(rsForPrint);
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Error retrieving Delivery Area records: " + e.getMessage());
			return false;
		}
	}

	private boolean isValidAreaUpdate(String updateAreaId, boolean areaExists) {
		if (updateAreaId.equals("-99")) {
			System.out.println("Update canceled.");
			return false;
		}
		if (!updateAreaId.matches("\\d+")) {
			System.out.println("Delivery Area ID must be a numeric value.");
			return false;
		}
		if (!areaExists) {
			System.out.println("Delivery Area ID does not exist.");
			return false;
		}
		return true;
	}

	private void deleteDeliveryArea(Scanner scan) {
		System.out.print("Enter Delivery Area ID to delete (-99 to cancel): ");
		String id = scan.next();
		boolean areaExists = getDeliveryAreaById(id);
		if (!isValidAreaUpdate(id, areaExists)) {
			return;
		}
		System.out.println("Confirm delete this delivery area from the system? (Y/N)");
		String confirmDelete = scan.next();

		if (confirmDelete.equalsIgnoreCase("Y")) {
			try {
				boolean isDeleted = dao.deleteDeliveryAreaById(id);
				if (isDeleted) {
					System.out.println("Delivery area deleted successfully.");
				} else {
					System.out.println("ERROR: Delivery area not deleted or does not exist.");
				}
			} catch (Exception e) {
				System.out.println("Error deleting Delivery Area: " + e.getMessage());
			}
		} else {
			System.out.println("Deletion not confirmed.");
		}
	}

	private static void printDeliveryAreaTable(ResultSet rs) throws Exception {
		System.out.printf("%-10s%-20s%-30s\n", "ID", "Area Address", "Number of Customers");
		System.out.println("---------------------------------------------------------------------------------");

		while (rs.next()) {
			System.out.printf("%-10d%-20s%-30d\n", rs.getInt("area_id"), rs.getString("area_address"),
					rs.getInt("num_of_cust"));
		}
		System.out.println("=================================================================================\n");
	}
}
