<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>e-university</title>
    <!-- Настройка viewport -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Подключаем Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <!-- Подключаем стили заголовка -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/headerStyle.css">
    <%-- Стили всплывающего окна. Скрипты подключаются перед /body --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/sweetalert2.min.css">
</head>

<body>

<div class="container">

    <!-- Заголовок -->
    <div class="row justify-content-center">
        <div class="header-h1 header-h1-left" style="margin-top: 4ex">
            <h1>Регистрация пользователя</h1>
        </div>
    </div>

    <div class="row justify-content-center" style="margin-top: 6ex">
        <form action="<c:url value="/registration"/> "
              class="col-6 row justify-content-center" method="POST"> <%-- Вызов сервелета RegistrationPageServlet --%>

            <%-- Секция логина --%>
            <div class="form-group col-6">
                <label for="loginInput"><b>Введите логин:</b></label>
                <input type="text" name="login" class="form-control" id="loginInput" aria-describedby="emailHelp"/>
                <div class="errors" style="height: 20px; color: red"></div>
            </div>
            <%-- Секция повторения логина --%>
            <div class="form-group col-6">
                <label for="loginRepeatInput"><b>Повторите логин:</b></label>
                <input type="text" name="login_repeat" class="form-control" id="loginRepeatInput"
                       aria-describedby="emailHelp"/>
                <div class="errors" style="height: 20px; color: red"></div>
            </div>


            <%-- Секция емаила --%>
            <div class="clearfix"></div>
            <%-- Скрытый блок. Следующий начнется с новой строки --%>
            <div class="form-group col-6">
                <label for="emailInput"><b>Введите email:</b></label>
                <input type="email" name="email" class="form-control" id="emailInput" aria-describedby="emailHelp"/>
                <div class="errors" style="height: 20px; color: red"></div>
            </div>
            <%-- Секция повторения емаила --%>
            <div class="form-group col-6">
                <label for="emailRepeatInput"><b>Повторите email:</b></label>
                <input type="email" name="email_repeat" class="form-control" id="emailRepeatInput"
                       aria-describedby="emailHelp"/>
                <div class="errors" style="height: 20px; color: red"></div>
            </div>


            <%-- Секция пароля --%>
            <div class="clearfix"></div>
            <%-- Скрытый блок. Следующий начнется с новой строки --%>
            <div class="form-group col-6">
                <label for="passwordInput"><b>Введите пароль:</b></label>
                <input type="password" name="password" class="form-control formPassword" id="passwordInput"/>
                <div class="errors" style="height: 20px; color: red"></div>
            </div>
            <%-- Секция повторения пароля --%>
            <div class="form-group col-6">
                <label for="passwordRepeatInput"><b>Повторите пароль:</b></label>
                <input type="password" name="password_repeat" class="form-control formPasswordRepeat"
                       id="passwordRepeatInput"/>
                <div class="errors" style="height: 20px; color: red"></div>
            </div>


            <%-- Секция выбора типа учетной записи --%>
            <div class="clearfix"></div>
            <%-- Скрытый блок. Следующий начнется с новой строки --%>
            <div class="form-group col-12 row justify-content-center" style="margin-bottom: 6ex">
                <label for="roleSelect" class="col-7"><b>Укажите тип учетной записи:</b></label>
                <select name="role" class="form-control col-7"
                        id="roleSelect"> <%-- Список доступных типов учетной записи --%>
                    <option value="USER" selected>
                        <c:out value="пользователь"/>
                    </option>
                    <option value="ADMIN">
                        <c:out value="администратор"/>
                    </option>
                </select>
                <div class="errors" style="height: 20px; color: red"></div>
            </div>

            <div class="clearfix"></div>
            <%-- Скрытый блок. Следующий начнется с новой строки --%>
            <div class="form-group col-6 row justify-content-center">
                <button type="submit" class="btn btn-primary col-10">Зарегистрировать</button>
            </div>
            <div class="form-group col-6 row justify-content-center">
                <button type="button" name="cancel" class="btn btn-primary col-10" onclick="window.location = 'main';">
                    Отмена
                </button>
                <%-- history.back() - возрващает на предыдущую страницу --%>
            </div>

        </form>
    </div>
</div>

<%-- Скрипты выгодно подключать в конце тега body для более быстрой загрузки страницы --%>
<%-- Скрипты всплывающего окна. Используеюся библиотека sweetalert2 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/sweetalert2.min.js"></script>
<%-- подключаем скрипт валидации введенных данных --%>
<%-- !!! Так как registrationValidator.js использует код из sweetalert2.min.js, то этот скрипт нужно подключать после подключения sweetalert2.min.js! --%>
<script src="${pageContext.request.contextPath}/static/scripts/registrationValidator.js"></script>

<c:if test="${successfulRegistrationNotification}">
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
            title: 'Вы успешно зарегистрировали нового пользователя в системе!',
            text: "Зарегистрировать нового или перейти на главную страницу?",
            showCancelButton: true,
            confirmButtonText: 'На главную',
            cancelButtonText: 'Регистрация',
            reverseButtons: true,
            backdrop: 'rgb(0, 255, 102, 0.1)'
        }).then((result) => {
            if (result.value) { // Yes
                window.location = 'main';
            } else if (result.dismiss === Swal.DismissReason.cancel) { // No
                window.location = 'RegistrationPage.jsp';
            }
        })
    </script>
</c:if>

<c:if test="${unsuccessfulRegistrationNotification}">
    <script>
        const swalWithBootstrapButtons = Swal.mixin({
            customClass: {
                confirmButton: 'btn btn-success',
                cancelButton: 'btn btn-success'
            },
            buttonsStyling: true
        })

        swalWithBootstrapButtons.fire({
            icon: 'error',
            title: 'Регистрация не удалась!',
            html: 'Пользователь с логином <b>${unsuccessfulRegistrationLogin}</b> уже существует. <br>Зарегистрировать нового или перейти на главную страницу?',
            showCancelButton: true,
            confirmButtonText: 'Регистрация',
            cancelButtonText: 'На главную',
            reverseButtons: false,
            backdrop: 'rgba(255, 0, 0, 0.1)'
        }).then((result) => {
            if (result.value) { // Yes
                window.location = 'RegistrationPage.jsp';
            } else if (result.dismiss === Swal.DismissReason.cancel) { // No
                window.location = 'main';
            }
        })
    </script>
</c:if>
</body>
</html>