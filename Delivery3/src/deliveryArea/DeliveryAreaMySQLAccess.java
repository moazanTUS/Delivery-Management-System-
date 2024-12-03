package deliveryArea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Connection.MySQLAccess;
import deliveryArea.DeliveryArea;

public class DeliveryAreaMySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public DeliveryAreaMySQLAccess() throws Exception {
		MySQLAccess dbAccess = new MySQLAccess();
		connect = dbAccess.getConnection();
	}

	public boolean insertDeliveryArea(DeliveryArea d) {
		boolean insertSuccessfull = true;
		try {
			preparedStatement = connect
					.prepareStatement("INSERT INTO newsagent.delivery_area (area_address, num_of_cust) VALUES (?, ?)");
			preparedStatement.setString(1, d.getAreaAddress());
			preparedStatement.setInt(2, d.getNumOfCustomer());
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Delivery area records inserted successfully!");
			} else {
				insertSuccessfull = false;
			}
		} catch (SQLException e) {
			insertSuccessfull = false;
			System.err.println("SQL Exception: " + e.getMessage());
		} catch (Exception e) {
			insertSuccessfull = false;
			e.printStackTrace();
		}
		return insertSuccessfull;
	}

	public ResultSet retrieveAllDeliveryArea() {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM newsagent.delivery_area");
		} catch (Exception e) {
			resultSet = null;
		}
		return resultSet;
	}
	
	public ResultSet getDeliveryAreaById(String delAreaID) {
		try {
			preparedStatement = connect.prepareStatement("SELECT * FROM newsagent.delivery_area WHERE area_id = ?");
		        preparedStatement.setString(1, 	delAreaID);
		        resultSet = preparedStatement.executeQuery();
		}
		catch (Exception e) {
			resultSet=null;
		}
		return resultSet;
	}

	public boolean updateDeliveryArea(DeliveryArea d) {
		boolean updateSuccessful = true;
		try {
			preparedStatement = connect.prepareStatement(
					"UPDATE newsagent.delivery_area SET area_address = ?, num_of_cust = ? WHERE area_id = ?");

			preparedStatement.setString(1, d.getAreaAddress());
			preparedStatement.setInt(2, d.getNumOfCustomer());
			preparedStatement.setInt(3, Integer.parseInt(d.getAreaId()));

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Delivery area updated successfully with areaID = " + d.getAreaId());
			} else {
				updateSuccessful = false;
				System.out.println("No delivery area found with areaID = " + d.getAreaId());
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

	public boolean deleteDeliveryAreaById(String areaID) {

		boolean deleteSucessfull = true;
		try {
			preparedStatement = connect.prepareStatement("delete from newsagent.delivery_area where area_id = ?");
			preparedStatement.setString(1, areaID);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			deleteSucessfull = false;
		}

		return deleteSucessfull;

	}

}
