package customer;

import customer.CustomerExceptionHandler;

public class Customer {
	private String custId;
	private String custName;
	private long custPhone;
	private String custAddress;
	private String custStatus;
	private String areaId;
	
	public Customer() {
		this.custId = null;
		this.custName = null;
		this.custPhone = 0L;
		this.custAddress = null;
		this.custStatus = null;
	}
	public Customer(String custName, long custPhone, String custAddress, String custStatus, String areaId) throws CustomerExceptionHandler {
		try {
			validateName(custName);
			validateAddress(custAddress);
			validatePhoneNumber(custPhone);
			validateCustStatus(custStatus);
		}
		catch (CustomerExceptionHandler e) {
			throw e;
		}
		this.custName = custName;
		this.custPhone = custPhone;
		this.custAddress = custAddress;
		this.custStatus = custStatus;
		this.areaId=areaId;
	}
	
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public long getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(long custPhone) {
		this.custPhone = custPhone;
	}

	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	public String getCustStatus() {
		return custStatus;
	}
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	
	public static void validateName(String custName) throws CustomerExceptionHandler {
	    if (custName == null || custName.isBlank()) {
	        throw new CustomerExceptionHandler("Customer Name is empty. Please enter Customer Name.");
	    }
	    if (custName.matches(".*[,.@#$%^&*()+=!{}\\[\\]\\\\|?<>].*")) {
	        throw new CustomerExceptionHandler("Customer Name contains invalid characters. Only letters, spaces, and apostrophes are allowed.");
	    }
	    if (custName.matches("\\d+")) {
	        throw new CustomerExceptionHandler("Invalid Customer Name. Customer Name containing only numbers is not allowed.");
	    }
	    if (custName.length() < 3) {
	        throw new CustomerExceptionHandler("Customer Name is too short. Minimum length for Customer Name should be 3 characters.");
	    }
	    if (custName.length() > 50) {
	        throw new CustomerExceptionHandler("Customer Name is too long. Maximum length allowed is 50 characters.");
	    }
	}

	
	public static void validateAddress(String custAddr) throws CustomerExceptionHandler {
	    if (custAddr == null || custAddr.isBlank()) {
	        throw new CustomerExceptionHandler("Address is empty. Please enter Customer Address.");
	    }
	    if (custAddr.length() < 10) {
	        throw new CustomerExceptionHandler("Customer Address must be at least 10 characters long.");
	    }
	    if (custAddr.length() >= 60) {
	        throw new CustomerExceptionHandler("Customer Address exceeds the maximum length of 60 characters.");
	    } 
	    if (custAddr.matches(".*[@#$%^&*`'()+=!{}\\\\[\\\\]\\\\\\\\|?<>].*")) {
	        throw new CustomerExceptionHandler("Customer Address contains invalid characters. Only numbers, letters, spaces, periods, and commas are allowed.");
	    }
	}

	
	public static void validatePhoneNumber(long custPhone) throws CustomerExceptionHandler {
	    String phoneStr = String.valueOf(custPhone);
	    if (phoneStr.isEmpty() || custPhone == 0) {
	        throw new CustomerExceptionHandler("Phone number is empty. Please enter Customer Phone Number.");
	    }
	    if (phoneStr.length() <= 8) {
	        throw new CustomerExceptionHandler("Phone number must be at least 8 digits long.");
	    }
	    if (phoneStr.length() > 15) {
	        throw new CustomerExceptionHandler("Invalid phone number. Phone number is too long.");
	    }
	}

	public static void validateCustStatus(String custStatus) throws CustomerExceptionHandler {
	    
	    if (custStatus == null || custStatus.isBlank()) {
	        throw new CustomerExceptionHandler("Customer Status is empty. Please enter Customer Status.");
	    }
	    String ignoreCaseStatus = custStatus.trim().toLowerCase();
	    if (!ignoreCaseStatus.equals("active") && !ignoreCaseStatus.equals("suspended")) {
	        throw new CustomerExceptionHandler("Invalid Customer Status. Allowed values are 'active' or 'suspended'.");
	    }
	}
}
