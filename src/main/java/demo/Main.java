package demo;

import java.security.SecureRandom;
import java.util.ArrayList;

import demo.ValidateJob;
import demo.CockpitClient;

public class Main {
	public static final int VALIDATE_JOB_NUM = 4;
	public static final int CLIENT_NUM = 10;

	public static final long MAX_CLIENT_ID = 92233736854775807L;
	public static final long MIN_CLIENT_ID = 1L;
	
	public static ArrayList<Package> unvalidatedQueue =
	        new ArrayList<Package>();
	
	public static void main(String args[]) throws InterruptedException{
		
		// Generate Client
		Thread clients[] = new Thread[CLIENT_NUM];
		SecureRandom ranGen = new SecureRandom();
		
		for(int i=0; i<CLIENT_NUM; i++){
			long clientId = (long)((ranGen.nextDouble()
                     * (MAX_CLIENT_ID - MIN_CLIENT_ID)) + MIN_CLIENT_ID);
			clients[i] = new CockpitClient(clientId, unvalidatedQueue);
			clients[i].setDaemon(true);
			clients[i].start();
		}
		Thread.sleep(2000);
		
		// Generate validateJob
		Thread jobs[] = new Thread[VALIDATE_JOB_NUM];
		
		for(int i=0; i<VALIDATE_JOB_NUM; i++){
			int jobId = i;
			jobs[i] = new ValidateJob(jobId, unvalidatedQueue);
			jobs[i].setDaemon(true);
			jobs[i].start();
			Thread.sleep(500);
		}
		
		Thread.sleep(2000);
	}
}
