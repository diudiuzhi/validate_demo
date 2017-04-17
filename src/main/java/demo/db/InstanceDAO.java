package demo.db;

import java.util.ArrayList;

import demo.model.Instance;

public interface InstanceDAO {
	public int insertInstance(Instance instance);
	public void updateInstanceStatus(Instance instance);
	public  void deleteInstance(String instanceID);
	public  Instance getInstance(String instanceID);
	public  ArrayList<Instance> getAllInstances();
}
