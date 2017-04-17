package demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import demo.model.Instance;
import demo.model.InstanceAssignment;
import demo.model.Packet;
import demo.service.InstanceAssignmentService;
import demo.service.InstanceService;
import demo.service.StagingService;
import demo.service.TelemetryService;

public class ValidationThread extends Thread{

	private InstanceService instanceService;
	private InstanceAssignmentService instanceAssignmentService;
	private StagingService stagingService;
	private TelemetryService telemetryService;
	private String instanceID;

	public  ValidationThread(String instanceID) throws IOException {
		instanceService = new InstanceService();
		instanceAssignmentService = new InstanceAssignmentService();
		stagingService = new StagingService();
		telemetryService = new TelemetryService();
		this.instanceID = instanceID;
	}

	@Override
	public void run(){
		while(true){
			// 1.Fetch packets from staging.
			ArrayList<Packet> sortedPacketsList = fetchPackets();
			ArrayList<Packet> validatedPacketsList;

			if (sortedPacketsList == null){
				continue;
			}

			// 2.Validate packets
			validatedPacketsList = validatePackets(sortedPacketsList);

			if (validatedPacketsList == null){
				continue;
			}
			// 3.Insert packets
			insertValidatedPackets(validatedPacketsList);
		}
	}

	private ArrayList<Packet> fetchPackets(){
		InstanceAssignment instanceAssignment = null;
		String clientID = null;
		ArrayList<Packet> packets = null;

		// 1. Select and mark a pending clients.
		instanceAssignment = instanceAssignmentService.getPendingInstanceAssignment();

		if (instanceAssignment == null){
			return null;
		}
		clientID = instanceAssignment.getClientID();

		// 2. Select all packets which belong above client.
		packets = stagingService.getPacketsByClientID(clientID);

		// 3. Sort packets with transmission ID.
		Comparator<Packet> c = new Comparator<Packet>() {
			public int compare(Packet o1, Packet o2) {
				if (o1.getTranmissionID() > o2.getTranmissionID()){
					return 1;
				}
				return -1;
			}
		};
		packets.sort(c);
		return packets;
	}

	private ArrayList<Packet> validatePackets(ArrayList<Packet> SortedpacketsList){
		ArrayList<Packet> validatedPacketsList = null;

		// Do some validation, here is fake.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		validatedPacketsList = SortedpacketsList;
		return validatedPacketsList;
	}

	private int insertValidatedPackets(ArrayList<Packet> validatedPacketsList){

		// 1. Check current instance status.
		Instance instance = instanceService.getInstance(instanceID);
		if (instance.getStatus().equals(Instance.STATUS.DOWN)){
			instanceAssignmentService.resetInstanceAssignmentByInstanceID(instanceID);
			Util.registerInstance(instanceID);
			return 0;
		}

		// 2. Insert Teletemtry table;
		telemetryService.insertPacketsToTelemetry(validatedPacketsList);

		// 3. Delete Staging table;
		stagingService.deletePackets(validatedPacketsList);
		return 0;
	}
}
