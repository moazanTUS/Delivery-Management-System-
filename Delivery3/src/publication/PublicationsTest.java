package publication;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PublicationsTest {

    // Test #: 1
    // Test Objective: To catch a null Publication Name
    // Inputs: pubName = null
    // Expected Output: Exception Message: "Publication Name is empty. Please enter Publication Name."
    @Test
    public void testValidateNameNull() {
        try {
            Publication.validateName(null);
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication Name is empty. Please enter Publication Name.", e.getMessage());
        }
    }

    // Test #: 2
    // Test Objective: To catch an empty Publication Name
    // Inputs: pubName = ""
    // Expected Output: Exception Message: "Publication Name is empty. Please enter Publication Name."
    @Test
    public void testValidateNameEmpty() {
        try {
            Publication.validateName("");
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication Name is empty. Please enter Publication Name.", e.getMessage());
        }
    }

    // Test #: 3
    // Test Objective: To catch a Publication Name with only numbers
    // Inputs: pubName = "12345"
    // Expected Output: Exception Message: "Publication Name cannot contain only numbers."
    @Test
    public void testValidateNameOnlyNumbers() {
        try {
            Publication.validateName("12345");
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication Name cannot contain only numbers.", e.getMessage());
        }
    }

    // Test #: 4
    // Test Objective: To catch an invalid Publication Name length (below boundary)
    // Inputs: pubName = "AB"
    // Expected Output: Exception Message: "Publication Name is too short. Minimum length is 3 characters."
    @Test
    public void testValidateNameLengthInvalidLowerBoundary() {
        try {
            Publication.validateName("AB");
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication Name is too short. Minimum length is 3 characters.", e.getMessage());
        }
    }

    // Test #: 5
    // Test Objective: To catch an invalid Publication Name length (above boundary)
    // Inputs: pubName = "This is a very long publication name exceeding fifty characters"
    // Expected Output: Exception Message: "Publication Name is too long. Maximum length is 50 characters."
    @Test
    public void testValidateNameLengthInvalidUpperBoundary() {
        try {
            Publication.validateName("This is a very long publication name exceeding fifty characters");
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication Name is too long. Maximum length is 50 characters.", e.getMessage());
        }
    }

    // Test #: 6
    // Test Objective: To catch a Publication Name with invalid characters
    // Inputs: pubName = "John@Doe"
    // Expected Output: Exception Message: "Publication Name contains invalid characters. Only letters, spaces, and apostrophes are allowed."
    @Test
    public void testValidateNameInvalidCharacters() {
        try {
            Publication.validateName("John@Doe");
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication Name contains invalid characters. Only letters, spaces, and apostrophes are allowed.", e.getMessage());
        }
    }

    // Test #: 7
    // Test Objective: To validate a correct Publication Name
    // Inputs: pubName = "Athlone Journal"
    // Expected Output: No exception thrown
    @Test
    public void testValidateNameValid() {
        try {
            Publication.validateName("Athlone Journal");
        } catch (PublicationExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    // Test #: 8
    // Test Objective: To catch a Publication Price that is zero
    // Inputs: pubPrice = 0.0
    // Expected Output: Exception Message: "Publication price must be greater than zero."
    @Test
    public void testValidatePriceZero() {
        try {
            Publication.validatePrice(0.0);
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication price must be greater than zero.", e.getMessage());
        }
    }

    // Test #: 9
    // Test Objective: To catch an invalid Publication Price (below boundary)
    // Inputs: pubPrice = -5.0
    // Expected Output: Exception Message: "Publication price must be greater than zero."
    @Test
    public void testValidatePriceInvalidLowerBoundary() {
        try {
            Publication.validatePrice(-5.0);
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication price must be greater than zero.", e.getMessage());
        }
    }

    // Test #: 10
    // Test Objective: To catch an invalid Publication Price (above boundary)
    // Inputs: pubPrice = 501.0
    // Expected Output: Exception Message: "Publication price exceeds the maximum allowed value of 10,000."
    @Test
    public void testValidatePriceInvalidUpperBoundary() {
        try {
            Publication.validatePrice(501.0);
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication price exceeds the maximum allowed value of 500.", e.getMessage());
        }
    }

    // Test #: 11
    // Test Objective: To validate a correct Publication Price
    // Inputs: pubPrice = 500.0
    // Expected Output: No exception thrown
    @Test
    public void testValidatePriceValid() {
        try {
            Publication.validatePrice(500.0);
        } catch (PublicationExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    // Test #: 12
    // Test Objective: To catch a quantity of zero
    // Inputs: pubQty = 0
    // Expected Output: Exception Message: "Publication quantity must be greater than zero."
    @Test
    public void testValidateQuantityZero() {
        try {
            Publication.validateQuantity(0);
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication quantity must be greater than zero.", e.getMessage());
        }
    }

    // Test #: 13
    // Test Objective: To catch an invalid Publication Quantity (below boundary)
    // Inputs: pubQty = -10
    // Expected Output: Exception Message: "Publication quantity must be greater than zero."
    @Test
    public void testValidateQuantityInvalidLowerBoundary() {
        try {
            Publication.validateQuantity(-10);
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication quantity must be greater than zero.", e.getMessage());
        }
    }

    // Test #: 14
    // Test Objective: To catch an invalid Publication Quantity (above boundary)
    // Inputs: pubQty = 1001
    // Expected Output: Exception Message: "Publication quantity exceeds the maximum allowed value of 1000."
    @Test
    public void testValidateQuantityInvalidUpperBoundary() {
        try {
            Publication.validateQuantity(1001);
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication quantity exceeds the maximum allowed value of 1000.", e.getMessage());
        }
    }

    // Test #: 15
    // Test Objective: To validate a correct quantity
    // Inputs: pubQty = 500
    // Expected Output: No exception thrown
    @Test
    public void testValidateQuantityValid() {
        try {
            Publication.validateQuantity(500);
        } catch (PublicationExceptionHandler e) {
            fail("Exception not expected");
        }
    }

    // Test #: 16
    // Test Objective: To catch a null publication type
    // Inputs: pubType = null
    // Expected Output: Exception Message: "Publication type is empty. Please enter a valid publication type."
    @Test
    public void testValidateTypeNull() {
        try {
            Publication.validateType(null);
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication type is empty. Please enter a valid publication type.", e.getMessage());
        }
    }

    // Test #: 17
    // Test Objective: To catch an empty publication type
    // Inputs: pubType = ""
    // Expected Output: Exception Message: "Publication type is empty. Please enter a valid publication type."
    @Test
    public void testValidateTypeEmpty() {
        try {
            Publication.validateType("");
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication type is empty. Please enter a valid publication type.", e.getMessage());
        }
    }

    // Test #: 18
    // Test Objective: To catch a publication type with numbers
    // Inputs: pubType = "Midlands123"
    // Expected Output: Exception Message: "Publication type cannot contain numbers."
    @Test
    public void testValidateTypeWithNumbers() {
        try {
            Publication.validateType("Midlands123");
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication type cannot contain numbers.", e.getMessage());
        }
    }

    // Test #: 19
    // Test Objective: To catch a publication type with special characters
    // Inputs: pubType = "Midlands@"
    // Expected Output: Exception Message: "Publication type contains invalid characters. Only letters are allowed."
    @Test
    public void testValidateTypeWithSpecialCharacters() {
        try {
            Publication.validateType("Midlands@");
            fail("Exception expected");
        } catch (PublicationExceptionHandler e) {
            assertEquals("Publication type contains invalid characters. Only letters are allowed.", e.getMessage());
        }
    }

    // Test #: 20
    // Test Objective: To validate the creation of a valid Publication object
    // Inputs: pubName = "Daily News", pubPrice = 5.0, pubQty = 100, pubType = "Daily"
    // Expected Output: No exception thrown
    @Test
    public void testCreateValidPublication() {
        try {
            Publication pub = new Publication("Daily News", 5.0, 100, "Daily");
            assertEquals("Daily News", pub.getPubName());
            assertEquals(5.0, pub.getPubPrice());
            assertEquals(100, pub.getPubQty());
            assertEquals("Daily", pub.getPubType());
        } catch (PublicationExceptionHandler e) {
            fail("Exception not expected");
        }
    }

}
