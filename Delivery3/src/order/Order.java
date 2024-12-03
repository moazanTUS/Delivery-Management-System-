package order;

import java.util.Date;
import java.util.List;

public class Order {
	private String orderId;
	private Date orderDate;
	private String custId;
	private String orderFreq;
	private int orderQty;
	private String pubId;

	public Order() {
		this.orderId = null;
		this.orderDate = orderDate;
		this.custId = null;
		this.orderFreq = null;
		this.orderQty=0;
		this.pubId = null;
	}
	public Order(Date orderDate, String custId, String orderFreq, int orderQty, String pubId) throws OrderExceptionHandler {
		try {
			validateDate(orderDate);
			validateCustomerId(custId);
			validateFrequency(orderFreq);
		
		}
		catch (OrderExceptionHandler e) {
			throw e;
		}
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.custId = custId;
		this.orderFreq = orderFreq;
		this.orderQty=orderQty;
		this.pubId = pubId;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustomerId(String custId) {
		this.custId = custId;
	}

	public String getOrderFreq() {
		return orderFreq;
	}

	public void setOrderFreq(String orderFreq) {
		this.orderFreq = orderFreq;
	}
	
	public int getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
	
	public static void validateDate(Date orderDate) throws OrderExceptionHandler {
        if (orderDate == null) {
            throw new OrderExceptionHandler("Order date cannot be null.");
        }
        Date today = new Date();
        if (orderDate.after(today)) {
            throw new OrderExceptionHandler("Order date cannot be in the future.");
        }
    }

    
	public static void validateFrequency(String orderFreq) throws OrderExceptionHandler {
        List<String> validFrequencies = List.of("Daily", "Weekly", "Monthly", "Yearly");
        if (!validFrequencies.contains(orderFreq)) {
            throw new OrderExceptionHandler("Invalid order frequency. Expected values: Daily, Weekly, Monthly, or Yearly.");
        }
    }
    
	public static void validateCustomerId(String custId)throws OrderExceptionHandler {
    	if (custId == null || custId.isEmpty()) {
            throw new OrderExceptionHandler("Customer ID cannot be empty.");
        }
        if (!custId.matches("[0-9]+")){
            throw new OrderExceptionHandler("Customer ID must be numeric and at least 6 characters long.");
        }
    }
}


	