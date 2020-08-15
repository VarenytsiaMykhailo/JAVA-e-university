<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>e-university</title>
</head>

<body>
<form action="<c:url value="/main" />" method="POST">
    <table> <%-- Таблица --%>
        <tr> <%-- Строка --%>
            <td> <%-- Столбец --%>
                Год: <input type="text" name="selected_year" value="${mainDataForm.selectedYear}" size="5"/>
            </td>
            <td>
                Список групп:
                <select name="selected_group_id"> <%-- Список --%>
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
                <input type="submit" name="get_students_list" value="Обновить"/> <%-- Кнопка --%>
            </td>
        </tr>
    </table>

    <p></p>
    <b>Список студентов для выбранных параметров:</b>
    <p></p>

    <table>
        <tr>
            <th></th>
            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Дата рождения</th>
            <th>Пол</th>
        </tr>
        <c:forEach var="student" items="${mainDataForm.studentsForSelectedGroup}">
            <tr>
                <td>
                    <input type="radio" name="student_id" value="${student.studentId}"/>
                </td>
                <td>
                    <c:out value="${student.lastName}"/>
                </td>
                <td>
                    <c:out value="${student.firstName}"/>
                </td>
                <td>
                    <c:out value="${student.patronymic}"/>
                </td>
                <td>
                    <c:out value="${student.dateOfBirth}"/>
                </td>
                <td>
                    <c:out value="${student.sex}"/>
                </td>
            </tr>
        </c:forEach>
    </table>

    <table> <%-- Кнопки управления --%>
        <tr>
            <td>
                <input type="submit" name="insert_student" value="Добавить студента">
            </td>
            <td>
                <input type="submit" name="update_student" value="Редактировать студента">
            </td>
            <td>
                <input type="submit" name="delete_student" value="Удалить студента">
            </td>
        </tr>
    </table>

    <p></p>
    <b>Переместить студентов в другую группу:</b>
    <p></p>

    <table>
        <tr>
            <td>
                Новый год обучения: <input type="text" name="new_year" value="${mainDataForm.selectedYear}" size="5"/>
            </td>
            <td>
                Новая группа:
                <select name="new_group_id"> <%-- Список --%>
                    <c:forEach var="group" items="${mainDataForm.allGroups}">
                        <option value="${group.groupId}">
                            <c:out value="${group.groupName}"/>
                        </option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <input type="submit" name="move_group" value="Переместить студентов">
            </td>
        </tr>
    </table>

</form>
</body>
</html>