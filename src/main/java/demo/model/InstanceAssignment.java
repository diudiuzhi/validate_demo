package demo.model;

public class InstanceAssignment {

	private String clientID;
	private String instanceID;
	private STATUS status;
	public static enum STATUS { PENDING, FETCHED, FINISHED}

	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getInstanceID() {
		return instanceID;
	}
	public void setInstanceID(String instanceID) {
		this.instanceID = instanceID;
	}
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS pending) {
		this.status = pending;
	}
}
