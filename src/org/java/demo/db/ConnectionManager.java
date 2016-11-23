package org.java.demo.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionManager {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	
	private static BoneCP connectionPool = null;
	
	private ConnectionManager() {
		
	}

	public static void configureConnPool() {		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver not found." + e);
			logger.error("JDBC Driver not found.", e);			
		}
		
		System.out.println("DB Host: " + System.getProperty("dbHost"));		
		
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl("jdbc:mysql://" + System.getProperty("dbHost") + ":" + System.getProperty("dbPort") + "/" + System.getProperty("dbSchema"));
		config.setUsername(System.getProperty("dbUser"));
		config.setPassword(System.getProperty("dbPass"));
		config.setDefaultAutoCommit(false);

		config.setLazyInit(true); 
		try {
			connectionPool = new BoneCP(config);
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Connection pool cannot be created.", e);
		}
		
		ConnectionManager.setConnectionPool(connectionPool);
		System.out.println("creating new db connection pool");
		logger.info("creating new db connection pool");
	}

	public static void shutdownConnPool() {

		BoneCP connectionPool = ConnectionManager.getConnectionPool();
		logger.info("db connection pool contextDestroyed....");
		if (connectionPool != null) {
			connectionPool.shutdown(); 
			logger.info("db connection pool contextDestroyed.....Connection Pooling shut downed!");
		}
	}

	public static Connection getConnection() throws SQLException {

		return getConnectionPool().getConnection();
	}

	public static void closeStatement(Statement stmt) throws SQLException {
		
		if (stmt != null)
			stmt.close();		
	}

	public static void closeResultSet(ResultSet rSet) throws SQLException {
		
		if (rSet != null)
			rSet.close();		
	}

	public static void closeConnection(Connection conn) throws SQLException {
		
		if (conn != null)
			conn.close(); 
	}

	public static BoneCP getConnectionPool() {
		return connectionPool;
	}

	public static void setConnectionPool(BoneCP connectionPool) {
		ConnectionManager.connectionPool = connectionPool;
	}
}