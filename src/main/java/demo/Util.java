package demo;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import demo.service.InstanceService;

public final class Util {
	private static InstanceService instanceService;

	public static final int registerInstance(String instanceID){
		// Run in once when process started.
		try{
			instanceService.insertInstance(instanceID);
		}
		catch (Exception e){
			return -1;
		}
		return 0;
	}

	public static SqlSession GetDBSession() throws IOException{
		String resource = "mybatis-config.xml";
	    Reader reader = Resources.getResourceAsReader(resource);
	    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	    SqlSessionFactory factory = builder.build(reader);
	    SqlSession session = factory.openSession();
		return session;
	}


}
