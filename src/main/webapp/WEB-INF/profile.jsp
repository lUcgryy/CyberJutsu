<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile</title>
</head>
<body>
<h1>Welcome to profile page</h1>
	<p>${adminFlag }</p>
    <div align="center">
    	<p>${json }</p>
        <h1>UPDATE</h1>
        <form action="<%= request.getContextPath() %>/profile?id=${id }" method="post">
            <p>Credit Card: <input type="text" name="creditCard"/></p>
            <p>Email: <input type="text" name="email"/></p>
            <p>Biography: <input type="text" name="bio"/></p>
            <input type="submit" value="Save change"/>
        </form>
        <h1>Upload Avatar</h1>
        <form action="<%= request.getContextPath() %>/profile" method="post" >
            <input type="file" name="avatar"/>
            <p></p>
            <input type="submit" value="Upload"/>
        </form>
        <h1>Upload Kyc</h1>
        <form action="<%= request.getContextPath() %>/profile" method="post">
            <input type="text" name="kyc">
            <p></p>
            <input type="submit" value="Verify"/>	
        </form>
    </div>
</body>
</html>