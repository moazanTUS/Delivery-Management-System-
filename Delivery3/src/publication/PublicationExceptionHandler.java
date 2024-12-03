package publication;

public class PublicationExceptionHandler extends Exception{
	private String message;
	
	public PublicationExceptionHandler(String errorMessage){
		message = errorMessage;
	}
	
	public String getMessage() {
		return message;
	}
}
