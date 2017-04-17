package demo.service;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;

import demo.db.DBUtil;
import demo.db.InstanceAssigmentDAO;
import demo.model.InstanceAssignment;

public class InstanceAssignmentService {
	private SqlSession session;
	private InstanceAssigmentDAO instanceAssigmentDAO;

	public InstanceAssignmentService() throws IOException{
		session = DBUtil.getSession();
		instanceAssigmentDAO = session.getMapper(InstanceAssigmentDAO.class);
	}

	public void insertInstanceAssignment(InstanceAssignment instanceAssignment){
		// not implement in production environment
		instanceAssigmentDAO.insertInstanceAssigment(instanceAssignment);
		session.commit();
	}

	public int getInstanceAssignmentCount() {
		return instanceAssigmentDAO.getInstanceAssignmentsCount();
	}

	public InstanceAssignment getPendingInstanceAssignment() {
		// Pick a client with PENDING status and return ClientID
		InstanceAssignment instanceAssignment = null;
		int count = instanceAssigmentDAO.getInstanceAssignmentsCount();
		if(count > 0){
			instanceAssignment = instanceAssigmentDAO.getPendingInstanceAssignment();
			if(instanceAssignment == null){
				instanceAssigmentDAO.resetFininshedInstanceAssignment();
				instanceAssignment = instanceAssigmentDAO.getPendingInstanceAssignment();
				instanceAssignment.setStatus(InstanceAssignment.STATUS.FETCHED);
				instanceAssigmentDAO.updateInstanceAssignment(instanceAssignment);
				session.commit();
			}
			return instanceAssignment;
		}
		return null;
	}

	public void resetInstanceAssignmentByInstanceID(String instanceID ) {
		instanceAssigmentDAO.resetInstanceAssignmentByInstanceID(instanceID);
		session.commit();
	}


	public InstanceAssignment[] getInstanceAssignmentsByInstanceID(String instanceID) {
		return null;
	}

	public static void main(String args []) throws IOException{
		InstanceAssignmentService service = new InstanceAssignmentService();
		InstanceAssignment instanceAssignment = new InstanceAssignment();
		instanceAssignment.setClientID("11111111");
		instanceAssignment.setInstanceID("3");
		instanceAssignment.setStatus(InstanceAssignment.STATUS.PENDING);

		int count = service.getInstanceAssignmentCount();
		System.out.println(count);
	}
}
