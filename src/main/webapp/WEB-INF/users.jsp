<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Users"/>
</jsp:include>

<style>
    <%@include file="/WEB-INF/css/style.css" %>
</style>

<h2>Users</h2>
<ul>
    <c:forEach var="user" items="${users}">
        <li>
            <h3>${user.value.username}</h3>
            <p>Email: ${user.value.email}</p>
            <p>Wins: ${user.value.wins}</p>
            <p>Losses: ${user.value.losses}</p>
        </li>
    </c:forEach>
</ul>
<jsp:include page="footer.jsp"/>