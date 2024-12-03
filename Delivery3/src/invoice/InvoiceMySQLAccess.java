package invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Connection.MySQLAccess;
import customer.Customer;
import order.Order;

public class InvoiceMySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public InvoiceMySQLAccess() throws Exception {
		MySQLAccess dbAccess = new MySQLAccess();
		this.connect = dbAccess.getConnection();
	}

	// Method to add an Invoice
//	public boolean addInvoice(Invoice invoice) {
//		boolean insertSuccessfull = true;
//		try {
//			preparedStatement = connect
//					.prepareStatement("INSERT INTO Invoice cust_id, order_id, total_payment, home_address, last_paid_date, customer_paid) VALUES (?, ?, ?, ?, ?, ?)");
//			preparedStatement.setString(1, invoice.getCustomer().getCustId()); 																			
//			preparedStatement.setString(2, invoice.getOrder().getOrderId()); 
//			preparedStatement.setDouble(3, invoice.getTotalPayment()); 
//			preparedStatement.setString(4, invoice.getCustomer().getCustAddress()); 																																									
//			preparedStatement.setDate(5, new java.sql.Date(invoice.getInvoiceDate().getTime())); 																							
//			preparedStatement.setBoolean(6, invoice.isCustomerPaid());
//			int rowsAffected = preparedStatement.executeUpdate();
//			if (rowsAffected > 0) {
//				System.out.println("Delivery area records inserted successfully!");
//			} else {
//				insertSuccessfull = false;
//			}
//
//		} catch (SQLException e) {
//			insertSuccessfull = false;
//			System.err.println("SQL Exception: " + e.getMessage());
//		} catch (Exception e) {
//			insertSuccessfull = false;
//			e.printStackTrace();
//		}
//		return insertSuccessfull;
//	}

	public ResultSet getInvoiceById(String custId) {
		try {
			preparedStatement = connect.prepareStatement(
					"SELECT i.invoice_id, i.order_id, i.invoice_date, i.total_payment, i.invoice_status, "
							+ "c.cust_name, c.cust_address, p.pub_id, p.pub_name, p.pub_type, p.pub_price, o.order_freq, o.order_qty "
							+ "FROM invoice i JOIN customer c ON i.cust_id = c.cust_id "
							+ "JOIN publication p ON i.pub_id = p.pub_id JOIN orders o ON i.order_id = o.order_id "
							+ "WHERE c.cust_id = ?;");

			preparedStatement.setString(1, custId);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			resultSet = null;
			System.err.println("Error retrieving invoice: " + e.getMessage());
		}
		return resultSet;
	}

	public ResultSet getAllInvoices() {
		try {
			String query = "SELECT i.invoice_id, i.order_id, i.invoice_date, i.total_payment, " + "i.invoice_status, "
					+ "c.cust_name, c.cust_address, p.pub_id, p.pub_name, p.pub_type, p.pub_price, o.order_freq, o.order_qty "
					+ "FROM invoice i JOIN customer c ON i.cust_id = c.cust_id "
					+ "JOIN publication p ON i.pub_id = p.pub_id JOIN orders o ON i.order_id = o.order_id;";

			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			resultSet = null;
			System.err.println("Error retrieving invoice: " + e.getMessage());
		}
		return resultSet;
	}

	public boolean deleteInvoiceById(String invoiceId) {
		boolean deleteSuccessful = true;
		try {
			preparedStatement = connect.prepareStatement("DELETE FROM newsagent.invoice WHERE invoice_id = ?");
			preparedStatement.setString(1, invoiceId);
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Invoice deleted successfully with invoiceId = " + invoiceId);
			} else {
				deleteSuccessful = false;
				System.out.println("No invoice found with invoiceId = " + invoiceId);
			}
		} catch (Exception e) {
			deleteSuccessful = false;
			e.printStackTrace();
		}
		return deleteSuccessful;
	}

	public boolean updateInvoiceByID(String invoiceId, String newStatus) {
		boolean updateSuccessful = true;
		try {
			preparedStatement = connect
					.prepareStatement("UPDATE newsagent.invoice SET invoice_status = ? WHERE invoice_id = ?");

			preparedStatement.setString(1, newStatus);
			preparedStatement.setString(2, invoiceId);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Invoice updated successfully with invoiceID = " + invoiceId);
			} else {
				updateSuccessful = false;
				System.out.println("No invoice found with invoiceID = " + invoiceId);
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

	private void closePreparedStatement() {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void closeResultSet() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeConnection() {
		if (connect != null) {
			try {
				connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
