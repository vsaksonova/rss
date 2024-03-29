<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/registration.css">
</head>
<body>
    <c:if test="${!empty error}"><p id="error">${error}</p></c:if>

    <form action="registration" method="post" accept-charset="UTF-8">
        Name:<br> <input type="text" name="username" maxlength="${usernameMaxLength}" required="required" /><br>
        Password:<br> <input type="password" name="userpass" maxlength="${passwordMaxLength}" required="required" /><br><br>
        <input type="submit" value="Register" /><br><br>
        <a href="login">Already have account?</a>
    </form>

</body>
</html>