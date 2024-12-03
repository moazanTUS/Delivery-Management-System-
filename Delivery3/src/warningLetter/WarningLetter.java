package warningLetter;

import java.util.Calendar;
import java.util.Date;
import invoice.Invoice;

public class WarningLetter {
    private int warningId;
    private Invoice invoice;
    private String reminder;

    

    public WarningLetter(String reminder, Invoice invoice) {
    	this.reminder = reminder;
    	this.invoice = invoice;  	
	}
	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {

	    this.reminder = reminder;
	}



	public void setInvoice(Invoice invoice) {
	    this.invoice = invoice;

	    }
	
	public Invoice getInvoice() {
		return invoice;
		}
	
	

		
	    public int getWarningId() {
	        return warningId;
	    }

	    public void setWarningId(int warningId) {
	        this.warningId = warningId;
	    }

	    
		public boolean isCustomerPaid() {
	        return invoice.isCustomerPaid();
	        }
	        


	public boolean isTotalPaymentMadeWithin30Days() {
        return isTotalPaymentMadeWithinDays(30);
    }

    public boolean isTotalPaymentMadeWithin60Days() {
        return isTotalPaymentMadeWithinDays(60);
    }

    public boolean isTotalPaymentMadeWithin90Days() {
        return isTotalPaymentMadeWithinDays(90);
    }

    //  method to check if the total payment was made within a specified number of days
    private boolean isTotalPaymentMadeWithinDays(int days) {
        if (invoice.getInvoiceStatus().equals("PAID") || invoice.getInvoiceStatus().equals("ONGOING")) {
            return true; // Payment already made or ongoing, no warning needed
        }

        // Check if the payment is overdue
        if (invoice.getInvoiceStatus().equals("UNPAID")) {
            Date invoiceDate = invoice.getInvoiceDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(invoiceDate);
            calendar.add(Calendar.DAY_OF_YEAR, days);

            Date dueDate = calendar.getTime();
            Date currentDate = new Date(); 

            return !(currentDate.after(dueDate));
        }

        return false;
    }



		


}
