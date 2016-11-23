package org.lok.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.lok.demo.db.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HelloServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {				
		
		Connection connection = null;
		
		try {
			connection = ConnectionManager.getConnection();		
			updateCounter(connection);
			int counter = getCounter(connection);
			PrintWriter out = resp.getWriter();
	    	out.println("<h1>Hello World! " + counter + "</h1>");
	    	out.close();
	    	
	    	resp.setStatus(200);
		} catch (SQLException e) {
			System.out.println("SQL Error occurred." + e);
			logger.error("SQL Error occurred.", e);
		} finally {
			
			if(connection != null){
				try {
					connection.commit();				
					ConnectionManager.closeConnection(connection);
				} catch (SQLException e) {
					
				}
			}
		}
	}
	
	private void updateCounter(Connection connection) throws SQLException {
		
		String query = "UPDATE `counter` SET `counter`= `counter` + 1 WHERE `config_key` = 'counter'";
		
		QueryRunner runner = new QueryRunner();
		
		runner.update(connection, query);
	}
	
	private int getCounter(Connection connection) throws SQLException {
		
		String query = "SELECT counter FROM `counter` WHERE config_key = 'counter'";
		
		QueryRunner runner = new QueryRunner();
		return runner.query(connection, query, rs -> {
			
			if (rs.next()) {
				return rs.getInt("counter");
			}
			
			return -1;
		});
	} 
}