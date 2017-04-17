package demo.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;

import demo.db.DBUtil;
import demo.db.InstanceDAO;
import demo.model.Instance;
import demo.model.Instance.STATUS;

public class InstanceService {
	private SqlSession session;
	private InstanceDAO instanceDAO;

	public InstanceService() throws IOException{
		session = DBUtil.getSession();
	    instanceDAO = session.getMapper(InstanceDAO.class);
	}

	public  void insertInstance(String instanceID){
		long lastHeartbeat = System.currentTimeMillis();
		Instance instance = new Instance();
		instance.setInstanceID(instanceID);
		instance.setStatus(Instance.STATUS.UP);
		instance.setLastHeartbeat(lastHeartbeat);
		instanceDAO.insertInstance(instance);
		session.commit();
	}

	public  void updateInstanceStatus(String instanceID, STATUS status){
		Instance instance = null;
		instance = instanceDAO.getInstance(instanceID);
		if(instance != null){
			if (instance.getStatus() == status){
				return;
			}
			instance.setStatus(status);
			session.commit();
		}
	}

	public  void updateInstanceLastHeartbeat(String instanceID){
		Instance instance = null;
		instance = instanceDAO.getInstance(instanceID);
		if(instance != null){
			long currentLastHeartbeat = System.currentTimeMillis();
			instance.setLastHeartbeat(currentLastHeartbeat);
			session.commit();
		}
	}

	public  void deleteInstance(String instanceID){
		instanceDAO.deleteInstance(instanceID);
	}

	public  Instance getInstance(String instanceID){
		return instanceDAO.getInstance(instanceID);
	}

	public  ArrayList<Instance> getInstances(){
		return instanceDAO.getAllInstances();
	}
}
