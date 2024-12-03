package docket;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Docket {
	private String docketId; 
    private String orderId;
    private String custId;
    private String pubId;
    private Date delDate;
    private String delStatus;

    
    public Docket() {
        this.docketId = "";
        this.orderId = "";
        this.custId = "";
        this.pubId = "";
        this.delDate = new Date(); 
        this.delStatus = ""; 
    }


	public Docket( String orderId, String custId, String pubId, Date delDate, String delStatus) throws DocketExceptionHandler {
		try {
			validateDeliveryDate(delDate);
			validateStatus(delStatus);
			
		}catch(DocketExceptionHandler e) {
			throw e;
		}
		
		this.orderId = orderId;
		this.custId = custId;
		this.pubId = pubId;
		this.delDate = delDate;
		this.delStatus = delStatus;
	}

	public String getDocketId() {
		return docketId;
	}

	public void setDocketId(String docketId) {
		this.docketId = docketId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
    
	public String getCustId() {
		return custId;
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


	public Date getDelDate() {
		return delDate;
	}


	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}


	public String getDelStatus() {
		return delStatus;
	}


	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}


	public static void validateDeliveryDate(Date deliveryDate) throws DocketExceptionHandler {
	    if (deliveryDate == null) {
	        throw new DocketExceptionHandler("Delivery date cannot be null. Please provide a valid date.");
	    }
	    
	    Date today = new Date();
	    // Check if the delivery date is too far in the future (e.g., more than 1 year from today)
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(today);
	    calendar.add(Calendar.YEAR, 1); // Add 1 year to the current date
	    Date oneYearFromToday = calendar.getTime();

	    if (deliveryDate.after(oneYearFromToday)) {
	        throw new DocketExceptionHandler("Delivery date is too far in the future. Please select a date within the next year.");
	    }
	}

	
	public void validateStatus(String str) throws DocketExceptionHandler {
		String status = str.toUpperCase();
	    if (status == null || status.trim().isEmpty()) {
	        throw new DocketExceptionHandler("Status cannot be null or empty. Please provide a valid status.");
	    }
	    List<String> validStatuses = List.of("DELIVERED", "NOT DELIVERED", "IN DELIVERY");
	    if (!validStatuses.contains(status)) {
	        throw new DocketExceptionHandler("Invalid status. Expected values are: Delivered, Not Delivered");
	    }
	}


	@Override
	public String toString() {
		return "Docket [docketId=" + docketId + ", orderId=" + orderId + ", custId=" + custId + ", pubId=" + pubId
				+ ", delDate=" + delDate + ", delStatus=" + delStatus + "]";
	}

	
}

