package docket;

//1
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Connection.MySQLAccess;

public class DocketMySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public DocketMySQLAccess() throws Exception {
		MySQLAccess dbAccess = new MySQLAccess();
		connect = dbAccess.getConnection();
	}

	public boolean insertDocket(Docket d) {
		boolean insertSuccessful = true;
//        try {
//            preparedStatement = connect.prepareStatement(
//                    "INSERT INTO newsagent.delivery_docket (order_id, cust_name, cust_address, pub_name, delivery_date, status) VALUES (?, ?, ?, ?, ?)");
//            preparedStatement.setString(1, d.getOrderId());
//            preparedStatement.setString(2, d.getCustName());
//            preparedStatement.setString(3, d.getCustAddress());
//            preparedStatement.setString(4, d.getPubName());
//            preparedStatement.setDate(5, new java.sql.Date(d.getDeliveryDate().getTime()));
//            preparedStatement.setString(6, d.getStatus());
//
//            int rowsAffected = preparedStatement.executeUpdate();
//            if (rowsAffected > 0) {
//                System.out.println("Docket inserted successfully!");
//            } else {
//                insertSuccessful = false;
//            }
//        } catch (SQLException e) {
//            insertSuccessful = false;
//            System.err.println("SQL Exception: " + e.getMessage());
//        } catch (Exception e) {
//            insertSuccessful = false;
//            e.printStackTrace();
//        }
		return insertSuccessful;
	}

	public boolean createDocketsForDate(String orderDate) {
		boolean insertSuccessful = false; // Track success of insertion
		try {
			String selectQuery = "SELECT o.order_id, o.cust_id, o.pub_id, o.order_date "
					+ "FROM orders o WHERE o.order_date = ?;";
			PreparedStatement selectStatement = connect.prepareStatement(selectQuery);
			selectStatement.setString(1, orderDate);
			ResultSet rs = selectStatement.executeQuery();

			String insertQuery = "INSERT INTO delivery_docket (order_id, cust_id, pub_id, del_date, del_status) "
					+ "VALUES (?, ?, ?, ?, ?);";
			PreparedStatement insertStatement = connect.prepareStatement(insertQuery);

			boolean hasOrders = false;
			while (rs.next()) {
				hasOrders = true;
				int orderId = rs.getInt("order_id");
				int customerId = rs.getInt("cust_id");
				int publicationId = rs.getInt("pub_id");
				String deliveryDate = rs.getString("order_date");

				insertStatement.setInt(1, orderId);
				insertStatement.setInt(2, customerId);
				insertStatement.setInt(3, publicationId);
				insertStatement.setString(4, deliveryDate);
				insertStatement.setString(5, "IN DELIVERY");

				int rowsAffected = insertStatement.executeUpdate();
				if (rowsAffected > 0) {
					insertSuccessful = true; // Set to true if at least one insert was successful
					System.out
							.println("Docket created for Order ID " + orderId + " with delivery date " + deliveryDate);
				}
			}

			if (!hasOrders) {
				System.out.println("No orders found for the specified date: " + orderDate);
			}

		} catch (Exception e) {
			insertSuccessful = false;
			System.err.println("Error creating dockets for date: " + e.getMessage());
		}
		return insertSuccessful;
	}

//	public ResultSet getDocketByDate(String orderDate) {
//		ResultSet resultSet = null;
//		try {
//			String query = "SELECT * FROM delivery_docket WHERE del_date = ?;";
//
//			PreparedStatement preparedStatement = connect.prepareStatement(query);
//			preparedStatement.setString(1, orderDate);
//			resultSet = preparedStatement.executeQuery();
//
//		} catch (Exception e) {
//			resultSet = null;
//			System.err.println("Error retrieving dockets by order date: " + e.getMessage());
//		}
//		return resultSet;
//	}
	
