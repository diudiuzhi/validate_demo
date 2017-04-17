package demo.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import demo.db.DBUtil;
import demo.db.StagingDAO;
import demo.model.Packet;

public class StagingService {
	private SqlSession session;
	private StagingDAO stagingDAO;

	public StagingService() throws IOException{
		session = DBUtil.getSession();
		stagingDAO = session.getMapper(StagingDAO.class);
	}

	public  ArrayList<Packet> getPacketsByClientID(String clientID){
		return stagingDAO.getPacketsByClientID(clientID);
	}

	public  void deletePackets(ArrayList<Packet> validatedPacketsList) {
		stagingDAO.deletePackets(validatedPacketsList);
		session.commit();
	}
}
