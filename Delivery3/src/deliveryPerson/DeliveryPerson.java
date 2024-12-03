package deliveryPerson;

//1
import deliveryPerson.DeliveryPersonExceptionHandler;

public class DeliveryPerson {
	private String driverId;
	private String driverName;
	private long driverPhone;
	private String areaId;

	public DeliveryPerson() {
		this.driverId = null;
		this.driverName = null;
		this.driverPhone = 0L;
	}

	public DeliveryPerson(String driverName, long driverPhone, String areaId)
			throws DeliveryPersonExceptionHandler {
		try {
			validateName(driverName);
			validatePhoneNumber(driverPhone);
		} catch (DeliveryPersonExceptionHandler e) {
			throw e;
		}
		this.driverName = driverName;
		this.driverPhone = driverPhone;
		this.areaId = areaId;
	}

	public String getDriverId() {
		return this.driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return this.driverName;
	}

	public void setDriverName(String driverName) throws DeliveryPersonExceptionHandler {
		validateName(driverName);
		this.driverName = driverName;
	}

	public long getDriverPhone() {
		return this.driverPhone;
	}

	public void setDriverPhone(long driverPhone) throws DeliveryPersonExceptionHandler {
		validatePhoneNumber(driverPhone);
		this.driverPhone = driverPhone;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void validatePhoneNumber(long driverPhone) throws DeliveryPersonExceptionHandler {
		String driverPhoneStr = String.valueOf(driverPhone);
		if (driverPhone == 0 || driverPhoneStr.isEmpty())
			throw new DeliveryPersonExceptionHandler(
					"Delivery Person Phone Number is empty. Please enter Delivery Person Phone Number.");
		if (!driverPhoneStr.matches("[1-9]\\d{8}")) {
		    throw new DeliveryPersonExceptionHandler("Delivery Person Phone Number should be exactly 9 digits and not start with 0.");
		}

	}


	public void validateName(String driverName1) throws DeliveryPersonExceptionHandler {
		String driverName = driverName1.trim();
		if (driverName.isBlank() || driverName.isEmpty())
			throw new DeliveryPersonExceptionHandler(
					"Delivery Person Name is empty. Please enter Delivery Person Name.");
		if (!driverName.matches("[a-zA-Z\\s']+")) {
			throw new DeliveryPersonExceptionHandler(
					"Delivery Person Name contains invalid characters. Only letters, spaces, and apostrophes are allowed.");
		}

	}

}
