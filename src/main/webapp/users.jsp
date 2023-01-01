<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<%@ page import="com.example.teapot_project.model.User" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<section>
    <h3><a href="/">Home</a></h3>
    <hr/>
    <h2>Users</h2>
    <a href="users?action=createForm">Add User</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Age</th>
            <th></th>
            <th></th>

        </tr>
        </thead>

        <c:forEach var="user" items="${users}">
            <jsp:useBean id="meal" class="com.example.teapot_project.model.User"/>

            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.age}</td>
                <td><a href="users?action=updateForm&id=${user.id}">Update</a></td>
                <td><a href="users?action=delete&id=${user.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>