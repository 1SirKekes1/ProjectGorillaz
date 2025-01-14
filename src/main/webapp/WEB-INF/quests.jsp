<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Quests"/>
</jsp:include>
<h2>Quests</h2>
<ul style="list-style-type: none; padding: 0;">
    <c:forEach var="questEntry" items="${quests}">
        <li style="display: flex; align-items: center; margin-bottom: 20px;">
            <c:if test="${not empty questEntry.value.base64Image}">
                <img src="data:image/jpeg;base64,${questEntry.value.base64Image}" alt="${questEntry.value.title}" style="width: 150px; height: 150px; border-radius: 10px; margin-right: 20px;">
            </c:if>
            <div>
                <h3>${questEntry.value.title}</h3>
                <p>${questEntry.value.description}</p>
            </div>
        </li>
    </c:forEach>
</ul>
<jsp:include page="footer.jsp"/>