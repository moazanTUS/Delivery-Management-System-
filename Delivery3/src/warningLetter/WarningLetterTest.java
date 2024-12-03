package warningLetter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import invoice.Invoice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

public class WarningLetterTest {

    private WarningLetter warningLetter;
    private MockInvoice mockInvoice;

    public static class MockInvoice extends Invoice {
        public MockInvoice(int invoiceId, String custId, String orderId, Date invoiceDate, String invoiceStatus) {
            super(invoiceId, custId, orderId, 0.0, invoiceDate, invoiceStatus, 0.0);
        }
    }

    @BeforeEach
    void setup() {
        mockInvoice = new MockInvoice(101, "C001", "O001", new Date(), "UNPAID");
        warningLetter = new WarningLetter("Payment overdue", mockInvoice);
    }

    private MockInvoice createMockInvoice(int daysAgo, String status) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo);
        return new MockInvoice(101, "C001", "O001", calendar.getTime(), status);
    }

    // Test #: 1
    // Test Objective: Verify that unpaid invoices within 30 days return true.
    // Inputs: Invoice date = 10 days ago, Status = "UNPAID"
    // Expected Output: true
    @Test
    void testUnpaidWithin30DaysReturnsTrue() {
        mockInvoice = createMockInvoice(10, "UNPAID");
        warningLetter.setInvoice(mockInvoice);
        assertTrue(warningLetter.isTotalPaymentMadeWithin30Days());
    }

    // Test #: 2
    // Test Objective: Verify that unpaid invoices beyond 30 days return false.
    // Inputs: Invoice date = 40 days ago, Status = "UNPAID"
    // Expected Output: false
    @Test
    void testUnpaidBeyond30DaysReturnsFalse() {
        mockInvoice = createMockInvoice(40, "UNPAID");
        warningLetter.setInvoice(mockInvoice);
        assertFalse(warningLetter.isTotalPaymentMadeWithin30Days());
    }

    // Test #: 3
    // Test Objective: Verify that unpaid invoices exactly 30 days old return true.
    // Inputs: Invoice date = 30 days ago, Status = "UNPAID"
    // Expected Output: true
    @Test
    void testUnpaidExactly30DaysReturnsTrue() {
        mockInvoice = createMockInvoice(30, "UNPAID");
        warningLetter.setInvoice(mockInvoice);
        assertTrue(warningLetter.isTotalPaymentMadeWithin30Days());
    }

    // Test #: 4
    // Test Objective: Verify that unpaid invoices within 60 days return true.
    // Inputs: Invoice date = 50 days ago, Status = "UNPAID"
    // Expected Output: true
    @Test
    void testUnpaidWithin60DaysReturnsTrue() {
        mockInvoice = createMockInvoice(50, "UNPAID");
        warningLetter.setInvoice(mockInvoice);
        assertTrue(warningLetter.isTotalPaymentMadeWithin60Days());
    }

    // Test #: 5
    // Test Objective: Verify that unpaid invoices beyond 60 days return false.
    // Inputs: Invoice date = 70 days ago, Status = "UNPAID"
    // Expected Output: false
    @Test
    void testUnpaidBeyond60DaysReturnsFalse() {
        mockInvoice = createMockInvoice(70, "UNPAID");
        warningLetter.setInvoice(mockInvoice);
        assertFalse(warningLetter.isTotalPaymentMadeWithin60Days());
    }

    // Test #: 6
    // Test Objective: Verify that unpaid invoices within 90 days return true.
    // Inputs: Invoice date = 80 days ago, Status = "UNPAID"
    // Expected Output: true
    @Test
    void testUnpaidWithin90DaysReturnsTrue() {
        mockInvoice = createMockInvoice(80, "UNPAID");
        warningLetter.setInvoice(mockInvoice);
        assertTrue(warningLetter.isTotalPaymentMadeWithin90Days());
    }

    // Test #: 7
    // Test Objective: Verify that unpaid invoices beyond 90 days return false.
    // Inputs: Invoice date = 100 days ago, Status = "UNPAID"
    // Expected Output: false
    @Test
    void testUnpaidBeyond90DaysReturnsFalse() {
        mockInvoice = createMockInvoice(100, "UNPAID");
        warningLetter.setInvoice(mockInvoice);
        assertFalse(warningLetter.isTotalPaymentMadeWithin90Days());
    }

    // Test #: 8
    // Test Objective: Verify that paid invoices always return true for any days.
    // Inputs: Invoice date = 100 days ago, Status = "PAID"
    // Expected Output: true
    @Test
    void testPaidWithinAnyDaysReturnsTrue() {
        mockInvoice = createMockInvoice(100, "PAID");
        warningLetter.setInvoice(mockInvoice);
        assertTrue(warningLetter.isTotalPaymentMadeWithin30Days());
        assertTrue(warningLetter.isTotalPaymentMadeWithin60Days());
        assertTrue(warningLetter.isTotalPaymentMadeWithin90Days());
    }

    // Test #: 9
    // Test Objective: Verify that ongoing invoices always return true for any days.
    // Inputs: Invoice date = 100 days ago, Status = "ONGOING"
    // Expected Output: true
    @Test
    void testOngoingWithinAnyDaysReturnsTrue() {
        mockInvoice = createMockInvoice(100, "ONGOING");
        warningLetter.setInvoice(mockInvoice);
        assertTrue(warningLetter.isTotalPaymentMadeWithin30Days());
        assertTrue(warningLetter.isTotalPaymentMadeWithin60Days());
        assertTrue(warningLetter.isTotalPaymentMadeWithin90Days());
    }

    // Test #: 10
    // Test Objective: Verify that invalid invoice status always returns false.
    // Inputs: Invoice date = 10 days ago, Status = "INVALID"
    // Expected Output: false
    @Test
    void testInvalidStatusReturnsFalse() {
        mockInvoice = createMockInvoice(10, "INVALID");
        warningLetter.setInvoice(mockInvoice);
        assertFalse(warningLetter.isTotalPaymentMadeWithin30Days());
        assertFalse(warningLetter.isTotalPaymentMadeWithin60Days());
        assertFalse(warningLetter.isTotalPaymentMadeWithin90Days());
    }


}
