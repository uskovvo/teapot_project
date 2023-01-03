<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>

<html>
<head>
    <title>User list</title>
</head>
<body>
<section>
    <h3><a href="/">Home</a></h3>
    <hr/>
    <h2>Users</h2>
    <a href="?action=createForm">Add User</a>
    <br/>
    <a href="?action=groupForm">Add Group</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Group</th>
            <th>Age</th>
            <th></th>
            <th></th>

        </tr>
        </thead>

        <c:forEach var="user" items="${users}">
            <jsp:useBean id="user" class="com.example.teapot_project.model.User"/>
            <jsp:useBean id="group" class="com.example.teapot_project.model.Group"/>


            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.getGroup(groups)}                           </td>
                <td>${user.age}</td>
                <td><a href="?action=updateForm&id=${user.id}">Update</a></td>
                <td><a href="?action=delete&id=${user.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>