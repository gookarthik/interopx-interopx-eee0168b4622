package com.interopx.platform.agent.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnector {

	public static Boolean generateCSV(String jdbcURL,String userName,String password, String filePath, String exportPath) throws SQLException {

		Connection connection = getConnection(jdbcURL,userName,password);
		
		if(connection!=null) {
			
			System.out.println("Database Connection established successfully...!");
			//get list of queries from the SQL file
			ArrayList<String> queries = SQLReader.getQueries(filePath);
			try {
			for(String query:queries) {
			    try {
			    	query = query.replace("{EXPORT_DIR}", exportPath);
			    	PreparedStatement psProcToexecute = connection.prepareStatement(query);
					psProcToexecute.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			}finally {
				connection.close();
			}
			
			System.out.println("CSV files generated successfully...!");
			return true;
			
		}else {
			System.err.println("Failed to establish connection with provided information. Please try again later...");
			return false;
		}
	}

	public static Connection getConnection(String url, String user, String password) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return conn;
	}

}
