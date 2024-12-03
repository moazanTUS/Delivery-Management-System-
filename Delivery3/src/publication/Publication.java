package publication;

import customer.CustomerExceptionHandler;

public class Publication {

	private String pubId;
	private String pubName;
	private double pubPrice;
	private int pubQty;
	private String pubType;

	public Publication(String pubName, double pubPrice, int pubQty, String pubType) throws PublicationExceptionHandler {
		try {
			validateName(pubName);
			validatePrice(pubPrice);
			validateQuantity(pubQty);
			validateType(pubType);
		}
		catch (PublicationExceptionHandler e) {
			throw e;
		}
		this.pubName = pubName;
		this.pubPrice = pubPrice;
		this.pubQty = pubQty;
		this.pubType = pubType;
	}

	public Publication() {
		this.pubId = null;
		this.pubName = null;
		this.pubPrice = 0;
		this.pubQty = 0;
		this.pubType = null;
	}
	
	public String getPubId() {
		return pubId;
	}
	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
	public String getPubName() {
		return pubName;
	}
	public void setPubName(String pubName) {
		this.pubName = pubName;
	}
	
	public double getPubPrice() {
		return pubPrice;
	}
	public void setPubPrice(double pubPrice) {
		this.pubPrice = pubPrice;
	}
	public int getPubQty() {
		return pubQty;
	}
	public void setPubQty(int pubQty) {
		this.pubQty = pubQty;
	}
	
	public String getPubType() {
		return pubType;
	}

	public void setPubType(String pubType) {
		this.pubType = pubType;
	}

	public static void validateName(String pubName) throws PublicationExceptionHandler {
	    if (pubName == null || pubName.isBlank()) {
	        throw new PublicationExceptionHandler("Publication Name is empty. Please enter Publication Name.");
	    }
	    if (pubName.matches("\\d+")) {
	        throw new PublicationExceptionHandler("Publication Name cannot contain only numbers.");
	    }
	    if (pubName.length() < 3) {
	        throw new PublicationExceptionHandler("Publication Name is too short. Minimum length is 3 characters.");
	    }
	    if (pubName.length() > 50) {
	        throw new PublicationExceptionHandler("Publication Name is too long. Maximum length is 50 characters.");
	    }
	    if (!pubName.matches("[a-zA-Z\\s']+")) {
	        throw new PublicationExceptionHandler("Publication Name contains invalid characters. Only letters, spaces, and apostrophes are allowed.");
	    }
	}

	
	public static void validatePrice(double pubPrice) throws PublicationExceptionHandler {
	    if (pubPrice <= 0) {
	        throw new PublicationExceptionHandler("Publication price must be greater than zero.");
	    }
	    if (pubPrice > 500) { 
	        throw new PublicationExceptionHandler("Publication price exceeds the maximum allowed value of 500.");
	    }
	}

	
	public static void validateQuantity(int pubQty) throws PublicationExceptionHandler {
	    if (pubQty <= 0) {
	        throw new PublicationExceptionHandler("Publication quantity must be greater than zero.");
	    }
	    if (pubQty > 1000) { 
	        throw new PublicationExceptionHandler("Publication quantity exceeds the maximum allowed value of 1000.");
	    }
	}


	public static void validateType(String pubType) throws PublicationExceptionHandler {
	    if (pubType == null || pubType.isBlank()) {
	        throw new PublicationExceptionHandler("Publication type is empty. Please enter a valid publication type.");
	    }

	    if (pubType.matches(".*\\d.*")) {
	        throw new PublicationExceptionHandler("Publication type cannot contain numbers.");
	    }

	    if (pubType.matches(".*[@#$%^&*()+=!{}\\[\\]\\\\|?<>`~].*")) {
	        throw new PublicationExceptionHandler("Publication type contains invalid characters. Only letters are allowed.");
	    }

	    if (pubType.length() < 3) {
	        throw new PublicationExceptionHandler("Publication type is too short. Minimum length is 3 characters.");
	    }

	    if (pubType.length() > 15) {
	        throw new PublicationExceptionHandler("Publication type is too long. Maximum length allowed is 15 characters.");
	    }

	    if (!(pubType.equalsIgnoreCase("daily") || pubType.equalsIgnoreCase("weekly") || pubType.equalsIgnoreCase("monthly"))) {
	        throw new PublicationExceptionHandler("Invalid publication type. Allowed values are 'Daily', 'Weekly', or 'Monthly'.");
	    }
	}

}
