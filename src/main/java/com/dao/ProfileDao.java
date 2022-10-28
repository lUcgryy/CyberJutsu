package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ProfileDao {
	public ArrayList<Object> getData(String id) throws ClassNotFoundException {
		ArrayList<Object> list = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager
		            .getConnection("jdbc:mysql://localhost:3306/cyberjutsu", "root", "123456");
			String query = "Select * from users where id = " + id;
			Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(query);
          
            while (result.next()) {
            	list.add(result.getString("username"));
            	list.add(result.getString("email"));
            	list.add(result.getString("bio"));
            	list.add(result.getInt("money"));
            	list.add(result.getString("credit_card"));
            	list.add(result.getString("avatar"));
            	break;
            }
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return list;
	}
	
	private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}