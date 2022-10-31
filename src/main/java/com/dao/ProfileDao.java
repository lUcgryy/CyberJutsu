package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.model.User;


public class ProfileDao {
	public User getData(String id) throws ClassNotFoundException {
		User user = new User();
		user.setId(id);
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager
		            .getConnection("jdbc:mysql://localhost:3306/cyberjutsu", "root", "123456");
			String query = "Select * from users where id = " + id;
			Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(query);
            
            while (result.next()) {
            	user.setUsername(result.getString("username"));
            	user.setEmail(result.getString("email"));
            	user.setBio(result.getString("bio"));
            	user.setMoney(result.getInt("money"));
            	user.setCreditCard(result.getString("credit_card"));
            	user.setAvatar(result.getString("avatar"));
            	user.setRole(result.getString("role"));
            	user.setKyc(result.getBoolean("kyc"));
            	break;
            }
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}
	public void updateInfo(User user) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager
		            .getConnection("jdbc:mysql://localhost:3306/cyberjutsu", "root", "123456");
			String query = "Update users"
					+ " Set email = '" + user.getEmail() + "', credit_card = '" + user.getCreditCard() + "', bio = '" + user.getBio() + "', avatar = '" + user.getAvatar() + "', kyc = " + user.isKyc()
					+ " where id = " + 	user.getId();
			System.out.println(query);
			Statement st = connection.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	public String getAdminId() throws ClassNotFoundException {
		String id = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager
		            .getConnection("jdbc:mysql://localhost:3306/cyberjutsu", "root", "123456");
			String query = "Select id from users where role = 'admin'";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			id = rs.getString("id");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return id;
	}
	public String getAdminUsername() throws ClassNotFoundException {
		String username = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager
		            .getConnection("jdbc:mysql://localhost:3306/cyberjutsu", "root", "123456");
			String query = "Select username from users where role = 'admin'";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			username = rs.getString("username");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return username;
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