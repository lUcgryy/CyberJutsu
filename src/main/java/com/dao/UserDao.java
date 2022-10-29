package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.model.User;

public class UserDao {
	public int registerUser(User user) throws ClassNotFoundException {
        String INSERT_USER_SQL = "INSERT INTO users" + " (username, password, email, bio, money, credit_card, avatar, role, kyc) VALUES " + " (?, ?, ?, ?, ?, ?, ?, ?, ?	);";
        String GET_USER_ID_SQL = "SELECT id from users where username = ?";
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/cyberjutsu", "root", "123456");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
            preparedStatement.setString(1, user.getUsername());            
            preparedStatement.setString(2, user.getPassword());   
            preparedStatement.setString(3, user.getEmail()); 
            preparedStatement.setString(4, user.getBio()); 
            preparedStatement.setInt(5, user.getMoney()); 
            preparedStatement.setString(6, user.getCreditCard()); 
            preparedStatement.setString(7, user.getAvatar()); 
            preparedStatement.setString(8, user.getRole()); 
            preparedStatement.setBoolean(9, user.isKyc()); 
//            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(GET_USER_ID_SQL);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next()){            
            	
            	break;
            }
            System.out.println(user.getId());
        } catch (SQLException e) {
        	printSQLException(e);
        }     
        return result;
    }
	public boolean checkUser(User user) throws ClassNotFoundException{
		boolean check = false;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
        	Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/cyberjutsu", "root", "123456");
        	String query = "Select * from users where username = '" + user.getUsername() + "'";
        	Statement st = connection.createStatement();
        	ResultSet rs = st.executeQuery(query);
        	check = !rs.next();
		} catch (SQLException e) {
			printSQLException(e);
		}
		return check;
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
