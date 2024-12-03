package customer;

public class CustomerExceptionHandler extends Exception{
	private String message;
	
	public CustomerExceptionHandler(String errorMessage){
		message = errorMessage;
	}
	
	public String getMessage() {
		return message;
	}
}
