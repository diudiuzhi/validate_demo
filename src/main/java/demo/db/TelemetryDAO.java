package demo.db;

import demo.model.Packet;

public interface TelemetryDAO {
	public void insertPacket(Packet packet);
}
