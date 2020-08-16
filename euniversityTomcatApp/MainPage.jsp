<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>e-university</title>
    <link rel="stylesheet" type="text/css" href="css/MainPage.css">
</head>

<body>
<form action="<c:url value="/main" />" method="POST">
    <table id="selectGroupTable"> <%-- Таблица --%>
        <tr> <%-- Строка --%>
            <td> <%-- Столбец --%>
                Год обучения:&nbsp; <input type="number" name="selected_year" id="year" min="1900"  value="${mainDataForm.selectedYear}"/>
            </td>
            <td>
                Список групп:&nbsp;
                <select name="selected_group_id"> <%-- Список групп --%>
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

    <p><b>Список студентов для выбранных параметров:</b></p>

    <table width="50%" cellspacing="0" cellpadding="5" border="1">
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

    <p></p>

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

    <p><b>Переместить студентов в другую группу:</b></p>

    <table id="moveGroupTable">
        <tr>
            <td>
                Новый год обучения:&nbsp; <input type="number" name="new_year" min="1900" size="5" value="${mainDataForm.selectedYear}"/>
            </td>
            <td>
                Новая группа:&nbsp;
                <select name="new_group_id"> <%-- Список групп --%>
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