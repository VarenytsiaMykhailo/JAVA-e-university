<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>e-university</title>
    <!-- Настройка viewport -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Подключаем Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <%-- Стили всплывающего окна. Используеюся библиотека sweetalert2. --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/sweetalert2.min.css">
    <%-- Стили Pages.css --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/Pages.css">

    <!-- Jquery -->
    <script
            src="https://code.jquery.com/jquery-2.2.4.js"
            integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
            crossorigin="anonymous"></script>
    <!-- Bootstrap js popper-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <!-- Bootstrap js -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>

</head>

<body>

<%-- Подключение navBar. Требуется подключение соответствующих скриптов bootstrap: js, jquery, css --%>
<jsp:include page="navBar.jsp"/>

<div class="container" style="width: 60vw; margin-top: 7vh">
    <div class="row">
        <div class="col-7 row justify-content-center">
            <form action="<c:url value="/department-staff" />" method="POST">


                <div class="row" style="height: 10vh">

                    <%-- Список для выбора таблицы --%>
                    <div class="form-group col-5">
                        <label for="tableSelectInput"><b>Список:</b></label>
                        <select name="selected_table" class="form-control" id="tableSelectInput">
                            <option value="departmentStaff">
                                <c:out value="штат"/>
                            </option>
                            <option value="scientists">
                                <c:out value="ученые"/>
                            </option>
                            <option value="curators">
                                <c:out value="кураторы"/>
                            </option>
                        </select>
                    </div>

                    <%-- Кнопка обновить --%>
                    <div class="form-group col-3 row align-items-center justify-content-center">
                        <input type="submit" name="get_selected_table" class="btn btn-primary"
                               style="height: 5vh; width: 120%; margin-left: 2ex" value="Обновить"/>
                    </div>
                </div>

                <c:choose>
                    <c:when test="${not empty departmentStaff}">
                        <%-- Таблица departmentStaffTable --%>
                        <div class="row align-items-start" style="max-height: 65vh; overflow-y: auto; ">
                            <label for="departmentStaffTable"><b>Штат:</b></label>
                            <table class="table table-hover" id="departmentStaffTable">
                                <thead class="thead-light">
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Фамилия</th>
                                    <th scope="col">Имя</th>
                                    <th scope="col">Отчество</th>
                                    <th scope="col">Договор</th>
                                    <th scope="col">Дата рождения</th>
                                    <th scope="col">Пол</th>
                                </tr>
                                </thead>
                                <c:forEach var="person" items="${departmentStaff}">
                                    <tbody class="">
                                    <tr>
                                        <td>
                                            <input type="radio" name="person_id" value="${person.personId}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.lastName}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.firstName}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.middleName}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.personContract}"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${person.dateOfBirth}" pattern="dd-MM-yyyy"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.sex}"/>
                                        </td>
                                    </tr>
                                    </tbody>
                                </c:forEach>
                            </table>
                        </div>
                    </c:when>
                    <c:when test="${not empty curators}">
                        <%-- Таблица curators --%>
                        <div class="row align-items-start" style="max-height: 65vh; overflow-y: auto; ">
                            <label for="curatorsTable"><b>Кураторы:</b></label>
                            <table class="table table-hover" id="curatorsTable">
                                <thead class="thead-light">
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Фамилия</th>
                                    <th scope="col">Имя</th>
                                    <th scope="col">Отчество</th>
                                    <th scope="col">Договор</th>
                                    <th scope="col">Дата рождения</th>
                                    <th scope="col">Пол</th>
                                    <th scope="col">Готов курировать новую группу</th>
                                </tr>
                                </thead>
                                <c:forEach var="person" items="${curators}">
                                    <tbody class="">
                                    <tr>
                                        <td>
                                            <input type="radio" name="person_id" value="${person.personId}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.lastName}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.firstName}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.middleName}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.personContract}"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${person.dateOfBirth}" pattern="dd-MM-yyyy"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.sex}"/>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${person.isActive}">
                                                    <c:out value="да"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="нет"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                    </tbody>
                                </c:forEach>
                            </table>
                        </div>
                    </c:when>
                    <c:when test="${not empty scientists}">
                        <%-- Таблица scientists --%>
                        <div class="row align-items-start" style="max-height: 65vh; overflow-y: auto; ">
                            <label for="scientistsTable"><b>Кураторы:</b></label>
                            <table class="table table-hover" id="scientistsTable">
                                <thead class="thead-light">
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Фамилия</th>
                                    <th scope="col">Имя</th>
                                    <th scope="col">Отчество</th>
                                    <th scope="col">Договор</th>
                                    <th scope="col">Дата рождения</th>
                                    <th scope="col">Пол</th>
                                    <th scope="col">Направления исследований</th>
                                </tr>
                                </thead>
                                <c:forEach var="person" items="${scientists}">
                                    <tbody class="">
                                    <tr>
                                        <td>
                                            <input type="radio" name="person_id" value="${person.personId}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.lastName}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.firstName}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.middleName}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.personContract}"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${person.dateOfBirth}" pattern="dd-MM-yyyy"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.sex}"/>
                                        </td>
                                        <td>
                                            <c:out value="${person.researchDirections}"/>
                                        </td>
                                    </tr>
                                    </tbody>
                                </c:forEach>
                            </table>
                        </div>
                    </c:when>
                </c:choose>


                <div class="row">
                    <div class="container">

                    </div>
                </div>


                <nav class="navbar fixed-bottom navbar-expand-lg navbar-light"
                     style="background-color: #979ba2; height: 18vh">
                    <div class="container" style="margin: 18vw; padding: 0; width: 60vw">
                        <div class="row col-8">
                            <%-- Переместить студентов в другую группу --%>

                            <div class="row align-items-end" id="moveAllStudentsTorGroup" style="height: 9vh">
                                <%-- Новый год обучения: --%>
                                <div class="form-group col-5">
                                    <label for="newYearOfStudyInput"><b>Новый год обучения:</b></label>
                                    <input type="number" name="new_year" class="form-control" id="newYearOfStudyInput"
                                           min="1900"
                                           value="${mainDataForm.selectedYear}"/>
                                </div>

                                <%-- Список групп --%>
                                <div class="form-group col-4">
                                    <label for="newGroupSelectInput"><b>Новая группа:</b></label>
                                    <select name="new_group_id" class="form-control"
                                            id="newGroupSelectInput"> <%-- Список групп --%>
                                        <c:forEach var="group" items="${mainDataForm.allGroups}">
                                            <option value="${group.groupId}">
                                                <c:out value="${group.groupName}"/>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <%-- Кнопка переместить студентов --%>
                                <div class="form-group col-3 row align-items-center">
                                    <input type="submit" name="move_group" class="btn btn-primary"
                                           value="Переместить студентов">
                                </div>
                            </div>

                            <%-- Кнопки управления --%>
                            <div class="row align-items-end" style="height: 8vh">
                                <%-- Кнопка добавить студента --%>
                                <div class="form-group col-4 align-items-center">
                                    <input type="submit" name="insert_student" class="btn btn-primary"
                                           value="Добавить студента">
                                </div>

                                <%-- Кнопка редактировать студента --%>
                                <div class="form-group col-4 align-items-center">
                                    <input type="submit" name="update_student" class="btn btn-primary"
                                           value="Редактировать студента">
                                </div>

                                <%-- Кнопка удалить студента --%>
                                <div class="form-group col-4  align-items-center ">
                                    <input type="submit" name="delete_student" class="btn btn-primary"
                                           style="width: 120%; margin-left: 2ex" value="Удалить студента">
                                </div>
                            </div>
                        </div>
                    </div>
                </nav>


            </form>
        </div>

        <div class="col-5 row align-items-center justify-content-center">
            <%-- Место для будущего контента --%>

        </div>
    </div>
</div>


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