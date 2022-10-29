package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.model.User;

public class RankDao {
	public ArrayList<User> getData() throws ClassNotFoundException {
		ArrayList<User> list = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			String query = "Select id, username, money, bio, avatar from users order by money DESC limit 5";
			Connection connection = DriverManager
		            .getConnection("jdbc:mysql://localhost:3306/cyberjutsu", "root", "123456");
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			ResultSet rs = preparedStatement.getResultSet();
			while (rs.next()) {
				User user = new User();
				user.setId(Integer.toString(rs.getInt("id")));
				user.setUsername(rs.getString("username"));
				user.setMoney(rs.getInt("money"));
				user.setBio(rs.getString("bio"));
				user.setAvatar(rs.getString("avatar"));
				list.add(user);
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
