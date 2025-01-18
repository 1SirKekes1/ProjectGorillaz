<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="${quest.title}"/>
</jsp:include>

<div class="quest-detail">
    <h2>${quest.title}</h2>
    <c:if test="${not empty currentStep}">
        <div class="step">
            <h3>Step: ${currentStep.description}</h3>
            <img src="data:image/jpeg;base64,${currentStep.base64Image}" alt="${currentStep.description}">
            <c:if test="${not empty currentStep.choices}">
                <h4>Choices:</h4>
                <ul>
                    <c:forEach var="choice" items="${currentStep.choices}">
                        <li>
                            <a href="${pageContext.request.contextPath}/quest?questId=${quest.id}&stepId=${choice.nextStepId}">
                                    ${choice.text}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>

        <div class="navigation">
            <c:if test="${not empty prevStepId}">
                <a href="${pageContext.request.contextPath}/quest?questId=${quest.id}&stepId=${prevStepId}" class="nav-button">Previous</a>
            </c:if>
            <c:if test="${not empty nextStepId}">
                <a href="${pageContext.request.contextPath}/quest?questId=${quest.id}&stepId=${nextStepId}" class="nav-button">Next</a>
            </c:if>
        </div>
    </c:if>
</div>

<jsp:include page="footer.jsp"/>