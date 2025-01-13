<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h1>Users</h1>
<ul>
    <c:forEach var="user" items="${users}">
        <li>
            <h2>${user.value.username}</h2>
            <p>Email: ${user.value.email}</p>
        </li>
    </c:forEach>
</ul>
<a href="/">Back to Home</a>
</body>
</html>