package customer;

import java.sql.*;
import Connection.MySQLAccess;

public class CustomerMySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public CustomerMySQLAccess() throws Exception {
		MySQLAccess dbAccess = new MySQLAccess();
		connect = dbAccess.getConnection();
	}

	public boolean insertCustomerDetailsAccount(Customer c) {
		boolean insertSuccessfull = true;
		try {
			String query = "INSERT INTO newsagent.customer (cust_name, cust_address, cust_phone, cust_status, area_id) VALUES (?, ?, ?, ?, ?)";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, c.getCustName());
			preparedStatement.setString(2, c.getCustAddress());
			preparedStatement.setLong(3, c.getCustPhone());
			preparedStatement.setString(4, c.getCustStatus());
			preparedStatement.setString(5, c.getAreaId());
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Customer record inserted successfully!");
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

	public ResultSet retrieveAllCustomerAccounts() {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM newsagent.customer");
		} catch (Exception e) {
			resultSet = null;
		}
		return resultSet;
	}

	public boolean updateCustomerDetailsAccount(Customer c) {
		boolean updateSuccessful = true;
		try {
			preparedStatement = connect.prepareStatement(
					"UPDATE newsagent.customer SET cust_name = ?, cust_address = ?, cust_phone = ?, cust_status = ?, area_id = ? WHERE cust_id = ?");

			preparedStatement.setString(1, c.getCustName());
			preparedStatement.setString(2, c.getCustAddress());
			preparedStatement.setLong(3, c.getCustPhone());
			preparedStatement.setString(4, c.getCustStatus());
			preparedStatement.setString(5, c.getAreaId());
			preparedStatement.setString(6, c.getCustId());

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Customer updated successfully with customerID = " + c.getCustId());
			} else {
				updateSuccessful = false;
				System.out.println("No customer found with customerID = " + c.getCustId());
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

	public boolean deleteCustomerById(String custID) {

		boolean deleteSucessfull = true;
		try {
			preparedStatement = connect.prepareStatement("delete from newsagent.customer where cust_id = ?");
			preparedStatement.setString(1, custID);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			deleteSucessfull = false;
		}

		return deleteSucessfull;

	}

	public String getAreaIdByAddress(String address) {
		String areaId = null;
		String query = "SELECT area_id FROM newsagent.delivery_area WHERE LOWER(area_address) LIKE ?";

		try {
			// remove any house number
			String cleanedAddress = address.replaceAll("^[\\d]+[a-zA-Z]*[\\W]*", "").toLowerCase().trim();
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, "%" + cleanedAddress + "%");
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				areaId = resultSet.getString("area_id");
			} else {
				System.out.println("Area Id with the address entered is not found.");
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return areaId;
	}


	public ResultSet getCustomerById(String custID) {
		try {
			preparedStatement = connect.prepareStatement("SELECT * FROM newsagent.customer WHERE cust_id = ?");
			preparedStatement.setString(1, custID);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			resultSet = null;
		}
		return resultSet;
	}
	
	public String getAddressByCustomerId(String custID) {
		try {
			preparedStatement = connect.prepareStatement("SELECT custAddress FROM newsagent.customer WHERE cust_id = ?");
			preparedStatement.setString(1, custID);
			resultSet = preparedStatement.executeQuery();
			String address = resultSet.getString("custAddress");
            return address;
		} catch (Exception e) {
			resultSet = null;
		}
		return custID;
	}

	public Customer getCustomerById2(String custID) {
		try {
			preparedStatement = connect.prepareStatement("SELECT * FROM newsagent.customer WHERE cust_id = ?");
			preparedStatement.setString(1, custID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String custName = resultSet.getString("cust_name"); // Assuming "cust_name" is a column in your database
				long custPhone = resultSet.getLong("cust_phone"); // Assuming "cust_phone" is a column
				String custAddress = resultSet.getString("cust_address"); // Assuming "cust_address" is a column
				String custStatus = resultSet.getString("cust_status"); // Assuming "cust_status" is a column
				String areaId = resultSet.getString("area_id"); // Assuming "area_id" is a column

				// Create and return a Customer object using the constructor
				return new Customer(custName, custPhone, custAddress, custStatus, areaId);
			}
		} catch (Exception e) {
			System.out.println("Customer not found");
			e.printStackTrace();
		}
		return null;
	}
}
