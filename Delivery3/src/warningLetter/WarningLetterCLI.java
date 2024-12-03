package warningLetter;

import java.sql.ResultSet;
import java.util.Scanner;
import invoice.Invoice;

public class WarningLetterCLI {

    private WarningLetterMySQLAccess dao;

    public WarningLetterCLI() throws Exception {
        dao = new WarningLetterMySQLAccess();
    }

    private static void displayWarningLetterMenu() {
        System.out.println("\n\t\tWarning Letter Menu:");
        System.out.println("=============================================");
        System.out.println("Please choose ONE of the following options:");
        System.out.println("1. Add New Warning Letter");
        System.out.println("2. View ALL Warning Letters");
        System.out.println("3. Update Warning Letter by ID");
        System.out.println("4. Delete Warning Letter by ID");
        System.out.println("99. Return to Main Menu");
        System.out.println("=============================================\n");
    }

    private static void printWarningLetterTable(ResultSet rs) throws Exception {
        System.out.println("\n=================================================================================");
        System.out.printf("%-15s%-15s%-30s%n", "Warning ID", "Invoice ID", "Reminder");
        System.out.println("---------------------------------------------------------------------------------");

        while (rs.next()) {
            String warningId = rs.getString("warning_id");
            int invoiceId = rs.getInt("invoice_id");
            String reminder = rs.getString("reminder");
            System.out.printf("%-15s%-15d%-30s%n", warningId, invoiceId, reminder);
        }
        System.out.println("=================================================================================\n");
    }


    public void run(Scanner scan) {
        boolean keepAppOpen = true;
        String functionNumber;
        while (keepAppOpen) {
            displayWarningLetterMenu();
            System.out.print("Enter Your Selection: ");
            functionNumber = scan.next();

            switch (functionNumber) {
                case "1":
                    createWarningLetter(scan);
                    break;
                case "2":
                    getAllWarningLetters();
                    break;
                case "3":
                    updateWarningLetter(scan);
                    break;
                case "4":
                    deleteWarningLetter(scan);
                    break;
                case "99":
                    keepAppOpen = false;
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public void createWarningLetter(Scanner scan) {
        try {
        	scan.nextLine(); 
            System.out.print("Enter Invoice ID: ");
            int invoiceId = Integer.parseInt(scan.nextLine());

            Invoice invoice = dao.getInvoiceById(invoiceId);
            if (invoice == null) {
                System.out.println("Invoice not found. Please enter a valid Invoice ID.");
                return;
            }

            WarningLetter warningLetter = new WarningLetter("", invoice);

            String reminder;
            if (!invoice.isCustomerPaid()) {
                if (!warningLetter.isTotalPaymentMadeWithin90Days()) {
                    reminder = "FINAL REMINDER: Account suspended. Pay now.";
                } else if (!warningLetter.isTotalPaymentMadeWithin60Days()) {
                    reminder = "Second reminder: Please Pay Now.";
                } else if (!warningLetter.isTotalPaymentMadeWithin30Days()) {
                    reminder = "First Reminder Please Pay Now.";
                } 
             else {
                System.out.println("Invoice is already paid or ongoing. No warning letter required.");
                return;
            }

            warningLetter.setReminder(reminder);

            boolean insertResult = dao.insertWarningLetter(warningLetter);
            if (insertResult) {
                System.out.println("Warning Letter added successfully with ID: " + warningLetter.getWarningId());
            } else {
                System.out.println("Failed to add Warning Letter.");
            }
        }} catch (Exception e) {
            System.out.println("Error creating warning letter please enter integer: " + e.getMessage());
        }
    }




    public void getAllWarningLetters() {
        try (ResultSet rs = dao.getAllWarningLetters()) {
            if (rs != null && rs.next()) {
                rs.beforeFirst(); 
                printWarningLetterTable(rs);
            } else {
                System.out.println("No warning letters found.");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving warning letters: " + e.getMessage());
        }
    }

    public void updateWarningLetter(Scanner scan) {
        try {
        	scan.nextLine();
        	System.out.print("Enter Warning Letter ID to update: ");
            String warningIdInput = scan.nextLine();
            int warningId = Integer.parseInt(warningIdInput);

            WarningLetter warningLetter = dao.getWarningLetterById(warningId);
            if (warningLetter == null) {
                System.out.println("Warning Letter not found. Please enter a valid Warning Letter ID.");
                return;
            }

            System.out.print("Enter new Reminder: ");
            String newReminder = scan.nextLine();
            
            if (newReminder.isEmpty()) {
                System.out.println("Reminder cannot be empty. Please enter a valid reminder.");
                return; 
                }

            warningLetter.setReminder(newReminder);

            boolean updateResult = dao.updateWarningLetter(warningLetter);
            if (updateResult) {
                System.out.println("Warning Letter updated successfully.");
            } else {
                System.out.println("Failed to update Warning Letter.");
            }
        } catch (Exception e) {
            System.out.println("Error updating warning letter please enter Integer: " + e.getMessage());
        }
    }

    public void deleteWarningLetter(Scanner scan) {
        try {
        	scan.nextLine();
            System.out.print("Enter Warning Letter ID to delete (-99 to cancel): ");
            int warningId;

            try {
                warningId = Integer.parseInt(scan.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for Warning Letter ID. Please enter an integer.");
                return;
            }

            if (warningId == -99) return;

            boolean deleteResult = dao.deleteWarningLetterById(warningId);
            if (deleteResult) {
                System.out.println("Warning Letter deleted successfully.");
            } else {
                System.out.println("Warning Letter not found. Please enter a valid Warning Letter ID.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting warning letter: " + e.getMessage());
        }
    }

    }

