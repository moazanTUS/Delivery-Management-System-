package deliveryArea;
//push
public class DeliveryAreaExceptionHandler extends Exception {
	private String message;

	public DeliveryAreaExceptionHandler(String errorMessage) {
		message = errorMessage;
	}

	public String getMessage() {
		return message;
	}

}
