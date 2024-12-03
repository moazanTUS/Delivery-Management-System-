package publication;

import java.sql.ResultSet;
import java.util.Scanner;

public class PublicationCLI {
	
	private PublicationMySQLAccess dao;
	
	public PublicationCLI() throws Exception{
		dao = new PublicationMySQLAccess(); 
	}
	
	Scanner scan = new Scanner(System.in); 
	
	private static void displayPublicationMenu() {
		System.out.println("\n\t\tPublication Menu:");
		System.out.println("=============================================");
		System.out.println("Please choose ONE of the following options:");
		System.out.println("1. Add New Publication");
		System.out.println("2. View ALL Publications");
		System.out.println("3. Update Publication by ID");
		System.out.println("4. Delete Publication by ID");
		System.out.println("99.Exit the Application");
		System.out.println("=============================================");
		System.out.println(" ");
		
	}
	
	private static void printPublicationTable(ResultSet rs) throws Exception {
		System.out.println("\n=================================================================================");
	    System.out.printf("%-10s%-30s%-10s%-15s%-10s\n", 
	                      "ID", "Name", "Price", "Quantity", "Type");
	    System.out.println("---------------------------------------------------------------------------------");
	    
	    while (rs.next()) {
	        int id = rs.getInt("pub_id");
	        String name = rs.getString("pub_name");
	        Double price = rs.getDouble("pub_price");
	        int qty = rs.getInt("pub_qty");
	        String type = rs.getString("pub_type");

	        System.out.printf("%-10d%-30s%-10s%-15s%-10s\n", 
	                          id, name, price, qty, type);
		}
		System.out.println("=================================================================================\n");		
		
	}

	public void run(Scanner scan) {
		String functionNumber = null;
		boolean keepAppOpen = true;
		
		while (keepAppOpen) {
			displayPublicationMenu();
			System.out.printf("Enter Your Selection:");
			functionNumber = scan.next();
			switch (functionNumber) {
				case "1":
				    createPublication(scan);
				    break;
				case "2": 
					getAllPublications();
					break;
				case "3":
				    updatePublication(scan);
				    break;
				case "4":
					deletePublication(scan);
					break;
				case "99":
					keepAppOpen = false;
					break;
				default:
					System.out.println("No valid function selected.");
					break;
			}
		}
		
	}
	
	public void createPublication(Scanner scan) {
	    try{
	    	scan.nextLine();
            System.out.printf("Enter Publication Name: ");
            String pubName = scan.nextLine();
            
            System.out.printf("Enter Publication Price: ");
            String inputPrice = scan.next();
            if(!inputPrice.matches("\\d+")) {
            	System.out.println("Error: Price can only contain numbers.");
            	return;
            }
            double pubPrice = Double.parseDouble(inputPrice);
            
            System.out.printf("Enter Publication Quantity: ");
            String inputQty = scan.next();
            if(!inputQty.matches("\\d+")) {
            	System.out.println("Error: Quantity can only contain numbers.");
            	return;
            }
            int pubQty = Integer.parseInt(inputQty);
            
            System.out.printf("Enter Publication Type: ");
            String pubType = scan.next();
            
            Publication pubObj = new Publication(pubName, pubPrice, pubQty, pubType);
            boolean insertResult = dao.insertPublication(pubObj);
    	    if (insertResult) {
    	        System.out.println("Publication added into the system.");
    	    } else {
    	        System.out.println("ERROR: Publication is not Saved");
    	    }
        }catch(Exception e) {
        	System.out.println("Error inserting publication:  "+e.getMessage());
        } 
	}
	
	public void getAllPublications(){
		try (ResultSet rs = dao.getAllPublications()){
			if(rs!=null) {
				printPublicationTable(rs);
			}else {
				System.out.println("No publication found.");
			}
		} catch(Exception e) {
			System.out.println("Error retrieving publication records: "+e.getMessage());
		}
	}
	
	private void updatePublication(Scanner scan) {
		getAllPublications();
		System.out.println("Enter Publication Id to be updated (-99 to cancel):");
	    String updatePubId = scan.next();
	    boolean pubExists = getPublicationById(updatePubId);
	    if (!pubExists) {
	        System.out.println("Publication ID does not exist. Exiting update process.");
	        return;
	    }
	    if (!updatePubId.equals("-99") && pubExists) {
	    	scan.nextLine();
	    	System.out.printf("Enter Publication Name: ");
            String newPubName = scan.nextLine();
	        
	        System.out.println("Enter new Publication Price: ");
	        String inputPrice = scan.next();
            if(!inputPrice.matches("\\d+")) {
            	System.out.println("Error: Price can only contain numbers.");
            	return;
            }
	        double newPubPrice = Double.parseDouble(inputPrice); 
	        
	        System.out.println("Enter new Publication Quantity:");
	        String qtyStr = scan.next();
	        if(!qtyStr.matches("\\d+")) {
            	System.out.println("Error: Quantity can only contain numbers.");
            	return;
            }
	        int newPubQty = Integer.parseInt(qtyStr);
	        
	        System.out.println("Enter new Publication Type:");
	        String newPubType = scan.next();
	        
	        Publication updatePub = new Publication();
	        updatePub.setPubId(updatePubId);
	        updatePub.setPubName(newPubName);
	        updatePub.setPubPrice(newPubPrice);
	        updatePub.setPubQty(newPubQty);
	        updatePub.setPubType(newPubType);
	        
	        boolean updateResult = dao.updatePublication(updatePub);
	    }
	}
	
	public boolean getPublicationById(String id){
		try (ResultSet rs = dao.getPublicationById(id)){
			if(rs!=null && rs.next()) {
				rs.close();
                try (ResultSet rsForPrint = dao.getPublicationById(id)) { 
                    printPublicationTable(rsForPrint);
                }
                return true;
			}else {
				System.out.println("No publication with the id = "+id+" found.");
				return false;
			}
		} catch(Exception e) {
			System.out.println("Error retrieving publication records: "+e.getMessage());
			return false;
		}
	}
	
	public boolean getPubById(String id){
		try (ResultSet rs = dao.getPublicationById(id)){
			if(rs!=null && rs.next()) {
                return true;
			}else {
				System.out.println("No publication with the id = "+id+" found.");
				return false;
			}
		} catch(Exception e) {
			System.out.println("Error retrieving publication records: "+e.getMessage());
			return false;
		}
	}
	
	private void deletePublication(Scanner scan) {
		System.out.println("Enter Publication Id to be deleted (-99 to cancel)");
		String deletePubId = scan.next();
		if(deletePubId.equals("-99")) return;
		boolean pubExists = getPublicationById(deletePubId);
	    if (!pubExists) {
	        System.out.println("Publication ID does not exist.");
	        return;
	    }
		System.out.println("Confirm delete this publication from the system? (Y/N):");
		String confirmDelete = scan.next();
		if(confirmDelete.equals("Y") || confirmDelete.equals("y")) {
			boolean deleteResult = dao.deletePublicationById(deletePubId);
			if (deleteResult == true)
				System.out.println("Publication deleted successfully!");
			else 
				System.out.println("ERROR: Publication not deleted or does not exist.");
		}else {
			System.out.println("Deletion cancelled.");
		}
		
	}
}
