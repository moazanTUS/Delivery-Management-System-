package deliveryPerson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.jupiter.api.Test;

public class DeliveryPersonTest {
	DeliveryPerson deliveryPersonObj = new DeliveryPerson();

	// Test #: 1
	// Test Objective: Create a valid DeliveryPerson object
	// Inputs: driverName = "Kanye East", driverPhone = 878787878, driverArea = "5"
	// Expected Output: No exception thrown, object created successfully
	@Test
	public void testCreateDeliveryPerson() {
		try {
			DeliveryPerson deliveryPersonObj = new DeliveryPerson("Kanye East", 878787878, "5");
		} catch (DeliveryPersonExceptionHandler e) {
			fail("Exception not expected");
		}
	}

	// Test #: 2
	// Test Objective: Attempt to create a DeliveryPerson object with an invalid
	// phone number
	// Inputs: driverName = "Kanye East", driverPhone = 78787878 (8 digits),
	// driverArea = "5"
	// Expected Output: Exception thrown with message "Delivery Person Phone Number should be exactly 9 digits and not start with 0."
	@Test
	public void testCreateInvalidDeliveryPerson() {
		try {
			DeliveryPerson deliveryPersonObj = new DeliveryPerson("Kanye East", 78787878, "5");
			fail("Exception expected");
		} catch (DeliveryPersonExceptionHandler e) {
			assertEquals("Delivery Person Phone Number should be exactly 9 digits and not start with 0.",
					e.getMessage());
		}
	}

	// Test #: 3
	// Test Objective: Validate a correct phone number
	// Inputs: driverPhone = 874567867
	// Expected Output: No exception thrown
	@Test
	public void validatePhoneNumberValid() {
		try {
			deliveryPersonObj.validatePhoneNumber(874567867);
		} catch (DeliveryPersonExceptionHandler e) {
			fail("No exception expected for valid phone number.");
		}
	}

	// Test #: 4
	// Test Objective: Validate phone number when set to zero (empty)
	// Inputs: driverPhone = 0L
	// Expected Output: Exception thrown with message "Delivery Person Phone Number
	// is empty."
	@Test
	public void validatePhoneNumberEmpty() {
		try {
			deliveryPersonObj.validatePhoneNumber(0L);
			fail("Exception expected");
		} catch (DeliveryPersonExceptionHandler e) {
			assertEquals("Delivery Person Phone Number is empty. Please enter Delivery Person Phone Number.",
					e.getMessage());
		}
	}

	// Test #: 5
	// Test Objective: Validate phone number that is too short (8 digits)
	// Inputs: driverPhone = 87878787
	// Expected Output: Exception thrown with message "Delivery Person Phone Number
	// too short."
	@Test
	public void validatePhoneNumberTooShort() {
		try {
			deliveryPersonObj.validatePhoneNumber(87878787);
			fail("Exception expected");
		} catch (DeliveryPersonExceptionHandler e) {
			assertEquals("Delivery Person Phone Number should be exactly 9 digits and not start with 0.",
					e.getMessage());
		}
	}

	// Test #: 6
	// Test Objective: Validate maximum boundary phone number (9 digits)
	// Inputs: driverPhone = 999999999
	// Expected Output: No exception thrown
	@Test
	public void validatePhoneNumberMaxBoundary() {
		try {
			deliveryPersonObj.validatePhoneNumber(999999999);
		} catch (DeliveryPersonExceptionHandler e) {
			fail("No exception expected for maximum valid 9-digit phone number.");
		}
	}

	// Test #: 7
	// Test Objective: Validate phone number that is too long (10 digits)
	// Inputs: driverPhone = 1234567890L
	// Expected Output: Exception thrown with message "Delivery Person Phone Number
	// too long."
	@Test
	public void validatePhoneNumberTooLong() {
		try {
			deliveryPersonObj.validatePhoneNumber(1234567890L);
			fail("Exception expected for phone number with more than 9 digits.");
		} catch (DeliveryPersonExceptionHandler e) {
			assertEquals("Delivery Person Phone Number should be exactly 9 digits and not start with 0.",
					e.getMessage());
		}
	}

	// Test #: 8
	// Test Objective: Validate correct delivery person name
	// Inputs: driverName = "John O'Connor"
	// Expected Output: No exception thrown
	@Test
	public void validateNameValid() {
		try {
			deliveryPersonObj.validateName("John O'Connor");
		} catch (DeliveryPersonExceptionHandler e) {
			fail("No exception expected for valid name.");
		}
	}

