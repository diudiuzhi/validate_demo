package demo.db;

import java.util.ArrayList;

import demo.model.InstanceAssignment;;

public interface InstanceAssigmentDAO {
	public int insertInstanceAssigment(InstanceAssignment instanceAssignment);
	public InstanceAssignment getPendingInstanceAssignment();
	public void updateInstanceAssignment(InstanceAssignment instanceAssignment);
	public ArrayList <InstanceAssignment> getInstanceAssignmentsByInstanceID(String instanceID);
	public int getInstanceAssignmentsCount();
	public void resetInstanceAssignmentByInstanceID(String instanceID);
	public void resetFininshedInstanceAssignment();

}
