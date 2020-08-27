<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>e-university</title>
    <%-- Стили страницы --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/MainPage.css">
    <%-- Стили всплывающего окна. Используеюся библиотека sweetalert2. --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/sweetalert2.min.css">
</head>

<body>

<div id="userBar">
    <hr>

    <c:if test="${role eq 'ADMIN'}">
        <h3>Администратор</h3>
        <table class="userBarOptionsTable">
            <tr>
                <td><b>Опции:</b></td>
                <td>
                    <input type="submit" value="Регистрация нового пользователя"
                           onclick="window.location='RegistrationPage.jsp';"/>
                </td>
            </tr>
        </table>
    </c:if>

    <c:if test="${role eq 'USER'}">
        <h3>Пользователь</h3>
        <table class="userBarOptionsTable">
            <tr>
                <td><b>Опции:</b></td>
                <td>
                    Дополнительные привилегии доступны у администратора
                </td>
            </tr>
        </table>
    </c:if>

    <table>
        <tr>
            <td>
                <a href="<c:url value='/logout' />">Выйти</a>
            </td>
        </tr>
    </table>
    <hr>
</div>


<form action="<c:url value="/main" />" method="POST">
    <table id="selectGroupTable"> <%-- Таблица --%>
        <tr> <%-- Строка --%>
            <td> <%-- Столбец --%>
                Год обучения:&nbsp; <input type="number" name="selected_year" id="year" min="1900"
                                           value="${mainDataForm.selectedYear}"/>
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
                    <fmt:formatDate value="${student.dateOfBirth}" pattern="dd-MM-yyyy"/>
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
                Новый год обучения:&nbsp; <input type="number" name="new_year" min="1900" size="5"
                                                 value="${mainDataForm.selectedYear}"/>
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
<%-- Скрипты выгодно подключать в конце тега body для более быстрой загрузки страницы --%>
<%-- Скрипты всплывающего окна. Используеюся библиотека sweetalert2. Скрипты выгодно подключать перед /body --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/sweetalert2.min.js"></script>

<%-- Скрипт всплывающего окна об успешном переводе студентов --%>
<script>
    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: false,
        onOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
    })
</script>

<%-- Выводит оповещение, если группа была успешно перемещена --%>
<c:if test="${successfulMoveGroupNotification}">
    <script>
        Toast.fire({
            icon: 'success',
            title: 'Студенты были успешно переведены в новую группу'
        })
    </script>
</c:if>

<%-- Выводит оповещение, если студент был успешно удален --%>
<c:if test="${successfulStudentDeletionNotification}">
    <script>
        Toast.fire({
            icon: 'success',
            title: 'Студент был успешно удален'
        })
    </script>
</c:if>

<%-- Выводит оповещение, если студент был успешно добавлен --%>
<c:if test="${successfulStudentInsertionNotification}">
    <script>
        Toast.fire({
            icon: 'success',
            title: 'Студент был успешно добавлен'
        })
    </script>
</c:if>

<%-- Выводит оповещение, если студент был успешно отредактирован --%>
<c:if test="${successfulStudentUpdatingNotification}">
    <script>
        Toast.fire({
            icon: 'success',
            title: 'Информация о студенте была успешно обновлена'
        })
    </script>
</c:if>

</body>
</html>