<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit page</title>
</head>
    <jsp:useBean id="user" type="com.example.teapot_project.model.User" scope="request"/>
<body style="background-color:#ADD8E6">
<section>
    <h2>${param.action == 'create' ? 'Create user' : 'Edit user'}</h2>
    <form method="post" action="?action=updateUser">
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
            <dd>
            <dt>Group:</dt>
            <dd>
                <select value="${user.groupId}" name="groupId">
                    <option value="${user.groupId}">${user.groupId == null ? 'chose group' : user.getGroup(groups)} </option>
                    <option value="">Without group</option>
                    <c:forEach var="group" items="${groups}">
                        <jsp:useBean id="group" class="com.example.teapot_project.model.Group"/>
                        <option value="${group.id}"> ${group.groupColor}</option>
                    </c:forEach>
                </select>
            </dd>
        </dl>
        <dl>
            <dt>Answer status:</dt>
            <dd>
                <select value="${user.answerStatus}" name="answerStatus">
                    <option value="${user.answerStatus}">${user.answerStatus == true ? 'Answered' : 'Not Answered'} </option>
                    <option value="${!user.answerStatus}">${user.answerStatus == true ? 'Not Answered' : 'Answered'} </option>

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
