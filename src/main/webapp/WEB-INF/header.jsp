<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${pageTitle}</title>
    <style>
        <%@include file="/WEB-INF/css/style.css" %>
    </style>
</head>
<body>
<header>
    <h1>My Web App</h1>
</header>
<nav>
    <a href="/">Home</a>
    <a href="/quests">Quests</a>
    <a href="/users">Users</a>

    <div style="float: right;">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <span>Welcome, ${sessionScope.user.username}!</span>
                <a href="/logout">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="/login">Login</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>