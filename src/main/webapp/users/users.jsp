<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>

<html>
<head>
    <title>User list</title>
</head>
<body style="background-color:#ADD8E6">
<section>
    <h3><a href="/teapot_project">Home</a></h3>
    <hr/>
    <h2>Users</h2>
    <a href="?action=createForm">Add User</a>
    <br/>
    <a href="?action=groupForm">Add Group</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Group</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Answer status</th>
            <th></th>
            <th></th>

        </tr>
        </thead>

        <c:forEach var="user" items="${users}">
            <jsp:useBean id="user" class="com.example.teapot_project.model.User"/>
            <jsp:useBean id="group" class="com.example.teapot_project.model.Group"/>


            <tr>
                <td><a href="?action=getGroupUsers&id=${user.groupId}">${user.getGroup(groups)}</a></td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.answerStatus ? 'Answered' : 'Not answered'}</td>
                <td><a href="?action=updateForm&id=${user.id}">Update</a></td>
                <td><a href="?action=delete&id=${user.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>