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
    <%-- Стили MainPage.css --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/MainPage.css">


    <script
            src="https://code.jquery.com/jquery-2.2.4.js"
            integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>

</head>

<body style="margin-top: 8ex">

<nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark" style="height: 6ex">
    <div class="container">
        <a class="navbar-brand" href="#" style="font-size: 160%">
            e-university
        </a>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item dropdown" style="font-size: 115%">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Опции
                    </a>
                    <div class="dropdown-menu bg-dark bg-light:hover" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item hoverBlack" style="color: aliceblue" href="AccountSettingsPage.jsp">Настройки
                            аккаунта</a>

                        <c:if test="${role eq 'ADMIN'}">
                            <a class="dropdown-item hoverBlack" style="color: aliceblue" href="RegistrationPage.jsp">Регистрация
                                нового пользователя</a>
                        </c:if>

                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item hoverBlack" style="color: aliceblue" href="#">В реализации</a>
                    </div>
                </li>
            </ul>

            <span class="navbar-text" style="padding-right: 2ex; font-size: 120%">
                <c:if test="${role eq 'ADMIN'}">
                    <b>Администратор:</b> ${login}
                </c:if>
                <c:if test="${role eq 'USER'}">
                    <b>Пользователь:</b> ${login}
                </c:if>
            </span>

            <span>
                <button class="btn btn-outline-info" type="button" onclick="window.location = 'logout';">Выход</button>
            </span>

        </div>
    </div>
</nav>


<div class="container">
    <div class="row">
        <div class="col-7 row justify-content-center">
            <form action="<c:url value="/main" />" method="POST">

                <div class="row" style="height: 10ex">
                    <%-- Год обучения --%>
                    <div class="form-group col-5">
                        <label for="yearOfStudyInput"><b>Год обучения:</b></label>
                        <input type="number" name="selected_year" class="form-control" id="yearOfStudyInput" min="1900"
                               value="${mainDataForm.selectedYear}"/>
                    </div>

                    <%-- Список групп --%>
                    <div class="form-group col-5">
                        <label for="groupSelectInput"><b>Список групп:</b></label>
                        <select name="selected_group_id" class="form-control" id="groupSelectInput">
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
                    </div>

                    <%-- Кнопка обновить --%>
                    <div class="form-group col-2 row align-items-center justify-content-center">
                        <input type="submit" name="get_students_list" class="btn btn-primary"
                               style="height: 6ex; width: 120%; margin-left: 4ex" value="Обновить"/>
                    </div>
                </div>


                <%-- Таблица --%>
                <div class="row align-items-start" style="max-height: 78ex; overflow-y: auto; ">
                    <label for="studentsTable"><b>Список студентов для выбранных параметров:</b></label>
                    <table class="table table-hover" id="studentsTable">
                        <thead class="thead-light">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Фамилия</th>
                            <th scope="col">Имя</th>
                            <th scope="col">Отчество</th>
                            <th scope="col">Дата рождения</th>
                            <th scope="col">Пол</th>
                        </tr>
                        </thead>
                        <c:forEach var="student" items="${mainDataForm.studentsForSelectedGroup}">
                            <tbody class="">
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
                            </tbody>
                        </c:forEach>
                    </table>
                </div>

                <div class="row">
                    <div class="container">

                    </div>
                </div>


                <nav class="navbar fixed-bottom navbar-expand-lg navbar-light"
                     style="background-color: #979ba2; height: 21ex">
                    <div class="container">
                        <div class="col-7">
                            <%-- Переместить студентов в другую группу --%>
                            <label for="moveAllStudentsTorGroup"><b>Переместить студентов в другую группу:</b></label>
                            <div class="row align-items-end " id="moveAllStudentsTorGroup" style="height: 10ex">
                                <%-- Новый год обучения: --%>
                                <div class="form-group col-4">
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
                                <div class="form-group col-4 row align-items-center justify-content-center">
                                    <input type="submit" name="move_group" class="btn btn-primary"
                                           value="Переместить студентов">
                                </div>
                            </div>

                            <%-- Кнопки управления --%>
                            <div class="row" style="height: 4ex">
                                <%-- Кнопка добавить студента --%>
                                <div class="form-group col-4 row align-items-center justify-content-center">
                                    <input type="submit" name="insert_student" class="btn btn-primary"
                                           value="Добавить студента">
                                </div>

                                <%-- Кнопка редактировать студента --%>
                                <div class="form-group col-4 row align-items-center justify-content-center">
                                    <input type="submit" name="update_student" class="btn btn-primary"
                                           value="Редактировать студента">
                                </div>

                                <%-- Кнопка удалить студента --%>
                                <div class="form-group col-4 row align-items-center justify-content-center">
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
            <img src="static/images/content.jpg" style="height: 50%; width: 90%">
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