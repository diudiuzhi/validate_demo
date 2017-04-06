package demo;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CockpitClient extends Thread{
	private long clientId;
	private ArrayList<Package> unvalidatedList;
	private static Lock lock = new ReentrantLock();
	
	public CockpitClient(long clientId, ArrayList<Package> unvalidatedList){
		this.clientId = clientId;
		this.unvalidatedList = unvalidatedList;
	}
	
	public void run(){
		System.out.println("client " + clientId + " started");
		int transmissionId = 0;
		try{
			lock.lock();
			for(int i=0; i<5; i++){
				Package pa = new Package(clientId, transmissionId);
				transmissionId++;
				unvalidatedList.add(pa);
			}
		}finally{
			lock.unlock();
		}
	}
}

class Package{
	private long transmissionId;
	private long clientId;
	
	public Package(long clientId, long transmissionId){
		this.transmissionId = transmissionId;
		this.clientId = clientId;
	}
	
	public long getClientId(){
		return this.clientId;
	}
	
	public long getTransmissionId(){
		return this.transmissionId;
	}
	
}