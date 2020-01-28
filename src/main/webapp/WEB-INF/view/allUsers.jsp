<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 16.01.2020
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>AllUsers</title>
</head>
<body>
<table style=" width: 100%; border: 4px double black;">
    <tr>
        <td style="border: 1px solid black; text-align: center">
            <a href="${pageContext.servletContext.contextPath}/add">Add new user</a>
        </td>
        <td style="border: 1px solid black; text-align: center">
            <a href="${pageContext.servletContext.contextPath}/delete">Delete user</a>
        </td>
        <td style="border: 1px solid black; text-align: center">
            <a href="${pageContext.servletContext.contextPath}/update">Update user</a>
        </td>
    </tr>
</table>

<p align="center">Hello from JSP</p>
<table align="center" border="1px">
    <tr>
        <th>User Id</th>
        <th>User Name</th>
        <th>User Age</th>
        <th>User email</th>
        <th>password</th>
        <th>Action</th>

    </tr>

    <c:forEach var="user" items="${usersFromDB}">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.password}</td>
            <td>
                <a href="${pageContext.servletContext.contextPath}/delete">Delete user</a>
                <a href="${pageContext.servletContext.contextPath}/update">Update user</a>
            </td>

        </tr>
    </c:forEach>
</table>
</body>
</html>
