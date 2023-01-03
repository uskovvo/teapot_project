<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>

<html>
<head>
    <title>Groups</title>
</head>
<body>
<section>
    <h3><a href="/users">Users</a></h3>
    <hr/>
    <h2>Groups</h2>
    <a href="?action=groupForm">Add Group</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Name</th>
            <th></th>
            <th></th>

        </tr>
        </thead>

        <c:forEach var="group" items="${groups}">
            <jsp:useBean id="group" class="com.example.teapot_project.model.Group"/>
            <tr>
                <td><a href="?action=getGroupUsers&id=${group.id}">${group.groupColor}</a></td>
                <td><a href="?action=updateGroupForm&id=${group.id}">Update</a></td>
                <td><a href="?action=deleteGroup&id=${group.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>