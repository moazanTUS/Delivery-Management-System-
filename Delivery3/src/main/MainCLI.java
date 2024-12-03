package main;

import java.sql.ResultSet;

import java.util.Scanner;

import customer.Customer;
import customer.CustomerCLI;
import customer.CustomerExceptionHandler;
import customer.CustomerMySQLAccess;
import deliveryArea.DeliveryAreaCLI;
import deliveryArea.DeliveryAreaMySQLAccess;
import deliveryPerson.DeliveryPersonCLI;
import docket.DocketCLI;
import docket.DocketMySQLAccess;
import invoice.InvoiceCLI;
//import invoice.InvoiceCLI;
import order.OrderCLI;
import publication.PublicationCLI;
import warningLetter.WarningLetterCLI;

public class MainCLI {
	private static DocketMySQLAccess daoD;
	private static DeliveryAreaMySQLAccess daoDA;

	static {
		try {
			daoD = new DocketMySQLAccess();
			daoDA = new DeliveryAreaMySQLAccess();
		} catch (Exception e) {
			System.out.println("Error initializing database connections: " + e.getMessage());
			System.exit(1); 
		}
	}

	public MainCLI() throws Exception {
		this.daoD = new DocketMySQLAccess();
		this.daoDA = new DeliveryAreaMySQLAccess();
	}

	private static void displayMenu() {
		System.out.println("\n\t\tMenu:");
		System.out.println("=============================================");
		System.out.println("Please choose ONE of the following options:");
		System.out.println("1. News Agent (PW:abcde)");
		System.out.println("2. Delivery Driver");
		System.out.println("99.Exit the Application");
		System.out.println("=============================================");
		System.out.println(" ");

	}

	private static void newsAgentDisplayMenu() {
		System.out.println("\n\t\tNews Agent Menu:");
		System.out.println("=============================================");
		System.out.println("Please choose ONE of the following options:");
		System.out.println("1. Customers Menu");
		System.out.println("2. Delivery area Menu");
		System.out.println("3. Delivery persons account");
		System.out.println("4. Orders account");
		System.out.println("5. Delivery dockets account");
		System.out.println("6. Publication account");
		System.out.println("7. Invoice account");
		System.out.println("8. Warning letter account");
		System.out.println("99.Exit the Application");
		System.out.println("=============================================");
		System.out.println(" ");

	}

	private static void deliveryPersonDisplayMenu() {
		System.out.println("\n\t\tDelivery Person Menu:");
		System.out.println("=============================================");
		System.out.println("Please choose ONE of the following options:");
		System.out.println("1. View dockets");
		System.out.println("2. Update docket status");
		System.out.println("3. View invoice");
		System.out.println("4. View warning letter");
		System.out.println("99.Exit the Application");
		System.out.println("=============================================");
		System.out.println(" ");

	}

	public static void main(String[] args) {
		try {

			Scanner scan = new Scanner(System.in);
			String functionNumber = "-99";
			boolean keepAppOpen = true;
			while (keepAppOpen == true) {
				displayMenu();
				System.out.printf("Enter Your Selection:");
				functionNumber = scan.next();
				switch (functionNumber) {

				case "1":
					String newsAgentPw = "abcde";
					System.out.println("Enter your news agent password:");
					String NApw = scan.next();
					if (NApw.equals(newsAgentPw)) {
						System.out.println("News agent login succesful!");
						boolean keepNewsAgentOpen = true;
						while (keepNewsAgentOpen) {
							newsAgentDisplayMenu();
							System.out.printf("Enter Your Selection:");
							functionNumber = scan.next();
							switch (functionNumber) {
							case "1":
								CustomerCLI customerCLI = new CustomerCLI();
								customerCLI.run(scan);
								break;
							case "2":
								DeliveryAreaCLI deliveryAreaCLI = new DeliveryAreaCLI();
								deliveryAreaCLI.run(scan);
								break;
							case "3":
								DeliveryPersonCLI deliveryPersonCLI = new DeliveryPersonCLI();
								deliveryPersonCLI.run(scan);
								break;
							case "4":
								OrderCLI orderCLI = new OrderCLI();
								orderCLI.run(scan);
								break;
							case "5":
								DocketCLI docketCLI = new DocketCLI();
								docketCLI.run(scan);
								break;
							case "6":
								PublicationCLI publicationCLI = new PublicationCLI();
								publicationCLI.run(scan);
								break;
							case "7":
								InvoiceCLI invoiceCLI = new InvoiceCLI();
								invoiceCLI.run(scan);
								break;
							case "8":
								WarningLetterCLI warningLetterCLI = new WarningLetterCLI();
								warningLetterCLI.run(scan);
								break;
							case "99":
								keepNewsAgentOpen = false; // Exit to main menu
								break;
							default:
								System.out.println("No Valid Function Selected");
								break;
							}

						}

					} else {
						System.out.println("Wrong password.Please try again.");
					}
					break;

				case "2":
					System.out.println("Enter your delivery area id(1-24):");
					String deliveryAreaId1 = scan.next().trim();
					boolean dareaExists = getDeliveryAreaById(deliveryAreaId1);
					if (!isValidAreaUpdate(deliveryAreaId1, dareaExists)) {
						continue;
					}
					scan.nextLine();
					try {

						System.out.println("Delivery Person login succesful!");
						boolean keepDeliveryPersonOpen = true;
						while (keepDeliveryPersonOpen) {
							deliveryPersonDisplayMenu();
							System.out.printf("Enter Your Selection:");
							functionNumber = scan.next();
							switch (functionNumber) {
							case "1":
								DocketCLI docketCLI = new DocketCLI();
								docketCLI.viewDocket(scan);
								break;
							case "2":
								DocketCLI docketCLI2 = new DocketCLI();
								docketCLI2.updateDocket(scan);
								break;
							case "3":
								InvoiceCLI invoicCLI = new InvoiceCLI();
								invoicCLI.getInvoiceById(scan);
								break;
							case "4":
								WarningLetterCLI warningLetterCLI = new WarningLetterCLI();
								warningLetterCLI.getAllWarningLetters();
								break;
							case "99":
								keepDeliveryPersonOpen = false; // Exit to main menu
								break;
							default:
								System.out.println("No Valid Function Selected");
								break;
							}
						}

					} catch (NumberFormatException e) {
						System.out.println("Invalid input. Please enter a numeric Delivery area ID (1-24).");
					}
					break;

				case "99":
					keepAppOpen = false;
					System.out.println("Closing the Application");
					break;

				default:
					System.out.println("No Valid Function Selected");
					break;
				}

			}

			scan.close();

		}

		catch (Exception e) {
			System.out.println("PROGRAM TERMINATED - ERROR MESSAGE:\n" + e.getMessage());
		}

	}

	public static boolean getDeliveryAreaById(String id) {
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

	private static boolean isValidAreaUpdate(String updateAreaId, boolean areaExists) {
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

}
