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
    <!-- Подключаем стили заголовка -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/headerStyle.css">
    <%-- Стили Pages.css --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/Pages.css">
    <%-- Стили всплывающего окна. Используеюся библиотека sweetalert2. --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/sweetalert2.min.css">

    <!-- Jquery -->
    <script
            src="https://code.jquery.com/jquery-2.2.4.js"
            integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
            crossorigin="anonymous"></script>
    <!-- Bootstrap js -->
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
<jsp:include page="navBar.jsp" />

<div class="container" style="margin-top: 9vh">

    <!-- Заголовок -->
    <div class="row justify-content-center">
        <div class="header-h1 header-h1-left">
            <c:choose>
                <c:when test="${departmentPerson.personId==0}">
                    <h1>Добавление сотрудника</h1>
                </c:when>
                <c:otherwise>
                    <h1>Редактирование информации о сотруднике</h1>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="row justify-content-center" style="margin-top: 4ex">
        <form action="<c:url value="/department-staff-edit" />" class="justify-content-center"
              method="POST"> <%-- Вызов сервелета DepartmentPersonEditPageServlet --%>
            <input type="hidden" name="person_id"
                   value="${departmentPerson.personId}"> <%-- Для передачи person_id в сервлет DepartmentPersonEditPageServlet --%>

            <%-- Секция фамилии --%>
            <div class="form-group">
                <label for="lastNameInput"><b>Фамилия:</b></label>
                <input type="text" name="last_name" class="form-control" id="lastNameInput" size="50"
                       value="${departmentPerson.lastName}"/>
            </div>

            <%-- Секция имени --%>
            <div class="form-group">
                <label for="firstNameInput"><b>Имя:</b></label>
                <input type="text" name="first_name" class="form-control" id="firstNameInput" size="50"
                       value="${departmentPerson.firstName}"/>
            </div>

            <%-- Секция отчества --%>
            <div class="form-group">
                <label for="middleNameInput"><b>Отчество:</b></label>
                <input type="text" name="middle_name" class="form-control" id="middleNameInput" size="50"
                       value="${departmentPerson.middleName}"/>

            </div>

            <%-- Секция даты рождения --%>
            <%--<fmt:parseDate value="${departmentPerson.dateOfBirth}" var="parsedDate" pattern="dd.MM.yyyy"/>--%>
            <div class="form-group">
                <label for="dateOfBirthInput"><b>Дата рождения:</b></label>
                <input type="date" name="date_of_birth" class="form-control" id="dateOfBirthInput" size="50"
                       value="<fmt:formatDate value="${departmentPerson.dateOfBirth}"  pattern="yyyy-MM-dd"/>"/>
            </div>

            <%-- Секция выбора пола --%>
            <label for="sex"><b>Пол:</b></label>
            <div class="form-group" id="sex">
                <c:choose>
                    <c:when test="${departmentPerson.sex eq 'М'.charAt(0)}"> <%-- Если пол известен и он мужской --%>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="М"
                                       checked/> <%-- Тогда будет выбран мужской пол, но будет возможность его изменить --%>
                                Мужской
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="Ж"/>
                                Женский
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="Н"/>
                                Неопределенный
                            </label>
                        </div>
                    </c:when>
                    <c:when test="${departmentPerson.sex eq 'Ж'.charAt(0)}"> <%-- Если пол известен и он женский --%>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="М"/>
                                Мужской
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="Ж"
                                       checked/> <%-- Тогда будет выбран женский пол, но будет возможность его изменить --%>
                                Женский
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="Н"/>
                                Неопределенный
                            </label>
                        </div>
                    </c:when>
                    <c:otherwise> <%-- Если пол не мужской и не женский т.е. неизвестен --%>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="М"/>
                                Мужской
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="Ж"/>
                                Женский
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="Н"
                                       checked/> <%-- Тогда будет выбран неизвестный пол, но будет возможность его изменить --%>
                                Неопределенный
                            </label>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <%-- Секция выбора договора --%>
            <div class="form-group">
                <label for="personContractInput"><b>Договор:</b></label>
                <input type="number" name="person_contract" class="form-control" id="personContractInput" min="10000"
                       value="${departmentPerson.personContract}"/>
            </div>

            <%-- Кнопки --%>
            <div class="form-group row justify-content-center">
                <input type="submit" name="apply" class="btn btn-primary col-8" value="Выполнить"/>
            </div>
            <div class="form-group row justify-content-center">
                <input type="button" name="cancel" class="btn btn-primary col-8" onclick="history.back();"
                       value="Отмена"/> <%-- history.back() - возрващает на предыдущую страницу --%>
            </div>


        </form>
    </div>
