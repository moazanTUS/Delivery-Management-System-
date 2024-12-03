package publication;

import java.sql.*;
import Connection.MySQLAccess;

public class PublicationMySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public PublicationMySQLAccess() throws Exception {
		MySQLAccess dbAccess = new MySQLAccess();
		connect = dbAccess.getConnection();
    }	
	
	public boolean insertPublication(Publication p) {
		boolean insertSuccessful = true;
		try {
			preparedStatement = connect.prepareStatement("INSERT INTO newsagent.publication (pub_name, pub_price, pub_qty, pub_type) VALUES (?, ?, ?, ?)");
			preparedStatement.setString(1, p.getPubName());
			preparedStatement.setDouble(2, p.getPubPrice());
			preparedStatement.setInt(3, p.getPubQty());
			preparedStatement.setString(4, p.getPubType());
	        int rowsAffected = preparedStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Publication inserted successfully!");
	        } else {
	        	insertSuccessful = false; 
	        }
		}catch (SQLException e) {
	        insertSuccessful = false;
	        System.err.println("SQL Exception: " + e.getMessage());
	    } catch (Exception e) {
	        insertSuccessful = false;
	        e.printStackTrace(); 
	    }
		return insertSuccessful;
	}
	
	public ResultSet getAllPublications() {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM newsagent.publication");
		}
		catch (Exception e) {
			resultSet = null;
		}
		return resultSet;
	}
	
	public boolean updatePublication(Publication p) {
	    boolean updateSuccessful = true;
	    try {
	        preparedStatement = connect.prepareStatement("UPDATE newsagent.publication SET pub_name = ?, pub_price = ?, pub_qty = ?, pub_type = ? WHERE pub_id = ?");
	        
	        preparedStatement.setString(1, p.getPubName());
	        preparedStatement.setDouble(2, p.getPubPrice());
	        preparedStatement.setInt(3, p.getPubQty());
	        preparedStatement.setString(4, p.getPubType());
	        preparedStatement.setString(5, p.getPubId());

	        int rowsAffected = preparedStatement.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("Publication updated successfully with the ID = " + p.getPubId());
	        } else {
	            updateSuccessful = false;
	            System.out.println("No publication found with the ID = " + p.getPubId());
	        }
	    } catch (SQLException e) {
	        updateSuccessful = false;
	        System.err.println("SQL Exception: " + e.getMessage());
	    } catch (Exception e) {
	        updateSuccessful = false;
	        e.printStackTrace();
	    }
	    return updateSuccessful;
	}
	
	public ResultSet getPublicationById(String pubID) {
		try {
			preparedStatement = connect.prepareStatement("SELECT * FROM newsagent.publication WHERE pub_id = ?");
		        preparedStatement.setString(1, pubID);
		        resultSet = preparedStatement.executeQuery();
		}
		catch (Exception e) {
			resultSet=null;
		}
		return resultSet;
	}
	
	public boolean deletePublicationById(String pubID) {

		boolean deleteSucessfull = true;
		try {
			preparedStatement = connect.prepareStatement("delete from newsagent.publication where pub_id = ?");
			preparedStatement.setString(1, pubID);
			preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			deleteSucessfull = false;
		}
		
		return deleteSucessfull;
		
	}
}
