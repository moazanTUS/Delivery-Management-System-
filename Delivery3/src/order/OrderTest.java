package order;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.fail;

public class OrderTest {

    Order orderObj = new Order();
    
    @Test
    public void testValidateDateEmpty() {
      try {
      orderObj.validateDate(null);
      fail("Exception expected");
  } catch (OrderExceptionHandler e) {
      assertEquals("Order date cannot be null.", e.getMessage());
  }
}
    @Test
    public void testValidateOrderDateInFuture() {
        try {
            // Set an order date in the future
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1); // Add one day to the current date
            Date futureDate = calendar.getTime();

            orderObj.validateDate(futureDate);
            fail("Exception expected");
        } catch (OrderExceptionHandler e) {
            assertEquals("Order date cannot be in the future.", e.getMessage());
        }
    }

    
    // Test for empty Frequency
    @Test
    public void testValidateFrequencyEmpty() {
        try {
            orderObj.validateFrequency("");
            fail("Exception expected");
        } catch (OrderExceptionHandler e) {
            assertEquals("Invalid order frequency. Expected values: Daily, Weekly, Monthly, or Yearly.", e.getMessage());
        }
    }

    @Test
    public void testValidateFrequencyDaily() {
        try {
            orderObj.validateFrequency("Daily");
        } catch (OrderExceptionHandler e) {
            fail("Exception not expected");
        }
    }
    @Test
    public void testValidateFrequencyWeekly() {
        try {
            orderObj.validateFrequency("Weekly");
        } catch (OrderExceptionHandler e) {
            fail("Exception not expected");
        }
    }
    
    @Test
    public void testValidateFrequencyMonthly() {
        try {
            orderObj.validateFrequency("Monthly");
        } catch (OrderExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testValidateFrequencyYearly() {
        try {
            orderObj.validateFrequency("Yearly");
        } catch (OrderExceptionHandler e) {
            fail("Exception not expected");
        }
    }
    
    @Test
    public void testValidateFrequencyInvalid() {
        try {
            orderObj.validateFrequency("Biweekly");
            fail("Exception expected");
        } catch (OrderExceptionHandler e) {
            assertEquals("Invalid order frequency. Expected values: Daily, Weekly, Monthly, or Yearly.", e.getMessage());
        }
    }

    @Test
    public void testValidateFrequencyInvalidFrequency() {
        try {
            orderObj.validateFrequency("Hourly");
            fail("Exception expected");
        } catch (OrderExceptionHandler e) {
            assertEquals("Invalid order frequency. Expected values: Daily, Weekly, Monthly, or Yearly.", e.getMessage());
        }
    }

  
    @Test
    public void testValidateCustomerIdEmpty() {
        try {
            orderObj.validateCustomerId(null);
            fail("Exception expected");
        } catch (OrderExceptionHandler e) {
            assertEquals("Customer ID cannot be empty.", e.getMessage());
        }
    }
    
    @Test
    public void testValidateCustomerIdValid() {
        try {
            orderObj.validateCustomerId("123456"); // Exactly 6 numeric characters
            orderObj.validateCustomerId("987654321"); // More than 6 numeric characters
        } catch (OrderExceptionHandler e) {
            fail("Exception not expected");
        }
    }


    @Test
    public void testValidateCustomerIdWithAlphabets() {
        try {
            orderObj.validateCustomerId("123a56"); // Contains alphabets
            fail("Exception expected");
        } catch (OrderExceptionHandler e) {
            assertEquals("Customer ID must be numeric and at least 6 characters long.", e.getMessage());
        }
    }

    @Test
    public void testValidateCustomerIdWithSpecialCharacters() {
        try {
            orderObj.validateCustomerId("123@56"); // Contains special characters
            fail("Exception expected");
        } catch (OrderExceptionHandler e) {
            assertEquals("Customer ID must be numeric and at least 6 characters long.", e.getMessage());
        }
    }

   

}
