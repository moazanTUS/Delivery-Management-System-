package customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CustomerTest {
	
	// Test #: 1
    // Test Objective: To create a valid Customer object
    // Inputs: custName = "Mary Liese", custAddress = "3, Willow Park", custPhone = "871231234", custStatus = "Active", areaId = "1"
    // Expected Output: No exception thrown and Customer object created with correct details
    @Test
    public void testCreateCustomerValid() {
        try {
            Customer custObj = new Customer("Mary Liese", 871231234, "3, Willow Park", "Active", "1");

            assertEquals("Mary Liese", custObj.getCustName());
            assertEquals("3, Willow Park", custObj.getCustAddress());
            assertEquals(871231234, custObj.getCustPhone());
            assertEquals("Active", custObj.getCustStatus());
            assertEquals("1", custObj.getAreaId());
        } catch (CustomerExceptionHandler e) {
            fail("Exception not expected");
        }
    }
    
	//Test #: 2
	//Test Objective: To catch an empty Customer Name
	//Inputs: custName = ""
	//Expected Output: Exception Message: "Customer Name is empty. Please enter Customer Name."
	@Test
	public void testValidateNameEmpty() {
	    try {
	        Customer.validateName("");
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Customer Name is empty. Please enter Customer Name.", e.getMessage());
	    }
	}

	//Test #: 3
	//Test Objective: To catch a null Customer Name
	//Inputs: custName = null
	//Expected Output: Exception Message: "Customer Name is empty. Please enter Customer Name."
	@Test
	public void testValidateNameNull() {
	    try {
	        Customer.validateName(null);
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Customer Name is empty. Please enter Customer Name.", e.getMessage());
	    }
	}
	
	//Test #: 4
	//Test Objective: To catch a Customer Name with invalid characters
	//Inputs: custName = "John@Doe"
	//Expected Output: Exception Message: "Customer Name contains invalid characters. Only letters, spaces, and apostrophes are allowed."
	@Test
	public void testValidateNameInvalidCharacters() {
	    try {
	        Customer.validateName("John@Doe");
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Customer Name contains invalid characters. Only letters, spaces, and apostrophes are allowed.", e.getMessage());
	    }
	}
	
	//Test #: 5
	//Test Objective: To catch a Customer Name that consists of only numbers
	//Inputs: custName = "12345"
	//Expected Output: Exception Message: "Invalid Customer Name. Customer Name containing only numbers is not allowed."
	@Test
	public void testValidateNameOnlyNumbers() {
	    try {
	        Customer.validateName("12345");
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Invalid Customer Name. Customer Name containing only numbers is not allowed.", e.getMessage());
	    }
	}

	//Test #: 6
	//Test Objective: To catch an invalid Customer Name length (below boundary)
	//Input: custName = "Vi"
	//Expected Output: Exception Message: "Customer Name is too short. Minimum length for Customer Name should be 3 characters."
	@Test
	public void testValidateNameLengthInvalidLowerBoundary() {
	    try {
	        Customer.validateName("Vi");
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Customer Name is too short. Minimum length for Customer Name should be 3 characters.", e.getMessage());
	    }
	}
		
	//Test #: 7
	//Test Objective: To catch an invalid Customer Name length (above boundary)
	//Inputs: custName = "Jonathan Michael Anderson with the Longest Last Name"
	//Expected Output: Exception Message: "Customer Name is too long. Maximum length allowed is 50 characters."
	@Test
	public void testValidateNameLengthInvalidUpperBoundary() {
	    try {
	        Customer.validateName("Jonathan Michael Anderson with the Longest Last Name");
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Customer Name is too long. Maximum length allowed is 50 characters.", e.getMessage());
	    }
	}

	//Test #: 8
	//Test Objective: To validate a correct Customer Name
	//Inputs: custName = Drew Gooden"
	//Expected Output: No exception thrown
	@Test
	public void testValidateNameValid() {
	    try {
	        Customer.validateName("Drew Gooden");
	    } catch (CustomerExceptionHandler e) {
	        fail("Exception not expected");
	    }
	}

	//Test #: 9
	//Test Objective: To validate a correct Customer Name that has spaces
	//Input: custName = "Sherlock Holmes"
	//Expected Output: No exception thrown
	@Test
	public void testValidateNameWithSpaces() {
	    try {
	        Customer.validateName("Sherlock Holmes");
	    } catch (CustomerExceptionHandler e) {
	        fail("Exception not expected");
	    }
	}

	//Test #: 10
	//Test Objective: To catch an empty Customer Address
	//Input: custAddress = ""
	//Expected Output: Exception Message: "Address is empty. Please enter Customer Address."
	@Test
	public void testValidateAddressEmpty() {
	    try {
	        Customer.validateAddress("");
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Address is empty. Please enter Customer Address.", e.getMessage());
	    }
	}

	//Test #: 11
	//Test Objective: To catch a null Customer Address
	//Input: custAddress = null
	//Expected Output: Exception Message: "Address is empty. Please enter Customer Address."
	@Test
	public void testValidateAddressNull() {
	    try {
	        Customer.validateAddress(null);
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Address is empty. Please enter Customer Address.", e.getMessage());
	    }
	}
	
	//Test #: 12
	//Test Objective: To catch an invalid Customer Address length (below boundary)
	//Inputs: custAddress = "123 St"
	//Expected Output: Exception Message: "Customer Address must be at least 10 characters long."
	@Test
	public void testValidateAddressLengthInvalidLowerBoundary() {
	    try {
	        Customer.validateAddress("123 St");
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Customer Address must be at least 10 characters long.", e.getMessage());
	    }
	}

	//Test #: 13
	//Test Objective: To catch an invalid Customer Address length (above boundary)
	//Inputs: custAddress = "This is an Example of A Very Long Customer Address to Fail this Test "
	//Expected Output: Exception Message: "Customer Address exceeds the maximum length of 60 characters."
	@Test
	public void testValidateAddressLengthInvalidUpperBoundary() {
	    try {
	        Customer.validateAddress("This is an Example of A Very Long Customer Address to Fail this Test ");
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Customer Address exceeds the maximum length of 60 characters.", e.getMessage());
	    }
	}

	//Test #: 14
	//Test Objective: To catch a Customer Address containing invalid characters
	//Inputs: custAddress = "123 Main St.@"
	//Expected Output: Exception Message: "Customer Address contains invalid characters. Only numbers, letters, spaces, periods, and commas are allowed."
	@Test
	public void testValidateAddressInvalidCharacters() {
	    try {
	        Customer.validateAddress("123 Main St.@");
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Customer Address contains invalid characters. Only numbers, letters, spaces, periods, and commas are allowed.", e.getMessage());
	    }
	}

	//Test #: 15
	//Test Objective: To validate a correct Customer Address
	//Inputs: custAddress = "Apt 24B, Meadow Brook, Athlone"
	//Expected Output: No exception thrown
	@Test
	public void testValidateAddressValid() {
	    try {
	        Customer.validateAddress("Apt 24B, Meadow Brook, Athlone");
	    } catch (CustomerExceptionHandler e) {
	        fail("Exception not expected");
	    }
	}

	//Test #: 16
	//Test Objective: To validate a correct Customer Address with period and comma
	//Inputs: custAddress = "13, Main St., Dublin"
	//Expected Output: No exception thrown
	@Test
	public void testValidateAddressWithPeriodAndComma() {
	    try {
	        Customer.validateAddress("13, Main St., Dublin");
	    } catch (CustomerExceptionHandler e) {
	        fail("Exception not expected");
	    }
	}

	//Test #: 17
	//Test Objective: To catch an empty Customer Phone Number
	//Inputs: custPhone = 0L
	//Expected Output: Exception Message: "Phone number is empty. Please enter Customer Phone Number."
	@Test
	public void testValidatePhoneNumberEmpty() {
	    try {
	        Customer.validatePhoneNumber(0L);
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Phone number is empty. Please enter Customer Phone Number.", e.getMessage());
	    }
	}
	
	//Test #: 18
	//Test Objective: To catch an invalid Customer Phone Number length (below boundary)
	//Input: custPhone = 1234567L
	//Expected Output: Exception Message: "Phone number must be at least 8 digits long."
	@Test
	public void testValidatePhoneNumberLengthInvalidLowerBoundary() {
	    try {
	        Customer.validatePhoneNumber(1234567L);
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Phone number must be at least 8 digits long.", e.getMessage());
	    }
	}

	//Test #: 19
	//Test Objective: To catch an invalid Customer Phone Number length (above boundary)
	//Input: custPhone = 922337203685477580L
	//Expected Output: Exception Message: "Invalid phone number. Phone number is too long."
	@Test
	public void testValidatePhoneNumberLengthInvalidUpperBoundary() {
	    try {
	        Customer.validatePhoneNumber(922337203685477580L);
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Invalid phone number. Phone number is too long.", e.getMessage());
	    }
	}

	//Test #: 20
	//Test Objective: To validate a correct Customer Phone Number
	//Input: custPhone = 892558764
	//Expected Output: No exception thrown
	@Test
	public void testValidatePhoneNumberValid() {
	    try {
	        Customer.validatePhoneNumber(892558764);
	    } catch (CustomerExceptionHandler e) {
	        fail("Exception not expected");
	    }
	}

	//Test #: 21
	//Test Objective: To catch a null Customer Status
	//Inputs: custStatus = null
	//Expected Output: Exception Message: "Customer Status is empty. Please enter Customer Status."
	@Test
	public void testValidateStatusNull() {
	    try {
	        Customer.validateCustStatus(null);
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Customer Status is empty. Please enter Customer Status.", e.getMessage());
	    }
	}
	
	//Test #: 22
	//Test Objective: To catch a null Customer Status
	//Input: custStatus = ""
	//Expected Output: Exception Message: "Customer Status is empty. Please enter Customer Status."
	@Test
	public void testValidateStatusEmpty() {
	    try {
	        Customer.validateCustStatus("");
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Customer Status is empty. Please enter Customer Status.", e.getMessage());
	    }
	}
	
	//Test #: 23
	//Test Objective: To catch an invalid Customer Status
	//Input: custStatus = "Deleted"
	//Expected Output: Exception Message: "Invalid Customer Status. Allowed values are 'active' or 'suspended'."
	@Test
	public void testValidateInvalidStatus() {
	    try {
	        Customer.validateCustStatus("Deleted");
	        fail("Exception expected");
	    } catch (CustomerExceptionHandler e) {
	        assertEquals("Invalid Customer Status. Allowed values are 'active' or 'suspended'.", e.getMessage());
	    }
	}
}
