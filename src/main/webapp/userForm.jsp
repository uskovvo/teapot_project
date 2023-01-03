<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit page</title>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create user' : 'Edit user'}</h2>
    <jsp:useBean id="user" type="com.example.teapot_project.model.User" scope="request"/>
    <form method="post" action="users?action=updateUser">
        <input type="hidden" name="id" value="${user.id}">
        <dl>
            <dt>Name:</dt>
            <dd><input type="text" value="${user.name}" size=20 name="name" required></dd>
        </dl>
        <dl>
            <dt>Surname:</dt>
            <dd><input type="text" value="${user.surname}" size=20 name="surname" required></dd>
        </dl>
        <dl>
            <dt>Group:</dt>
            <dd>
                <select value="${user.age}" name="groupId">
                    <option value="">Chose group</option>
                    <c:forEach var="group" items="${groups}">
                        <jsp:useBean id="group" class="com.example.teapot_project.model.Group"/>
                        <option value="${group.id}">${group.groupColor}</option>
                    </c:forEach>
                </select>
            </dd>
        </dl>
        <dl>
            <dt>Age:</dt>
            <dd><input type="number" value="${user.age}" name="age" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
