package invoice;

public class InvoiceExceptionHandler extends Exception {
    private String message;

    public InvoiceExceptionHandler(String errorMessage) {
        message = errorMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }
}