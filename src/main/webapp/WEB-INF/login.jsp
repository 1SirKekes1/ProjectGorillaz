<!-- login.jsp -->
<%@ include file="header.jsp" %>

<main style="padding: 20px;">
    <h2>Login</h2>
    <form action="login" method="post">
        Username: <input type="text" name="username" required><br><br>
        Password: <input type="password" name="password" required><br><br>
        <input type="submit" value="Login">
    </form>
</main>

<%@ include file="footer.jsp" %>