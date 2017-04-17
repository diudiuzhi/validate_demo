package demo;

import java.io.IOException;
import java.util.ArrayList;

import demo.model.Instance;
import demo.service.InstanceAssignmentService;
import demo.service.InstanceService;

public class HeartbeatThread extends Thread{
	private InstanceService instanceService;
	private InstanceAssignmentService instanceAssignmentService;
	private String instanceID;
	private final long intervalTime = 10;

	public  HeartbeatThread(String instanceID) throws IOException {
		// Register instance in database.
		instanceService = new InstanceService();
		instanceAssignmentService = new InstanceAssignmentService();
		this.instanceID = instanceID;
	}

	@Override
	public void run(){
		while(true){
			long o_time = System.currentTimeMillis();

			//1.Update itself heartbeat status to db.
			syncHeartbeatStatus();

			//2.Check the instance of cluster heartbeat status.
			checkClusterInstanceStatus();

			//3.Ensure run per 10 seconds.
			long c_time = System.currentTimeMillis();
			try {
				Thread.sleep( intervalTime + o_time - c_time );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private int syncHeartbeatStatus(){
		try{
			instanceService.updateInstanceLastHeartbeat(instanceID);
		}
		catch (Exception e){
			return -1;
		}
		return 0;
	}

	private int checkClusterInstanceStatus(){
		try{
			ArrayList<Instance> instances = instanceService.getInstances();
			for (Instance instance : instances){
				long currentTime = System.currentTimeMillis();
				long lastTimestamp = instance.getLastHeartbeat();
				String instanceID = instance.getInstanceID();

				if (currentTime - lastTimestamp > 15) {
					instanceService.updateInstanceStatus(instanceID, Instance.STATUS.DOWN);
					instanceAssignmentService.resetInstanceAssignmentByInstanceID(instanceID);
				}
			}
		}
		catch (Exception e){
			return -1;
		}
		return 0;
	}

}
