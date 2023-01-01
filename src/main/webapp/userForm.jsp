<%--
  Created by IntelliJ IDEA.
  User: sokol
  Date: 28.12.2022
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
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
    <jsp:useBean id="user" type="model.User" scope="request"/>
    <form method="post" action="users">
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
            <dt>Age:</dt>
            <dd><input type="number" value="${user.age}" name="age" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
