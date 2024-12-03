package order;

public class OrderExceptionHandler extends Exception {
	private String message;
	
	public OrderExceptionHandler(String errorMessage){
		message = errorMessage;
	}
	
	public String getMessage() {
		return message;
	}

}
