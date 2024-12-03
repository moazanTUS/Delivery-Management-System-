package order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import Connection.MySQLAccess;

public class OrderMySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public OrderMySQLAccess() throws Exception {
		MySQLAccess dbAccess = new MySQLAccess();
		connect = dbAccess.getConnection();
	}

	public boolean insertOrder(Order o) {
		boolean insertSuccessful = true;
		try {
			preparedStatement = connect.prepareStatement(
					"INSERT INTO newsagent.orders (order_date, cust_id, order_freq, order_qty, pub_id) VALUES (?, ?, ?, ?, ?)");
			preparedStatement.setDate(1, new java.sql.Date(o.getOrderDate().getTime()));
			preparedStatement.setString(2, o.getCustId());
			preparedStatement.setString(3, o.getOrderFreq());
			preparedStatement.setInt(4, o.getOrderQty());
			preparedStatement.setString(5, o.getPubId());

			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Order inserted successfully!");
			} else {
				insertSuccessful = false;
			}
		} catch (SQLException e) {
			insertSuccessful = false;
			System.err.println("SQL Exception: " + e.getMessage());
		} catch (Exception e) {
			insertSuccessful = false;
			e.printStackTrace();
		}
		return insertSuccessful;
	}

	public ResultSet retrieveAllOrders() {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM newsagent.orders");
		} catch (Exception e) {
			resultSet = null;
			System.err.println("Error retrieving orders: " + e.getMessage());
		}
		return resultSet;
	}

	public ResultSet getOrderById(String orderID) {
		try {
			preparedStatement = connect.prepareStatement("SELECT * FROM newsagent.orders WHERE order_id = ?");
			preparedStatement.setString(1, orderID);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			resultSet=null;
			e.printStackTrace();
		}
		return resultSet;
	}

	public boolean updateOrder(Order o) {
		boolean updateSuccessful = true;
		try {
			preparedStatement = connect.prepareStatement(
					"UPDATE newsagent.orders SET order_date = ?, cust_id = ?, order_freq = ?, order_qty = ?, pub_id = ? WHERE order_id = ?");

			preparedStatement.setDate(1, new java.sql.Date(o.getOrderDate().getTime()));
			preparedStatement.setString(2, o.getCustId());
			preparedStatement.setString(3, o.getOrderFreq());
			preparedStatement.setInt(4, o.getOrderQty());
			preparedStatement.setString(5, o.getPubId());
			preparedStatement.setString(6, o.getOrderId());

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Order updated successfully with orderId = " + o.getOrderId());
			} else {
				updateSuccessful = false;
				System.out.println("No order found with orderId = " + o.getOrderId());
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

	public boolean deleteOrderById(String orderId) {
		boolean deleteSuccessful = true;
		try {
			preparedStatement = connect.prepareStatement("DELETE FROM newsagent.orders WHERE order_id = ?");
			preparedStatement.setString(1, orderId);
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Order deleted successfully with orderId = " + orderId);
			} else {
				deleteSuccessful = false;
				System.out.println("No order found with orderId = " + orderId);
			}
		} catch (SQLException e) {
			deleteSuccessful = false;
			System.err.println("SQL Exception: " + e.getMessage());
		} catch (Exception e) {
			deleteSuccessful = false;
			e.printStackTrace();
		}
		return deleteSuccessful;
	}
}
