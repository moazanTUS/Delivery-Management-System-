package deliveryArea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class DeliveryAreaTest {
	DeliveryArea deliveryAreaObj = new DeliveryArea();

	// Test #: 1
	// Test Objective: To create a valid Delivery Area object
	// Inputs: areaAddress = "Athlone", numOfCustomer = 20
	// Expected Output: No exception thrown and Delivery Area object created with
	// correct details
	@Test
	public void testCreateDeliveryArea() {
		try {

			DeliveryArea deliveryAreaObj = new DeliveryArea("Athlone", 20);
			assertEquals("Athlone", deliveryAreaObj.getAreaAddress());
			assertEquals(20, deliveryAreaObj.getNumOfCustomer());

		} catch (DeliveryAreaExceptionHandler e) {
			fail("Exception not expected");
		}

	}

	// Test #: 2
	// Test Objective: To validate that creating a delivery area with an invalid
	// (blank) address throws an exception
	// Inputs: areaAddress = " ", numOfCustomer = 5
	// Expected Output: Exception thrown with message "Area address is empty. Please
	// enter a valid area address."
	@Test
	public void testCreateDeliveryAreaInvalidAddress() {
		try {
			new DeliveryArea("  ", 5);
			fail("Exception expected");
		} catch (DeliveryAreaExceptionHandler e) {
			assertEquals("Area address is empty. Please enter a valid area address.", e.getMessage());
		}
	}

	// Test #: 3
	// Test Objective: To validate that creating a delivery area with a negative
	// number of customers throws an exception
	// Inputs: areaAddress = "Athlone", numOfCustomer = -5
	// Expected Output: Exception thrown with message "Number of customers cannot be
	// negative."
	@Test
	public void testCreateDeliveryAreaNegativeCustomers() {
		try {
			new DeliveryArea("Athlone", -5);
			fail("Exception expected");
		} catch (DeliveryAreaExceptionHandler e) {
			assertEquals("Number of customers cannot be negative.", e.getMessage());
		}
	}

	// Test #: 4
	// Test Objective: To validate that area address with leading/trailing
	// whitespace is accepted as valid
	// Inputs: areaAddress = " Valid Address "
	// Expected Output: No exception thrown
	@Test
	public void validateAreaAddressWithWhitespace() {
		try {
			deliveryAreaObj.validateAreaAddress("   Valid Address   ");

		} catch (DeliveryAreaExceptionHandler e) {
			fail("No exception expected for valid area address with leading/trailing whitespace.");
		}
	}

	// Test #: 5
	// Test Objective: To validate a valid number of customers
	// Inputs: numOfCustomer = 50
	// Expected Output: No exception thrown
	@Test
	public void validateNumOfCustomerValid() {
		try {
			deliveryAreaObj.validateNumOfCustomer(50);

		} catch (DeliveryAreaExceptionHandler e) {
			fail("No exception expected for valid number of customers.");
		}
	}

	// Test #: 6
	// Test Objective: To validate the lower boundary for the number of customers
	// (0)
	// Inputs: numOfCustomer = 0
	// Expected Output: No exception thrown
	@Test
	public void validateNumOfCustomerLowerBoundary() {
		try {
			deliveryAreaObj.validateNumOfCustomer(0);
		} catch (DeliveryAreaExceptionHandler e) {
			fail("No exception expected for valid lower boundary.");
		}
	}

	// Test #: 7
	// Test Objective: To validate the upper boundary for the number of customers
	// (100)
	// Inputs: numOfCustomer = 100
	// Expected Output: No exception thrown
	@Test
	public void validateNumOfCustomerUpperBoundary() {
		try {
			deliveryAreaObj.validateNumOfCustomer(100);
		} catch (DeliveryAreaExceptionHandler e) {
			fail("No exception expected for valid lower boundary.");
		}
	}

	// Test #: 8
	// Test Objective: To validate a number of customers just above the lower
	// boundary (1)
	// Inputs: numOfCustomer = 1
	// Expected Output: No exception thrown
	@Test
	public void validateNumOfCustomerJustAboveLowerBoundary() {
		try {
			deliveryAreaObj.validateNumOfCustomer(1);
		} catch (DeliveryAreaExceptionHandler e) {
			fail("No exception expected for valid input just above lower boundary.");
		}
	}

	// Test #: 9
	// Test Objective: To validate a number of customers just below the upper
	// boundary (99)
	// Inputs: numOfCustomer = 99
	// Expected Output: No exception thrown
	@Test
	public void validateNumOfCustomerJustBelowUpperBoundary() {
		try {
			deliveryAreaObj.validateNumOfCustomer(99);
		} catch (DeliveryAreaExceptionHandler e) {
			fail("No exception expected for valid input just below upper boundary.");
		}
	}

	// Test #: 10
	// Test Objective: To validate that an empty area address throws an exception
	// Inputs: areaAddress = ""
	// Expected Output: Exception thrown with message "Area address is empty. Please
	// enter a valid area address."
	@Test
	public void validateAreaAddressEmpty() {
		try {

			deliveryAreaObj.validateAreaAddress("");
			fail("Exception expected");

		} catch (DeliveryAreaExceptionHandler e) {
			assertEquals("Area address is empty. Please enter a valid area address.", e.getMessage());
		}

	}

	// Test #: 11
	// Test Objective: To validate that a blank area address (only spaces) throws an
	// exception
	// Inputs: areaAddress = " "
	// Expected Output: Exception thrown with message "Area address is empty. Please
	// enter a valid area address."
	@Test
	public void validateAreaAddressBlank() {
		try {

			deliveryAreaObj.validateAreaAddress("   ");
			fail("Exception expected");

		} catch (DeliveryAreaExceptionHandler e) {
			assertEquals("Area address is empty. Please enter a valid area address.", e.getMessage());
		}
	}

	// Test #: 12
	// Test Objective: To validate that a null area address throws an exception
	// Inputs: areaAddress = null
	// Expected Output: Exception thrown with message "Area address is empty. Please
	// enter a valid area address."
	@Test
	public void validateAreaAddressNull() {
		try {
			deliveryAreaObj.validateAreaAddress(null);
			fail("Exception expected");
		} catch (DeliveryAreaExceptionHandler e) {
			assertEquals("Area address is empty. Please enter a valid area address.", e.getMessage());
		}
	}

	// Test #: 13
	// Test Objective: To validate that an area address with special characters is
	// accepted as valid
	// Inputs: areaAddress = "Area-42#North"
	// Expected Output: No exception thrown
	@Test
	public void validateAreaAddressSpecialCharacters() {
		try {
			deliveryAreaObj.validateAreaAddress("Area-42#North");
		} catch (DeliveryAreaExceptionHandler e) {
			fail("No exception expected for address with special characters.");
		}
	}

	// Test #: 14
	// Test Objective: To validate that an area address with only special characters
	// Inputs: areaAddress = "@#$%^"
	// Expected Output: No exception thrown
	@Test
	public void validateAreaAddressOnlySpecialCharacters() {
		try {
			deliveryAreaObj.validateAreaAddress("@#$%^");
			fail("Exception expected");
		} catch (DeliveryAreaExceptionHandler e) {
			assertEquals("Area address must contain at least one letter. Please enter a valid area address.",
					e.getMessage());
		}
	}

	// Test #: 15
	// Test Objective: To validate that an area address with only numbers
	// Inputs: areaAddress = "12345"
	// Expected Output: No exception thrown
	@Test
	public void validateAreaAddressOnlyNumbers() {
		try {
			deliveryAreaObj.validateAreaAddress("12345");
			fail("Exception expected");
		} catch (DeliveryAreaExceptionHandler e) {
			assertEquals("Area address must contain at least one letter. Please enter a valid area address.",
					e.getMessage());
		}
	}

	// Test #: 16
	// Test Objective: To validate that an area address with only numbers and
	// special characters
	// Inputs: areaAddress = "12345!!!"
	// Expected Output: No exception thrown
	@Test
	public void validateAreaAddressOnlyNumbersSpecialChar() {
		try {
			deliveryAreaObj.validateAreaAddress("12345!!!");
			fail("Exception expected");
		} catch (DeliveryAreaExceptionHandler e) {
			assertEquals("Area address must contain at least one letter. Please enter a valid area address.",
					e.getMessage());
		}
	}

}
