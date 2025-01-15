<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="${quest.title}"/>
</jsp:include>

<h2>${quest.title}</h2>

<c:if test="${not empty quest.base64Image}">
    <img src="data:image/jpeg;base64,${quest.base64Image}" alt="${quest.title}"
         style="width: 500px; height: 500px; border-radius: 10px;">
</c:if>

<p>${quest.description}</p>

<h3>Steps:</h3>
<ul>
    <c:forEach var="step" items="${quest.steps}">
        <li>
            <p>${step.description}</p>
            <c:if test="${not empty step.choices}">
                <ul>
                    <c:forEach var="choice" items="${step.choices}">
                        <li>${choice.text}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </li>
    </c:forEach>
</ul>

<jsp:include page="footer.jsp"/>