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
            	list.add(result.getBoolean("kyc"));
            	break;
            }
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return list;
	}
	public void updateInfo(String id ,String email, String creditCard, String bio, String avatar, String kyc) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			String kycCheck;
			if (kyc != "") {
				kycCheck = "true";
			} else {
				kycCheck = "false";
			}
			Connection connection = DriverManager
		            .getConnection("jdbc:mysql://localhost:3306/cyberjutsu", "root", "123456");
			String query = "Update users"
					+ " Set email = '" + email + "', credit_card = '" + creditCard + "', bio = '" + bio + "', avatar = '" + avatar + "', kyc = " + kycCheck
					+ " where id = " + id;
			System.out.println(query);
			Statement st = connection.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			printSQLException(e);
		}
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