package IgniteBizJava.IgniteJavaFirst;

import java.sql.*;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class CreateTableDemo
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException 
	{
		Ignite ignite = Ignition.start();
		ignite.cluster().active(true);
		
		// Register JDBC driver
		Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
		
		// Open JDBC connection
		Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
		
		// create tables
		try (Statement stmt = conn.createStatement()) {
		stmt.executeUpdate("CREATE TABLE City (" + 
		" id LONG PRIMARY KEY, name VARCHAR) " +
		" WITH \"template=replicated\"");
		stmt.executeUpdate("CREATE TABLE Person (" +
		" id LONG, name VARCHAR, city_id LONG, " +
		" PRIMARY KEY (id, city_id)) " +
		" WITH \"backups=1, affinityKey=city_id\"");
		stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)");
		stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)");
		}
	System.out.println("Tables created");
	}
}
