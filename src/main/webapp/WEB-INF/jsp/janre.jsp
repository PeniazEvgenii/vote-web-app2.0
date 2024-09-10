
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
    <title>Добавление жанров</title>
</head>

<body style="background-color: #D3D3D3">

    <%@ include file="header.jsp" %>


    <h4><fmt:message key="page.janre.janres"/></h4>
        <c:forEach var="janre" items="${requestScope.janres}">
            <span>${janre.value}</span>
            <br>
        </c:forEach>

    <form action="${pageContext.request.contextPath}/service/genre" method="post">
        <h4><fmt:message key="page.janre.addJanre"/></h4>

        <label for="janreID">
            <input type="text" name="janre" id="janreID" size="80" required>
        </label>
        <br>
        <br>
        <button type="submit"><fmt:message key="button.send" /></button>
        <br><br>

        <c:if test="${not empty requestScope.janreErr}">
            <div style="color: #FF0000">
            <span>${requestScope.janreErr}</span>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.janreAdd}">
             <div style="color: #228B22">
             <span>${requestScope.janreAdd}</span>
             </div>
        </c:if>
    </form>

    <h3><fmt:message key="page.genre.delete.header"/></h3>

    <form action="${pageContext.request.contextPath}/service/genre" method="post">
        <h4><fmt:message key="page.genre.delete.choice"/></h4>

        <select name = "deleteGenre" id = "delete">
            <c:forEach var="janre" items = "${requestScope.janres}">
                <option value = ${janre.key}>${janre.value}</option>
            </c:forEach>
         </select>
        <br><br>
        <button type="submit"><fmt:message key="page.artist.delete.button" /></button>
        <br><br>

        <c:if test="${not empty requestScope.deleteGenreErr}">
            <div style="color: #FF0000">
            <span>${requestScope.deleteGenreErr}</span>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.deleteGenre}">
            <div style="color: #228B22">
            <span>${requestScope.deleteGenre}</span>
            </div>
        </c:if>
    </form>

</body>