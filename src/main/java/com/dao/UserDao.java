package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.model.User;

public class UserDao {
	public int registerUser(User user) throws ClassNotFoundException {
        String INSERT_USER_SQL = "INSERT INTO users" + " (username, password, email, description) VALUES " + " (?, ?, ?, ?);";
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/cyberjutsu", "root", "123456");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
            preparedStatement.setString(1, user.getUsername());            
            preparedStatement.setString(2, user.getPassword());            
            preparedStatement.setString(3, user.getEmail());            
            preparedStatement.setString(4, user.getDescription());            
            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }     
        return result;
    }
}
