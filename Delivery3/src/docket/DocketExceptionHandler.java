package docket;

public class DocketExceptionHandler extends Exception {
	private String message;
	
	public DocketExceptionHandler(String errorMessage){
		message = errorMessage;
	}
	
	public String getMessage() {
		return message;
	}

}