</div>

<%-- Скрипты выгодно подключать в конце тега body для более быстрой загрузки страницы --%>
<%-- Скрипты всплывающего окна. Используеюся библиотека sweetalert2 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/sweetalert2.min.js"></script>


<%-- Выводит оповещение, если сотрудник был успешно добавлен --%>
<c:if test="${successfulDepartmentPersonInsertionNotification}">
    <%-- Скрипт всплывающего окна об успешном добавлении сотрудника --%>
    <script>
        const swalWithBootstrapButtons = Swal.mixin({
            customClass: {
                confirmButton: 'btn btn-success',
                cancelButton: 'btn btn-success'
            },
            buttonsStyling: true
        })

        swalWithBootstrapButtons.fire({
            icon: 'success',
            title: 'Вы успешно добавили сотрудника в систему!',
            showCancelButton: false,
            confirmButtonText: 'На главную',
            reverseButtons: true,
            backdrop: 'rgb(0, 255, 102, 0.1)'
        }).then((result) => {
            if (result.value) { // Yes
                window.location = 'department-staff';
            }
        })
    </script>
</c:if>

<%-- Выводит оповещение, если сотрудник был успешно отредактирован --%>
<c:if test="${successfulDepartmentPersonUpdatingNotification}">
    <%-- Скрипт всплывающего окна об успешном добавлении сотрудника --%>
    <script>
        const swalWithBootstrapButtons = Swal.mixin({
            customClass: {
                confirmButton: 'btn btn-success',
                cancelButton: 'btn btn-success'
            },
            buttonsStyling: true
        })

        swalWithBootstrapButtons.fire({
            icon: 'success',
            title: 'Вы успешно изменили информацию о сотруднике!',
            showCancelButton: false,
            confirmButtonText: 'На главную',
            reverseButtons: true,
            backdrop: 'rgb(0, 255, 102, 0.1)'
        }).then((result) => {
            if (result.value) { // Yes
                window.location = 'department-staff';
            }
        })
    </script>
</c:if>

<%-- Скрипт всплывающего окна-подсказки для person_contract --%>
<c:if test="${unSuccessfulDepartmentPersonInsertionNotification}">
    <script>
        // Конфигурация всплывающих подсказок
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 8000,
            timerProgressBar: false,
            onOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })

        Toast.fire({ // Всплывающая подсказка
            icon: 'info',
            html: '<p align="center"><h5>Вы выбрали уже существующий номер договора</h5></p>' +
                '<p align="left" style="margin-left: 3ex">Просмотрите список штата</p>' +
                '<p align="left" style="margin-left: 3ex">и выберите другой номер договора</p>',
        })
    </script>
</c:if>

<%-- Скрипт всплывающего окна-подсказки для person_contract --%>
<c:if test="${unSuccessfulDepartmentPersonUpdatingNotification}">
    <script>
        // Конфигурация всплывающих подсказок
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 8000,
            timerProgressBar: false,
            onOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })

        Toast.fire({ // Всплывающая подсказка
            icon: 'info',
            html: '<p align="center"><h5>Вы выбрали уже существующий номер договора</h5></p>' +
                '<p align="left" style="margin-left: 3ex">Просмотрите список штата</p>' +
                '<p align="left" style="margin-left: 3ex">и выберите другой номер договора</p>',
        })
    </script>
</c:if>

</body>
</html>