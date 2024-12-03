package deliveryPerson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Connection.MySQLAccess;
import deliveryPerson.DeliveryPerson;

public class DeliveryPersonMySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public DeliveryPersonMySQLAccess() throws Exception {
		MySQLAccess dbAccess = new MySQLAccess();
		connect = dbAccess.getConnection();
	}

	public boolean insertDeliveryPerson(DeliveryPerson d) {
		boolean insertSuccessfull = true;
		try {
			preparedStatement = connect.prepareStatement(
					"INSERT INTO newsagent.delivery_person (delivery_person_name, delivery_person_phone, area_id) VALUES (?, ?,?)");
			preparedStatement.setString(1, d.getDriverName());
			preparedStatement.setLong(2, d.getDriverPhone());
			preparedStatement.setInt(3, Integer.parseInt(d.getAreaId()));
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Delivery person records inserted successfully!");
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

	public ResultSet retrieveAllDeliveryPerson() {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM newsagent.delivery_person");
		} catch (Exception e) {
			resultSet = null;
		}
		return resultSet;
	}
	
	public ResultSet getDeliveryPersonById(String delPersonID) {
		try {
			preparedStatement = connect.prepareStatement("SELECT * FROM newsagent.delivery_person WHERE delivery_person_id = ?");
		        preparedStatement.setString(1, 	delPersonID);
		        resultSet = preparedStatement.executeQuery();
		}
		catch (Exception e) {
			resultSet=null;
		}
		return resultSet;
	}

	public boolean updateDeliveryPerson(DeliveryPerson d) {
		boolean updateSuccessful = true;
		try {
			preparedStatement = connect.prepareStatement(
					"UPDATE newsagent.delivery_person SET delivery_person_name = ?, delivery_person_phone = ?, area_id = ? WHERE delivery_person_id = ?");

			preparedStatement.setString(1, d.getDriverName());
			preparedStatement.setLong(2, d.getDriverPhone());
			preparedStatement.setString(3, d.getAreaId());
			preparedStatement.setString(4, d.getDriverId());

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

	public boolean deleteDeliveryPersonById(String deliveryPersonID) {

		boolean deleteSucessfull = true;
		try {
			preparedStatement = connect.prepareStatement("delete from newsagent.delivery_person where delivery_person_id = ?");
			preparedStatement.setString(1, deliveryPersonID);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			deleteSucessfull = false;
		}

		return deleteSucessfull;

	}

}
