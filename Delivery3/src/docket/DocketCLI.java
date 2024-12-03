package docket;

//1
import java.sql.*;
import java.text.*;
import java.util.Date;
import java.util.Scanner;

import deliveryPerson.DeliveryPerson;

public class DocketCLI {

	private DocketMySQLAccess dao;

	public DocketCLI() throws Exception {
		this.dao = new DocketMySQLAccess();
	}

	private static void displayDocketMenu() {
		System.out.println("\n\t\tDocket Menu:");
		System.out.println("=============================================");
		System.out.println("Please choose ONE of the following options:");
		System.out.println("1. Create Docket");
		System.out.println("2. View ALL Dockets");
		System.out.println("3. Update Docket by ID");
		System.out.println("4. Delete Docket by ID");
		System.out.println("99. Return to Main Menu");
		System.out.println("=============================================");
		System.out.println(" ");
	}

	public void run(Scanner scan) {
		String functionNumber;
		boolean keepAppOpen = true;

		while (keepAppOpen) {
			displayDocketMenu();
			System.out.print("Enter Your Selection: ");
			functionNumber = scan.next();

			switch (functionNumber) {
			case "1":
				createDocket(scan);
				break;
			case "2":
				viewAllDockets();
				break;
			case "3":
				updateDocket(scan);
				break;
			case "4":
				deleteDocket(scan);
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

//	public void viewDocket(Scanner scan) {
//		scan.nextLine();
//
//		System.out.print("Enter Date (yyyy-MM-dd): ");
//		String dateInput = scan.nextLine().trim();
//		if (!isValidDateFormat(dateInput)) {
//			System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
//			return;
//		}
//		System.out.println("You entered: " + dateInput);
//
//		System.out.println("Docket ID   Order ID   Customer ID    Publication ID   Delivery Date   Delivery Status");
//		System.out.println("-----------------------------------------------------------------------------------------");
//
//		try (ResultSet rs = dao.getDocketByDate(dateInput)) {
//			if (rs != null && rs.isBeforeFirst()) {
//				while (rs.next()) {
//					System.out.printf("%-12d %-10d %-14d %-17d %-15s %-20s%n", rs.getInt("docket_id"),
//							rs.getInt("order_id"), rs.getInt("cust_id"), rs.getInt("pub_id"),
//							rs.getDate("del_date").toString(), rs.getString("del_status"));
//				}
//			} else {
//				System.out.println("No docket records found for the specified date.");
//			}
//		} catch (Exception e) {
//			System.out.println("Error retrieving docket records: " + e.getMessage());
//		}
//	}
	
	public void viewDocket(Scanner scan) {
		scan.nextLine();

		System.out.print("Enter Date (yyyy-MM-dd): ");
		String dateInput = scan.nextLine().trim();
		if (!isValidDateFormat(dateInput)) {
			System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
			return;
		}
		System.out.print("Enter Delivery area id(1-24): ");
		String delAreaInput = scan.nextLine().trim();
		try {
		    int delAreaId = Integer.parseInt(delAreaInput); 
		    if (delAreaId < 1 || delAreaId > 24) {
		        System.out.println("Invalid Delivery Area ID. Please enter a number between 1 and 24.");
		        return; 
		    }
		} catch (NumberFormatException e) {
		    System.out.println("Invalid input. Please enter a valid integer between 1 and 24.");
		    return; 
		}
		

		try (ResultSet rs = dao.getDocketByDateAndArea(dateInput,delAreaInput)) {
			if (rs != null && rs.isBeforeFirst()) {
				printDocketTable(rs);
			} else {
				System.out.println("No docket records found for the specified date.");
			}
		} catch (Exception e) {
			System.out.println("Error retrieving docket records: " + e.getMessage());
		}
	}


	private boolean isValidDateFormat(String dateInput) {
		String regex = "^\\d{4}-\\d{2}-\\d{2}$";
		return dateInput.matches(regex);
	}

	private void createDocket(Scanner scan) {
		System.out.print("Enter Order Date to Create Dockets (yyyy-MM-dd): ");
		scan.nextLine();

		String dateInput = scan.nextLine().trim();
		System.out.println("You entered the date: " + dateInput);

		if (dateInput.isEmpty()) {
			System.out.println("Error: You must enter a valid date.");
			return;
		}
		boolean isInserted = dao.createDocketsForDate(dateInput);
		if (isInserted) {
			System.out.println("Docket for " + dateInput + " created.");
		} else {
			System.out.println("Error: Docket details not saved.");
		}

	}

	private void viewAllDockets() {
		try (ResultSet rs = dao.retrieveAllDockets()) {
			if (rs != null && rs.isBeforeFirst()) {
				printDocketTable(rs);
			} else {
				System.out.println("No docket records found.");
			}
		} catch (Exception e) {
			System.out.println("Error retrieving docket records: " + e.getMessage());
		}
	}

	public void updateDocket(Scanner scan) {
		scan.nextLine();
		System.out.print("Enter Docket ID to be updated (-99 to cancel): ");
		String idStr = scan.nextLine();
		if (idStr.equals("-99")) {
		    System.out.println("Operation cancelled.");
		    return;
		}

		if (!idStr.matches("\\d+")) {
		    System.out.println("Error: Docket ID can only contain numbers.");
		    return;
		}
		int docketId = Integer.parseInt(idStr);
		boolean docketExists = findDocketById(docketId);
		if(!docketExists) {
			System.out.println("Error: Docket ID not found. Updating Docket cancelled.");
			return;
		}
		
		if(docketId != -99 && docketExists) {
			System.out.print("Enter New Delivery Status: ");
			String updateStatus = scan.nextLine();
	
			boolean updateResult = dao.updateDocket(updateStatus, docketId);
	
			if (updateResult) {
				System.out.println("Docket Updated Successfully.");
			} else {
				System.out.println("ERROR: Docket NOT Updated.");
			}
		}
	}

	public boolean findDocketById(int id) {
		try (ResultSet rs = dao.findDocketById(id)) {
			if (rs != null && rs.next()) {
				rs.close();
				try (ResultSet rsForPrint = dao.findDocketById(id)) { 
					printDocketTable(rsForPrint);
                }
				return true;
			} else {
				System.out.println("No docket with the id = " + id + " found.");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Error retrieving docket records: " + e.getMessage());
			return false;
		}
	}

	private void deleteDocket(Scanner scan) {
		System.out.print("Enter Docket ID to delete (-99 to cancel): ");
		String id = scan.next();
		if (id.equals("-99")) {
		    System.out.println("Deletion cancelled.");
		    return;
		}

		if (!id.matches("\\d+")) {
		    System.out.println("Error: Docket ID can only contain numbers.");
		    return;
		}
		int docketId = Integer.parseInt(id);
		
		boolean docketExists = findDocketById(docketId);
		if(!docketExists) {
			System.out.println("Error: Docket ID not found. Deleting Docket cancelled.");
			return;
		}
		System.out.println("Confirm delete this docket from the system? (Y/N):");
		String confirmDelete = scan.next();
		if (confirmDelete.equals("Y") || confirmDelete.equals("y")) {
			try {
				boolean isDeleted = dao.deleteDocketById(id);
				if (isDeleted) {
					System.out.println("Docket deleted successfully.");
				} else {
					System.out.println("ERROR: Docket not deleted or does not exist.");
				}
			} catch (Exception e) {
				System.out.println("Error deleting docket: " + e.getMessage());
			}
		} else {
			System.out.println("Docket deletion cancelled.");
		}

	}

	private static void printDocketTable(ResultSet rs) throws Exception {
		System.out.printf("%-12s%-10s%-16s%-20s%-25s%-20s%-15s\n", "Docket ID", "Order ID", "Customer Name",
				"Customer Address", "Publication Name", "Delivery Date", "Status");
		System.out.println(
				"--------------------------------------------------------------------------------------------------------------------------------");

		while (rs.next()) {
			System.out.printf("%-12s%-10s%-16s%-20s%-25s%-20s%-15s\n", rs.getString("docket_id"),
					rs.getString("order_id"), rs.getString("cust_name"), rs.getString("cust_address"),
					rs.getString("pub_name"), rs.getDate("del_date").toString(), rs.getString("del_status"));
		}
		System.out.println(
				"===============================================================================================================================\n");
	}

	private Date parseDate(String dateStr) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		return dateFormat.parse(dateStr);
	}

}
