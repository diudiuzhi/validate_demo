package demo.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import demo.db.DBUtil;
import demo.db.TelemetryDAO;
import demo.model.Packet;

public class TelemetryService {

	private SqlSession session;
	private TelemetryDAO telemetryDAO;

	public TelemetryService() throws IOException{
		session = DBUtil.getSession();
		telemetryDAO = session.getMapper(TelemetryDAO.class);
	}

	public  void insertPacketsToTelemetry(ArrayList<Packet> validatedPacketsList) {
		for (Packet packet: validatedPacketsList){
			telemetryDAO.insertPacket(packet);
		}
	}
}
