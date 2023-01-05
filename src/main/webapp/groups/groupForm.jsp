<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Group Form</title>
</head>
<body style="background-color:#ADD8E6">
<section>
    <h2>Create group</h2>
    <jsp:useBean id="group" type="com.example.teapot_project.model.Group" scope="request"/>
    <form method="post" action="../users?action=updateGroup">
        <input type="hidden" name="id" value="${group.id}">
        <dl>
            <dt>Name:</dt>
            <dd><input type="text" value="${group.groupColor}" size=20 name="groupColor" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>