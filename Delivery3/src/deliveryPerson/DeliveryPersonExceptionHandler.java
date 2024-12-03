package deliveryPerson;

public class DeliveryPersonExceptionHandler extends Exception {
	private String message;

	public DeliveryPersonExceptionHandler(String errorMessage) {
		message = errorMessage;
	}

	public String getMessage() {
		return message;
	}

}
