package docket;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class DocketTest {
	
	Docket docketObj = new Docket();
	
	@Test
	public void testValidateDateEmpty() {
		try {
		docketObj.validateDeliveryDate(null);
		fail("Exception expected");
		}catch (DocketExceptionHandler e) {
			assertEquals("Delivery date cannot be null. Please provide a valid date." , e.getMessage());
		}
	}
	
	//test date in the past
	@Test
	public void testValidateOrderDateInPast() {
	    try {
	        Calendar calendar = Calendar.getInstance();
	        calendar.add(Calendar.DATE, -1); 
	        Date pastDate = calendar.getTime();

	        docketObj.validateDeliveryDate(pastDate); 
	        fail("Exception expected");
	    } catch (DocketExceptionHandler e) {
	        assertEquals("Delivery date cannot be in the past. Please select a future date.", e.getMessage());
	    }
	}
	
	//test date way too far in the future
	@Test
	public void testValidateDeliveryDateTooFarInFuture() {
		try {
		    Calendar calendar = Calendar.getInstance();
		    calendar.add(Calendar.YEAR, 1); 
		    calendar.add(Calendar.DAY_OF_MONTH, 1);
		    Date futureDate = calendar.getTime();

		    docketObj.validateDeliveryDate(futureDate); 
		    fail("Exception expected");
		} catch (DocketExceptionHandler e) {
		    assertEquals("Delivery date is too far in the future. Please select a date within the next year.", e.getMessage());
		}
	}
	
	//test date within the next year
	@Test
	public void testValidateDeliveryDateValid() {
	    try {
	        Calendar calendar = Calendar.getInstance();
	        calendar.add(Calendar.MONTH, 6); 
	        Date validFutureDate = calendar.getTime();

	        docketObj.validateDeliveryDate(validFutureDate);
	    } catch (DocketExceptionHandler e) {
	        fail("Exception not expected");
	    }
	}
	
	//test date for exactly 1 year in the future
	@Test
	public void testValidateDeliveryDateExactlyOneYear() {
	    try {
	        Calendar calendar = Calendar.getInstance();
	        calendar.add(Calendar.YEAR, 1); 
	        Date exactFutureDate = calendar.getTime();

	        docketObj.validateDeliveryDate(exactFutureDate); 
	    } catch (DocketExceptionHandler e) {
	        fail("Exception not expected for a date exactly one year in the future");
	    }
	}
	
	@Test
	public void testValidateStatusValid() {
	    try {
	        docketObj.validateStatus("DELIVERED");
	        docketObj.validateStatus("NOT DELIVERED");
	        docketObj.validateStatus("IN DELIVERY");
	    } catch (DocketExceptionHandler e) {
	        fail("Exception not expected");
	    }
	}

	//test for an invalid status
	@Test
	public void testValidateStatusInvalid() {
	    try {
	    	docketObj.validateStatus("In Progress");
	        fail("Exception expected");
	    } catch (DocketExceptionHandler e) {
	        assertEquals("Invalid status. Expected values are: Delivered, Not Delivered", e.getMessage());
	    }
	}

	//test for empty status
	@Test
	public void testValidateStatusEmpty() {
	    try {
	        docketObj.validateStatus(""); 
	        fail("Exception expected");
	    } catch (DocketExceptionHandler e) {
	        assertEquals("Status cannot be null or empty. Please provide a valid status.", e.getMessage());
	    }
	}


	//test for a null status
	@Test
	public void testValidateStatusNull() {
	    try {
	    	docketObj.validateStatus(null);
	        fail("Exception expected");
	    }catch (DocketExceptionHandler e) {
	        assertEquals("Status cannot be null or empty. Please provide a valid status.", e.getMessage());
	    }
	}




}


