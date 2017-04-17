package demo.model;

public class Instance {

	private String instanceID;
	private STATUS status;
	private long lastHeartbeat;
	public static enum STATUS { UP, DOWN }

	public String getInstanceID() {
		return instanceID;
	}
	public void setInstanceID(String instanceID) {
		this.instanceID = instanceID;
	}
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS up) {
		this.status = up;
	}
	public long getLastHeartbeat() {
		return lastHeartbeat;
	}
	public void setLastHeartbeat(long lastHeartbeat) {
		this.lastHeartbeat = lastHeartbeat;
	}

	@Override
	public String toString() {
		return "Instance [instanceID=" + instanceID + ", status=" + status + ", lastHeartbeat=" + lastHeartbeat + "]";
	}
}
