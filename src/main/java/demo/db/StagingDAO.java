package demo.db;

import java.util.ArrayList;

import demo.model.Packet;

public interface StagingDAO {
	public void insertStaging(Packet packet);
	ArrayList<Packet> getPacketsByClientID(String clientID);
	public  void deletePackets(ArrayList<Packet> validatedPacketsList);
}
