package demo;

import java.util.ArrayList;
import java.util.Comparator;

public class ValidateJob extends Thread{
	private int jobId;
	private ArrayList<Package> unvalidatedList;
	
	
	public ValidateJob(int jobId, ArrayList<Package> unvalidatedList){
		this.jobId = jobId;
		this.unvalidatedList = unvalidatedList;
	}
	
	public void run(){
		System.out.println("Telemetry validate process " + jobId + " started");
		
		// Find packages in List which clientId % VALIDATE_JOB_NUM == jobId
		ArrayList<Package> sortedList = new ArrayList<Package>();
			
		for(Package p: unvalidatedList){
			if (p.getClientId() % Main.VALIDATE_JOB_NUM == jobId){
				sortedList.add(p);
			}
		}
			
		// Sort packages and insert to db
		Comparator<Package> c = new Comparator<Package>(){
			public int compare(Package p1, Package p2) {
				// TODO Auto-generated method stub
				if(p1.getTransmissionId() <= p2.getTransmissionId()){
					return 1;
				}
				return 0;
			}
		};
		sortedList.sort(c);
		System.out.println();
		System.out.println("ValidateJobID" + "        " +"ClientID" + "       " + "TransmissionId");
		for(Package p: sortedList){
			System.out.println("      " +jobId + "        " +p.getClientId() + "         " + p.getTransmissionId());
		}
	}

}
