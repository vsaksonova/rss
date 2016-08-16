<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>

    <c:if test="${!empty error}"><p style="color:red">${error}</p></c:if>

    <form action="login" method="post" accept-charset="UTF-8">
        <fieldset>
            <legend> Login to RSS Reader </legend>
            Username:<br>
            <input type="text" name="username" required="required" /><br>
            Password:<br>
            <input type="password" name="userpass" required="required" /><br><br>
            <input type="submit" value="Login" /><br><br>
            <a href="registration.jsp">Register</a>
        </fieldset>

    </form>
</body>
</html>