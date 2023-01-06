<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>

<html>
<head>
    <title>Competition</title>
</head>
<body style="background-color:#ADD8E6">
<jsp:useBean id="user" class="com.example.teapot_project.model.User"/>
<jsp:useBean id="group" class="com.example.teapot_project.model.Group"/>
<section>
    <h3><a href="/teapot_project">Home</a></h3>
    <hr/>
    <h2>Competition</h2>
    <c:if test="${isNotValid}"><h3>You should have at least 2 groups and 2 users to start competition<h3/></c:if>
    <c:if test="${!isNotValid}"><h3><a href="/teapot_project/users?action=changeCompetitor">Next pair</a></h3></c:if>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Group</th>
            <th>Name</th>
            <th>Versus</th>
            <th>Group</th>
            <th>Name</th>

        </tr>
        </thead>
        <tr>
            <td>${groupA.groupColor}</td>
            <td>${userA.toStringName()}</td>
            <td>   </td>
            <td>${groupB.groupColor}</td>
            <td>${userB.toStringName()}</td>
        </tr>
        <tr>
            <td></td>
            <td><a href="/teapot_project/users?action=changeCompetitor&save=${userB.id}&change=${userA.id}">${userA == null ? '': 'Change'}</a></td>
            <td></td>
            <td></td>
            <td><a href="/teapot_project/users?action=changeCompetitor&save=${userA.id}&change=${userB.id}">${userB == null ? '' : 'Change'}</a></td>

        </tr>
    </table>
    <br/>
    <hr/>
    <br/>
    <h2>People who don't answer:</h2>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Group</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Status</th>

        </tr>
        </thead>

        <c:forEach var="user" items="${users}">
            <tr>
                <td><a href="?action=getGroupUsers&id=${user.groupId}">${user.getGroup(groups)}</a></td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.answerStatus ? 'Answered' : 'Not answered'}</td>
            </tr>
        </c:forEach>
    </table>


</section>
</body>
</html>