package demo;

import java.io.IOException;
import java.util.UUID;

public class Main {
	public static void start() throws IOException{
		// Register Instance
		String instanceID = UUID.randomUUID().toString();
		int rtval = Util.registerInstance(instanceID);
		if (rtval == -1){
			System.exit(-1);
		}

		// Generate HeartbeatThread
		HeartbeatThread heartbeatThread = new HeartbeatThread(instanceID);
		heartbeatThread.setDaemon(true);
		heartbeatThread.start();

		// Generate ValidateThread
		Thread validateThread = new ValidationThread(instanceID);
		validateThread.setDaemon(true);
		validateThread.start();
		while(true){
			if (heartbeatThread.isAlive() && validateThread.isAlive()){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			System.exit(-1);
		}
	}

	public static void main(String args[]) throws IOException{
		Main.start();
	}
}