//	public ResultSet getDocketByDate(String orderDate) {
//	    ResultSet resultSet = null;
//	    try {
//	        // SQL query similar to retrieveAllDockets but filtered by orderDate
//	        String query = "SELECT d.docket_id, d.order_id, d.del_date, d.del_status, "
//	                     + "c.cust_name, c.cust_address, p.pub_id, p.pub_name, o.order_qty "
//	                     + "FROM delivery_docket d "
//	                     + "JOIN orders o ON d.order_id = o.order_id "
//	                     + "JOIN publication p ON o.pub_id = p.pub_id "
//	                     + "JOIN customer c ON d.cust_id = c.cust_id "
//	                     + "WHERE d.del_date = ?;";  // Filter by orderDate
//
//	        // Prepare the statement and set the parameter
//	        PreparedStatement preparedStatement = connect.prepareStatement(query);
//	        preparedStatement.setString(1, orderDate);
//	        
//	        // Execute the query
//	        resultSet = preparedStatement.executeQuery();
//
//	    } catch (Exception e) {
//	        resultSet = null;
//	        System.err.println("Error retrieving dockets by order date: " + e.getMessage());
//	    }
//	    return resultSet;
//	}
	
	public ResultSet getDocketByDateAndArea(String orderDate, String areaId) {
	    ResultSet resultSet = null;
	    try {
	        // SQL query with filters for both orderDate and areaId
	        String query = "SELECT d.docket_id, d.order_id, d.del_date, d.del_status, "
	                     + "c.cust_name, c.cust_address, p.pub_id, p.pub_name, o.order_qty "
	                     + "FROM delivery_docket d "
	                     + "JOIN orders o ON d.order_id = o.order_id "
	                     + "JOIN publication p ON o.pub_id = p.pub_id "
	                     + "JOIN customer c ON d.cust_id = c.cust_id "
	                     + "WHERE d.del_date = ? AND c.area_id = ?;";  // Added areaId filter

	        // Prepare the statement and set the parameters
	        PreparedStatement preparedStatement = connect.prepareStatement(query);
	        preparedStatement.setString(1, orderDate); // Set the orderDate parameter
	        preparedStatement.setString(2, areaId);   // Set the areaId parameter
	        
	        // Execute the query
	        resultSet = preparedStatement.executeQuery();

	    } catch (Exception e) {
	        resultSet = null;
	        System.err.println("Error retrieving dockets by order date and area: " + e.getMessage());
	    }
	    return resultSet;
	}


	public ResultSet retrieveAllDockets() {
		try {
			String query = "SELECT d.docket_id, d.order_id, d.del_date, d.del_status, "
					+ "c.cust_name, c.cust_address, p.pub_id, p.pub_name, o.order_qty "
					+ "FROM delivery_docket d JOIN orders o ON d.order_id = o.order_id "
					+ "JOIN publication p ON o.pub_id = p.pub_id JOIN customer c ON d.cust_id = c.cust_id;";
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			resultSet = null;
			System.err.println("Error retrieving dockets: " + e.getMessage());
		}
		return resultSet;
	}

	public ResultSet findDocketById(int docketId) {
		try {
			preparedStatement = connect
					.prepareStatement("SELECT d.docket_id, d.order_id, d.del_date, d.del_status, d.cust_id, "
							+ "c.cust_name, c.cust_address, p.pub_id, p.pub_name, o.order_qty "
							+ "FROM delivery_docket d JOIN orders o ON d.order_id = o.order_id "
							+ "JOIN publication p ON o.pub_id = p.pub_id JOIN customer c ON d.cust_id = c.cust_id "
							+ "WHERE d.docket_id = ?;");
			preparedStatement.setInt(1, docketId);
			resultSet = preparedStatement.executeQuery();

		} catch (Exception e) {
			resultSet = null;
			System.err.println("Error retrieving dockets: " + e.getMessage());
		}
		return resultSet;
	}

	public boolean updateDocket(String delStatus, int docketId) {
		boolean updateSuccessful = true;
		try {
			preparedStatement = connect
					.prepareStatement("UPDATE newsagent.delivery_docket SET del_status= ? WHERE docket_id = ?");

			preparedStatement.setString(1, delStatus);
			preparedStatement.setInt(2, docketId);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Docket updated successfully with the id = " + docketId);
			} else {
				updateSuccessful = false;
				System.out.println("No docket found with id = " + docketId);
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

	public boolean deleteDocketById(String docketId) {
		boolean deleteSuccessful = true;
		try {
			preparedStatement = connect.prepareStatement("DELETE FROM newsagent.delivery_docket WHERE docket_id = ?");
			preparedStatement.setString(1, docketId);
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Docket deleted successfully with the id = " + docketId);
			} else {
				deleteSuccessful = false;
				System.out.println("No docket found with id = " + docketId);
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
