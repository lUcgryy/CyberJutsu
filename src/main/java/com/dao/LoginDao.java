package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.model.Login;


public class LoginDao {
	public boolean validate(Login login) throws ClassNotFoundException {
        boolean status = false;

        Class.forName("com.mysql.cj.jdbc.Driver");

        try {
        	Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/cyberjutsu", "root", "123456");

            String query = "Select * from users where username = '" + login.getUsername() + "' and password = '" + login.getPassword() + "'";
            System.out.println(query);
            Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(query);
            status = result.next();
            System.out.println(result);
            
            
        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return status;
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
