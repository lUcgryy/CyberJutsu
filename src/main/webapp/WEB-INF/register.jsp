<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Register</title>
    </head>
    <body>
        <div align="center">
            <h1>Register form</h1>
            <form action="<%= request.getContextPath() %>/register" method="post">
                <table style="width: 80%">                 
                    <tr>
                        <td>Username</td>
                        <td><input type="text" name="username"></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password"></td>
                    </tr>
                    <tr>
                    <td>Confirm Password</td>
                    <td><input type="password" name="confirmPassword"></td>
                    </tr>
                </table>
                <input type="submit" value="Submit">
            </form>
            <p>${noti }</p>
        </div>
    </body>
</html>