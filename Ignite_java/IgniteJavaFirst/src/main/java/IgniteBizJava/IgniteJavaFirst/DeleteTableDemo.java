package IgniteBizJava.IgniteJavaFirst;

import java.sql.*;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class DeleteTableDemo 
{
	public static void main(String[] args) throws ClassNotFoundException,SQLException 
	{
		Ignite ignite = Ignition.start();
		ignite.cluster().active(true);
		
		Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
		Connection conn = DriverManager.getConnection("jdbc:ignite:thin://192.168.1.6/");
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("Drop TABLE City"); 
			stmt.executeUpdate("Drop TABLE Person");
		
		}
	System.out.println("Tables deleted");
	}
}