	// Test #: 9
	// Test Objective: Validate name with leading and trailing spaces
	// Inputs: driverName = " John Doe "
	// Expected Output: No exception thrown (spaces trimmed)
	@Test
	public void validateNameWithLeadingTrailingSpaces() {
		try {
			deliveryPersonObj.validateName("  John Doe  ");
		} catch (DeliveryPersonExceptionHandler e) {
			fail("No exception expected for valid name.");
		}
	}

	// Test #: 10
	// Test Objective: Validate empty name input
	// Inputs: driverName = ""
	// Expected Output: Exception thrown with message "Delivery Person Name is
	// empty."
	@Test
	public void validateNameEmpty() {
		try {
			deliveryPersonObj.validateName("");
			fail("Exception expected for empty name.");
		} catch (DeliveryPersonExceptionHandler e) {
			assertEquals("Delivery Person Name is empty. Please enter Delivery Person Name.", e.getMessage());
		}
	}

	// Test #: 11
	// Test Objective: Validate blank name (only spaces)
	// Inputs: driverName = " "
	// Expected Output: Exception thrown with message "Delivery Person Name is
	// empty."
	@Test
	public void validateNameBlank() {
		try {
			deliveryPersonObj.validateName("   ");
			fail("Exception expected for blank name.");
		} catch (DeliveryPersonExceptionHandler e) {
			assertEquals("Delivery Person Name is empty. Please enter Delivery Person Name.", e.getMessage());
		}
	}

	// Test #: 12
	// Test Objective: Validate name with invalid characters (numbers and symbols)
	// Inputs: driverName = "John123!"
	// Expected Output: Exception thrown with message about invalid characters
	@Test
	public void validateNameInvalidCharacters() {
		try {
			deliveryPersonObj.validateName("John123!");
			fail("Exception expected for name with invalid characters.");
		} catch (DeliveryPersonExceptionHandler e) {
			assertEquals(
					"Delivery Person Name contains invalid characters. Only letters, spaces, and apostrophes are allowed.",
					e.getMessage());
		}
	}

	// Test #: 13
	// Test Objective: Validate name with unsupported special characters (hyphen)
	// Inputs: driverName = "Anna-Marie"
	// Expected Output: Exception thrown about invalid characters
	@Test
	public void validateNameWithSpecialCharacters() {
		try {
			deliveryPersonObj.validateName("Anna-Marie");
			fail("Exception expected for name with unsupported special characters.");
		} catch (DeliveryPersonExceptionHandler e) {
			assertEquals(
					"Delivery Person Name contains invalid characters. Only letters, spaces, and apostrophes are allowed.",
					e.getMessage());
		}
	}

	// Test #: 14
	// Test Objective: Validate single character name
	// Inputs: driverName = "A"
	// Expected Output: No exception thrown
	@Test
	public void validateNameSingleCharacter() {
		try {
			deliveryPersonObj.validateName("A");
		} catch (DeliveryPersonExceptionHandler e) {
			fail("No exception expected for single character name.");
		}
	}

	// Test #: 15
	// Test Objective: Validate name with multiple apostrophes
	// Inputs: driverName = "O'Neil O'Connor"
	// Expected Output: No exception thrown
	@Test
	public void validateNameMultipleApostrophes() {
		try {
			deliveryPersonObj.validateName("O'Neil O'Connor");
		} catch (DeliveryPersonExceptionHandler e) {
			fail("No exception expected for name with multiple apostrophes.");
		}
	}

	// Test #: 16
	// Test Objective: Validate maximum length name (50 characters)
	// Inputs: driverName = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
	// Expected Output: No exception thrown if within system constraints
	@Test
	public void validateNameMaximumLength() {
		try {
			deliveryPersonObj.validateName("A".repeat(50));
		} catch (DeliveryPersonExceptionHandler e) {
			fail("No exception expected for maximum length name.");
		}
	}

	// Test #: 17
	// Test Objective: Attempt to create a DeliveryPerson object with an invalid name
	// Inputs: driverName = "656543", driverPhone = 879685968 , driverArea = "5"
	// Expected Output: Exception thrown with message "Delivery Person Name contains invalid characters. Only letters, spaces, and apostrophes are allowed."
	@Test
	public void testCreateInvalidDeliveryPersonName() {
		try {
			DeliveryPerson deliveryPersonObj = new DeliveryPerson("656543", 879685968, "5");
			fail("Exception expected");
		} catch (DeliveryPersonExceptionHandler e) {
			assertEquals(
					"Delivery Person Name contains invalid characters. Only letters, spaces, and apostrophes are allowed.",
					e.getMessage());
		}
	}

}
