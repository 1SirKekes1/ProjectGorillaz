<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quests</title>
</head>
<body>
<h1>Quests</h1>
<ul>
    <c:forEach var="quest" items="${quests}">
        <li>
            <h2>${quest.value.title}</h2>
            <p>${quest.value.description}</p>
        </li>
    </c:forEach>
</ul>
<a href="/">Back to Home</a>
</body>
</html>