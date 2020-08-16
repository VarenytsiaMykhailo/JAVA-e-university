<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>
        e-university
    </title>
</head>

<body>
<form action="<c:url value="/main" />" method="POST">
    <table> <%-- Таблица --%>
        <tr> <%-- Строка --%>
            <td> <%-- Столбец --%>
                Год: <input type="text" name="selectedYear" value="${mainDataForm.selectedYear}"/>
            </td>
            <td>
                Список групп:
                <select name="selectedGroupId"> <%-- Список --%>
                    <c:forEach var="group" items="${mainDataForm.allGroups}">
                        <c:choose>
                            <c:when test="${group.groupId == mainDataForm.selectedGroupId}">
                                <option value="${group.groupId}" selected>
                                    <c:out value="${group.groupName}"/>
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="${group.groupId}">
                                    <c:out value="${group.groupName}"/>
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </td>
            <td>
                <input type="submit" name="getStudentsList" value="Обновить"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>