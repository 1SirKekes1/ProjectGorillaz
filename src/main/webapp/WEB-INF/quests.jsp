<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="Quests"/>
</jsp:include>

<h2>Quests</h2>

<div class="quest-grid">
    <jsp:useBean id="quests" scope="request" type="java.util.HashMap"/>
    <c:forEach var="questEntry" items="${quests}">
        <div class="quest-item">
            <c:if test="${not empty questEntry.value.base64Image}">
                <img src="data:image/jpeg;base64,${questEntry.value.base64Image}" alt="${questEntry.value.title}"
                     style="width: 500px; height: 500px; border-radius: 10px;">
            </c:if>
            <div>
                <h3>${questEntry.value.title}
                    <a href="${pageContext.request.contextPath}/quest?questId=${questEntry.value.id}"
                       class="play-button">PLAY</a>
                </h3>
                <p>${questEntry.value.description}</p>
            </div>
        </div>
    </c:forEach>
</div>

<jsp:include page="footer.jsp"/>