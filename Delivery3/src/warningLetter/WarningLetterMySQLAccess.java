package warningLetter;

import java.sql.*;
import Connection.MySQLAccess;
import invoice.Invoice;

public class WarningLetterMySQLAccess {

    private Connection connect;

    public WarningLetterMySQLAccess() throws Exception {
        MySQLAccess dbAccess = new MySQLAccess();
        connect = dbAccess.getConnection();
    }

    public boolean insertWarningLetter(WarningLetter warningLetter) {
        String query = "INSERT INTO WarningLetter (invoice_id, reminder) VALUES (?, ?)";
        try (PreparedStatement ps = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, warningLetter.getInvoice().getInvoiceId()); 
            ps.setString(2, warningLetter.getReminder()); 

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        warningLetter.setWarningId(rs.getInt(1)); 
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error inserting warning letter: " + e.getMessage());
        }
        return false;
    }



    public ResultSet getAllWarningLetters() {
        String query = "SELECT warning_id, invoice_id, reminder FROM WarningLetter";
        try {
            Statement stmt = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Error retrieving warning letters: " + e.getMessage());
            return null;
        }
    }



    public boolean updateWarningLetter(WarningLetter warningLetter) {
        String query = "UPDATE WarningLetter SET reminder = ? WHERE warning_id = ?";
        try (PreparedStatement ps = connect.prepareStatement(query)) {
            ps.setString(1, warningLetter.getReminder());
            ps.setInt(2, warningLetter.getWarningId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating Warning Letter: " + e.getMessage());
            return false;
        }
    }



    public WarningLetter getWarningLetterById(int warningId) {
        String query = "SELECT * FROM WarningLetter WHERE warning_id = ?";
        try (PreparedStatement ps = connect.prepareStatement(query)) {
            ps.setInt(1, warningId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("warning_id");
                    int invoiceId = rs.getInt("invoice_id");
                    String reminder = rs.getString("reminder");

                    Invoice invoice = getInvoiceById(invoiceId);

                    WarningLetter warningLetter = new WarningLetter(reminder, invoice);
                    warningLetter.setWarningId(id);
                    warningLetter.setInvoice(invoice);
                    return warningLetter;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Warning Letter by ID: " + e.getMessage());
        }
        return null;
    }



    public boolean deleteWarningLetterById(int warningId) {
        try (PreparedStatement ps = connect.prepareStatement(
                "DELETE FROM WarningLetter WHERE warning_id = ?")) {
            ps.setInt(1, warningId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting warning letter: " + e.getMessage());
            return false;
        }
    }

    public Invoice getInvoiceById(int invoiceId) {
        String query = "SELECT * FROM Invoice WHERE invoice_id = ?";
        try (PreparedStatement ps = connect.prepareStatement(query)) {
            ps.setInt(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("invoice_id");
                    int custId = rs.getInt("cust_id");
                    String orderId = rs.getString("order_id");
                    String pubId = rs.getString("pub_id");
                    Date invoiceDate = rs.getDate("invoice_date");
                    String invoiceStatus = rs.getString("invoice_status");
                    double totalPayment = rs.getDouble("total_payment");

                    return new Invoice(id, pubId, orderId, custId, invoiceDate, invoiceStatus, totalPayment);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Invoice by ID: " + e.getMessage());
        }
        return null; 
    }

		
	
	}

