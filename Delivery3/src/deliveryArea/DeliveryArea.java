package deliveryArea;

import deliveryArea.DeliveryAreaExceptionHandler;

public class DeliveryArea {
	private String areaId;
	private String areaAddress;
	private int numOfCustomer;

	public DeliveryArea() {
		this.areaId = null;
		this.areaAddress = null;
		this.numOfCustomer = 0;
	}


	public DeliveryArea(String areaAddress, int numOfCustomer) throws DeliveryAreaExceptionHandler {
		try {
			validateAreaAddress(areaAddress);
			validateNumOfCustomer(numOfCustomer);
		} catch (DeliveryAreaExceptionHandler e) {
			throw e;
		}
		this.areaId = areaId;
		this.areaAddress = areaAddress;
		this.numOfCustomer = numOfCustomer;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaAddress() {
		return areaAddress;
	}

	public void setAreaAddress(String areaAddress) throws DeliveryAreaExceptionHandler {
		validateAreaAddress(areaAddress);
		this.areaAddress = areaAddress;
	}

	public int getNumOfCustomer() {
		return numOfCustomer;
	}

	public void setNumOfCustomer(int numOfCustomer) throws DeliveryAreaExceptionHandler {
		validateNumOfCustomer(numOfCustomer);
		this.numOfCustomer = numOfCustomer;
	}

	public void validateNumOfCustomer(int numOfCustomer) throws DeliveryAreaExceptionHandler {

		if (numOfCustomer < 0) {
			throw new DeliveryAreaExceptionHandler("Number of customers cannot be negative.");
		}

		int maxCustomersPerArea = 100;
		if (numOfCustomer > maxCustomersPerArea) {
			throw new DeliveryAreaExceptionHandler("Number of customers exceeds the maximum allowed for an area.");
		}

	}

	public void validateAreaAddress(String areaAddress2) throws DeliveryAreaExceptionHandler {
		if (areaAddress2 == null || areaAddress2.trim().isEmpty())
			throw new DeliveryAreaExceptionHandler("Area address is empty. Please enter a valid area address.");
		if(!areaAddress2.matches(".*[a-zA-Z]+.*"))
			throw new DeliveryAreaExceptionHandler("Area address must contain at least one letter. Please enter a valid area address.");

	}

}
