package deliveryPerson;

import java.sql.ResultSet;
import java.util.Scanner;

import deliveryArea.DeliveryArea;
import deliveryArea.DeliveryAreaExceptionHandler;
import deliveryArea.DeliveryAreaMySQLAccess;

public class DeliveryPersonCLI {

	private DeliveryPersonMySQLAccess dao;
	private DeliveryAreaMySQLAccess daoDA;

	public DeliveryPersonCLI() throws Exception {
		this.dao = new DeliveryPersonMySQLAccess();
		this.daoDA = new DeliveryAreaMySQLAccess();
	}

	private static void displayDeliveryPersonMenu() {
		System.out.println("\n\t\tDelivery Person Menu:");
		System.out.println("=============================================");
		System.out.println("Please choose ONE of the following options:");
		System.out.println("1. Create Delivery Person");
		System.out.println("2. View ALL Delivery Person");
		System.out.println("3. Update Delivery Person by ID");
		System.out.println("4. Delete Delivery Person by ID");
		System.out.println("99. Return to Main Menu");
		System.out.println("=============================================");
		System.out.println(" ");
	}

	public void run(Scanner scan) {
		String functionNumber;
		boolean keepAppOpen = true;

		while (keepAppOpen) {
			displayDeliveryPersonMenu();
			System.out.print("Enter Your Selection: ");
			functionNumber = scan.next();

			switch (functionNumber) {
			case "1":
				createDeliveryPerson(scan);
				break;
			case "2":
				viewAllDeliveryPersons();
				break;
			case "3":
				updateDeliveryPerson(scan);
				break;
			case "4":
				deleteDeliveryPerson(scan);
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

	private void createDeliveryPerson(Scanner scan) {
		try {
			scan.nextLine(); 
			System.out.print("Enter Delivery Person Name: ");
			String name = scan.nextLine().trim();

			System.out.print("Enter Delivery Person Phone Number: ");
			String phoneInput = scan.nextLine().trim();
			long phone;
			if (!phoneInput.matches("[1-9]\\d{8}")) {
				System.out.println("Error: Phone number must be a 9-digit numeric value and cannot start with zero.");
				return;
			}
			phone = Long.parseLong(phoneInput);

			System.out.print("Enter Area ID: ");
			String areaId = scan.next().trim();
			boolean dareaExists = getDeliveryAreaById(areaId);
			if (!isValidAreaUpdate(areaId, dareaExists)) {
				return;
			}
			scan.nextLine();

			DeliveryPerson deliveryPerson = new DeliveryPerson(name, phone, areaId);
			boolean isInserted = dao.insertDeliveryPerson(deliveryPerson);

			if (isInserted) {
				System.out.println("Delivery Person added successfully.");
			} else {
				System.out.println("ERROR: Delivery Person not saved. Please check the details.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Error: Invalid phone number format. Please enter a numeric value.");
		} catch (Exception e) {
			System.out.println("Unexpected error creating delivery person: " + e.getMessage());
		}
	}

	private void viewAllDeliveryPersons() {
		try (ResultSet rs = dao.retrieveAllDeliveryPerson()) {
			if (rs != null && rs.isBeforeFirst()) {
				printDeliveryPersonTable(rs);
			} else {
				System.out.println("No delivery person records found.");
			}
		} catch (Exception e) {
			System.out.println("Error retrieving delivery person records: " + e.getMessage());
		}
	}

	private void updateDeliveryPerson(Scanner scan) {
		System.out.println("Enter Delivery Person Id to be updated(-99 to cancel):");
		String updateDPersonId = scan.next().trim();
		boolean dpersonExists = getDeliveryPersonById(updateDPersonId);
		if (!isValidPersonId(updateDPersonId, dpersonExists)) {
			return;
		}
		scan.nextLine();

		System.out.print("Enter New Delivery Person Name: ");
		String updateName = scan.nextLine().trim();

		System.out.print("Enter New Delivery Person Phone Number: ");
		
		String phoneInput = scan.nextLine().trim();
	    if (!phoneInput.matches("[1-9]\\d{8}")) {
	        System.out.println("Error: Phone number must be exactly 9 digits and cannot start with zero.");
	        return;
	    }

	    long updatePhone;
	    try {
	        updatePhone = Long.parseLong(phoneInput);  
	    } catch (NumberFormatException e) {
	        System.out.println("Error: Please enter a valid 9-digit numeric phone number.");
	        return;
	    }

		System.out.print("Enter New Area ID: ");
		String updateAreaId = scan.next().trim();
		boolean dareaExists = getDeliveryAreaById(updateAreaId);
		if (!isValidAreaUpdate(updateAreaId, dareaExists)) {
			return;
		}
		scan.nextLine();

		try {
			DeliveryPerson updatedDeliveryPerson = new DeliveryPerson();
			updatedDeliveryPerson.setDriverId(updateDPersonId);
			updatedDeliveryPerson.setDriverName(updateName);
			updatedDeliveryPerson.setDriverPhone(updatePhone);
			updatedDeliveryPerson.setAreaId(updateAreaId);

			boolean updateResult = dao.updateDeliveryPerson(updatedDeliveryPerson);
			if (updateResult) {
				System.out.println("Delivery Person updated successfully.");
			} else {
				System.out.println("ERROR: Delivery Person not updated or does not exist.");
			}
		} catch (DeliveryPersonExceptionHandler e) {
			System.out.println("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("An unexpected error occurred while updating the delivery person.");
			System.out.println("Error Details: " + e.getMessage());
		}
	}

	public boolean getDeliveryPersonById(String id) {
		try (ResultSet rs = dao.getDeliveryPersonById(id)) {
			if (rs != null && rs.next()) {
				rs.close();
				try (ResultSet rsForPrint = dao.getDeliveryPersonById(id)) {
					printDeliveryPersonTable(rsForPrint);
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Error retrieving Delivery Person records: " + e.getMessage());
			return false;
		}
	}
	
	public boolean getDeliveryAreaById(String id) {
		try (ResultSet rs = daoDA.getDeliveryAreaById(id)) {
			if (rs != null && rs.next()) {
				rs.close();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Error retrieving Delivery Area records: " + e.getMessage());
			return false;
		}
	}

	private boolean isValidPersonId(String updateDPersonId, boolean dpersonExists) {
		if (updateDPersonId.equals("-99")) {
			System.out.println("Update canceled.");
			return false;
		}
		if (!updateDPersonId.matches("\\d+")) {
			System.out.println("Delivery Person ID must be a numeric value.");
			return false;
		}
		if (!dpersonExists) {
			System.out.println("Delivery Person ID does not exist.");
			return false;
		}
		return true;
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

	private void deleteDeliveryPerson(Scanner scan) {
		System.out.print("Enter Delivery Person ID to delete (-99 to cancel): ");
		String id = scan.next();
		if ("-99".equals(id))
			return;
		boolean dpersonExists = getDeliveryPersonById(id);
		if (!isValidPersonId(id, dpersonExists)) {
			return;
		}
		System.out.println("Confirm delete this delivery person from the system? (Y/N)");
		String confirmDelete = scan.next();

		if (confirmDelete.equals("Y") || confirmDelete.equals("y")) {
			try {
				boolean isDeleted = dao.deleteDeliveryPersonById(id);
				if (isDeleted) {
					System.out.println("Delivery person deleted successfully.");
				} else {
					System.out.println("ERROR: Delivery person not deleted or does not exist.");
				}
			} catch (Exception e) {
				System.out.println("Error deleting Delivery person: " + e.getMessage());
			}
		}
	}
	
//	public boolean getDeliveryAreaById(String id) {
//		try (ResultSet rs = daoDA.getDeliveryAreaById(id)) {
//			if (rs != null && rs.next()) {
//				rs.close();
////				try (ResultSet rsForPrint = daoDA.getDeliveryAreaById(id)) {
////					printDeliveryAreaTable(rsForPrint);
////				}
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			System.out.println("Error retrieving Delivery Area records: " + e.getMessage());
//			return false;
//		}
//	}

	private static void printDeliveryPersonTable(ResultSet rs) throws Exception {
		System.out.printf("%-10s%-20s%-15s%-10s\n", "ID", "Name", "Phone", "Area ID");
		System.out.println("---------------------------------------------------------------------------------");

		while (rs.next()) {
			System.out.printf("%-10s%-20s%-15s%-10s\n", rs.getInt("delivery_person_id"),
					rs.getString("delivery_person_name"), rs.getString("delivery_person_phone"),
					rs.getString("area_id"));
		}
		System.out.println("=================================================================================\n");
	}
}
