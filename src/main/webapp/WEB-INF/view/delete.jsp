<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add user</title>
</head>
<body>
<table style=" width: 100%; border: 4px double black;">
    <tr>
        <td style="border: 1px solid black; text-align: center">
            <a href="${pageContext.servletContext.contextPath}/all">All users</a>
        </td>
        <td style="border: 1px solid black; text-align: center">
            <a href="${pageContext.servletContext.contextPath}/add">Add new user</a>//!! не работает!!
        </td>
        <td style="border: 1px solid black; text-align: center">
            <a href="${pageContext.servletContext.contextPath}/delete">Delete user</a>
        </td>
        <td style="border: 1px solid black; text-align: center">
            <a href="${pageContext.servletContext.contextPath}/update">Update user</a>
        </td>
    </tr>
</table>
<form action="${pageContext.servletContext.contextPath}/delete" method="post">
    <p align="center">
        ID: <input type="text" name="id"/>
        <input type="submit" value="Submit"/>
    </p>
</form>
</body>
</html>