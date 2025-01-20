<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="header.jsp" %>

<main style="padding: 20px;">
    <h2>Welcome, ${user.username}!</h2>
    <p>Your email is ${user.email}.</p>
    <p><a href="logout">Logout</a></p>
</main>

<%@ include file="footer.jsp" %>