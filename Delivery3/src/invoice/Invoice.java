package invoice;

import java.util.Date;

import customer.Customer;
import customer.CustomerExceptionHandler;
import customer.CustomerMySQLAccess;
import order.Order;
import order.OrderMySQLAccess;

public class Invoice {

	private int invoiceId;
	private String custId;
	private String orderId;
	private double totalPayment;
	private Date invoiceDate;
	private String custAddress;
	private String invoiceStatus;
	private boolean customerPaid;

	public Invoice(String custId, String orderId, double totalPayment,  Date invoiceDate, String custAddress, boolean customerPaid)
			throws InvoiceExceptionHandler {
		this.custId = custId;
		this.orderId = orderId;
		this.totalPayment = totalPayment;
		this.invoiceDate = invoiceDate;
		this.custAddress = custAddress;
		this.customerPaid = customerPaid;
	}

	public Invoice(int invoiceId, boolean customerPaid, Date invoiceDate) {
		this.invoiceId = invoiceId;
			this.customerPaid = customerPaid;
			this.invoiceDate = invoiceDate;
			}
		// TODO Auto-generated constructor stub
	
    public Invoice(int invoiceId, String custId, String orderId, double pubId, Date invoiceDate,String invoiceStatus,  double totalPayment) {
			 this.invoiceId = invoiceId;
			 this.custId = custId;
			 this.orderId = orderId;
			 	this.totalPayment = pubId;
			 	this.invoiceDate = invoiceDate;
			 	this.invoiceStatus = invoiceStatus;
			 	this.totalPayment = totalPayment;
			 	
			 	
			 	
		// TODO Auto-generated constructor stub
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public boolean isCustomerPaid() {
		return customerPaid;
	}

	public void setCustomerPaid(boolean customerPaid) {
		this.customerPaid = customerPaid;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	
//	public static void validateOrderId(String orderId) throws InvoiceExceptionHandler {
//	    if (orderId == null || orderId.isEmpty()) {
//	        throw new InvoiceExceptionHandler("Order ID cannot be null or empty.");
//	    }
//	    try {
//	    	OrderMySQLAccess orderAccess = new OrderMySQLAccess();
//		    Order order = orderAccess.getOrderById(orderId);
//
//		    if (order == null) {
//		        throw new InvoiceExceptionHandler("Order ID " + orderId + " does not exist in the database");
//		    }
//	    }catch(Exception e) {
//	    	e.getStackTrace();
//	    }
//	    
//	}
//
//	public static void validateCustId(String custId) throws InvoiceExceptionHandler {
//	    if (custId == null || custId.isEmpty()) {
//	        throw new InvoiceExceptionHandler("Customer ID cannot be null or empty.");
//	    }
//	    try {
//	    	CustomerMySQLAccess custAccess = new CustomerMySQLAccess();
//		    Customer customer = custAccess.getCustomerById2(custId);
//
//		    if (customer == null) {
//		        throw new InvoiceExceptionHandler("Customer ID " + custId + " does not exist in the database");
//		    }
//	    }catch(Exception e) {
//	    	e.getStackTrace();
//	    }
//	}
//	
//	public static void validatePayment(double totalPayment) throws InvoiceExceptionHandler {
//	    if (totalPayment == 0) {
//	        throw new InvoiceExceptionHandler("Total payment cannot be zero. Please enter a valid payment amount.");
//	    }
//	    if (totalPayment < 0) {
//	        throw new InvoiceExceptionHandler("Total payment cannot be negative. Please enter a positive payment amount.");
//	    }
//	}

}
