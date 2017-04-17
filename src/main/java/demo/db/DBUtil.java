package demo.db;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBUtil {
	private static SqlSession session;

	public static SqlSession getSession() throws IOException{
		if(session == null){
			String resource = "mybatis-config.xml";
		    Reader reader = Resources.getResourceAsReader(resource);
		    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		    SqlSessionFactory factory = builder.build(reader);
		    session = factory.openSession();
		}
		return session;
	}
}
