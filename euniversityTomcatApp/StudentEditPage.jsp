<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>e-university</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/StudentEditPage.css">
</head>

<body>
<p>
    <c:choose>
        <c:when test="${studentDataForm.studentId==0}">
            <b>Добавление студента.</b>
        </c:when>
        <c:otherwise>
            <b>Редактирование информации о студенте.</b>
        </c:otherwise>
    </c:choose>
</p>
<form action="<c:url value="/student-edit" />" method="POST"> <%-- Вызов сервелета StudentEditPageServlet --%>
    <input type="hidden" name="student_id"
           value="${studentDataForm.studentId}"> <%-- Для передачи student_id из studentDataForm в сервлет StudentEditPageServlet --%>

    <table> <%-- Таблица --%>
        <tr> <%-- Строка --%>
            <td>Фамилия:</td>
            <td>
                <input type="text" name="last_name" value="${studentDataForm.lastName}">
            </td>
        </tr>
        <tr>
            <td>Имя:</td>
            <td>
                <input type="text" name="first_name" value="${studentDataForm.firstName}">
            </td>
        </tr>
        <tr>
            <td>Отчество:</td>
            <td>
                <input type="text" name="patronymic" value="${studentDataForm.patronymic}">
            </td>
        </tr>
        <tr>
            <td>Дата рождения:</td>
            <td>
                <input type="text" name="date_of_birth" value="${studentDataForm.dateOfBirth}">
            </td>
        </tr>
        <tr>
            <td>Пол:</td>
            <td>
                <c:choose>
                    <c:when test="${studentDataForm.sex==0}"> <%-- Если пол известен и он мужской --%>
                        <input type="radio" name="sex" value="0"
                               checked>Мужчина</input> <%-- Тогда будет выбран мужской пол, но будет возможность его изменить --%>
                        <input type="radio" name="sex" value="1">Женщина</input>
                        <input type="radio" name="sex" value="3">Неопределенный</input>
                    </c:when>
                    <c:when test="${studentDataForm.sex==1}"> <%-- Если пол известен и он женский --%>
                        <input type="radio" name="sex" value="0">Мужчина</input>
                        <input type="radio" name="sex" value="1"
                               checked>Женщина</input> <%-- Тогда будет выбран женский пол, но будет возможность его изменить --%>
                        <input type="radio" name="sex" value="3">Неопределенный</input>
                    </c:when>
                    <c:otherwise> <%-- Если пол не мужской и не женский т.е. неизвестен --%>
                        <input type="radio" name="sex" value="0">Мужчина</input>
                        <input type="radio" name="sex" value="1">Женщина</input>
                        <input type="radio" name="sex" value="3"
                               checked>Неопределенный</input> <%-- Тогда будет выбран неизвестный пол, но будет возможность его изменить --%>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>Группа:</td>
            <td>
                <select name="group_id"> <%-- Список групп--%>
                    <c:forEach var="group" items="${studentDataForm.allGroups}">
                        <c:choose>
                            <c:when test="${group.groupId==studentDataForm.groupId}">
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
        </tr>
        <tr>
            <td>Год обучения:</td>
            <td>
                <input type="number" name="education_year" min="1900" value="${studentDataForm.educationYear}">
            </td>
        </tr>
    </table>

    <table>
        <tr>
            <td>
                <input type="submit" name="apply" value="Выполнить">
            </td>
            <td>
                <input type="button" name="cancel" onclick="history.back();" value="Отмена"> <%-- history.back() - возрващает на предыдущую страницу --%>
            </td>
        </tr>
    </table>

</form>
</body>
</html>