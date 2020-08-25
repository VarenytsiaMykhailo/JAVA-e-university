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
                <label for="loginInput">Введите логин:</label>
                <input type="text" name="login" class="form-control" id="loginInput" aria-describedby="emailHelp" />
                <div class="errors" style="height: 20px; color: red"></div>
            </div>
            <%-- Секция повторения логина --%>
            <div class="form-group col-6">
                <label for="loginRepeatInput">Повторите логин:</label>
                <input type="text" name="login_repeat" class="form-control" id="loginRepeatInput"
                       aria-describedby="emailHelp" />
                <div class="errors" style="height: 20px; color: red"></div>
            </div>


            <%-- Секция емаила --%>
            <div class="clearfix"></div> <%-- Скрытый блок. Следующий начнется с новой строки --%>
            <div class="form-group col-6">
                <label for="emailInput">Введите email:</label>
                <input type="email" name="email" class="form-control" id="emailInput" aria-describedby="emailHelp" />
                <div class="errors" style="height: 20px; color: red"></div>
            </div>
            <%-- Секция повторения емаила --%>
            <div class="form-group col-6">
                <label for="emailRepeatInput">Повторите email:</label>
                <input type="email" name="email_repeat" class="form-control" id="emailRepeatInput"
                       aria-describedby="emailHelp" />
                <div class="errors" style="height: 20px; color: red"></div>
            </div>


            <%-- Секция пароля --%>
            <div class="clearfix"></div> <%-- Скрытый блок. Следующий начнется с новой строки --%>
            <div class="form-group col-6">
                <label for="passwordInput">Введите пароль:</label>
                <input type="password" name="password" class="form-control formPassword" id="passwordInput" />
                <div class="errors" style="height: 20px; color: red"></div>
            </div>
            <%-- Секция повторения пароля --%>
            <div class="form-group col-6">
                <label for="passwordRepeatInput">Повторите пароль:</label>
                <input type="password" name="password_repeat" class="form-control formPasswordRepeat"
                       id="passwordRepeatInput" />
                <div class="errors" style="height: 20px; color: red"></div>
            </div>


            <%-- Секция выбора типа учетной записи --%>
            <div class="clearfix"></div> <%-- Скрытый блок. Следующий начнется с новой строки --%>
            <div class="form-group col-12 row justify-content-center" style="margin-bottom: 6ex">
                <label for="roleSelect" class="col-7">Укажите тип учетной записи:</label>
                <select name="role" class="form-control col-7"
                        id="roleSelect"> <%-- Список доступных типов учетной записи --%>
                    <option value="user" selected>
                        <c:out value="пользователь"/>
                    </option>
                    <option value="admin">
                        <c:out value="администратор"/>
                    </option>
                </select>
                <div class="errors" style="height: 20px; color: red"></div>
            </div>

            <div class="clearfix"></div> <%-- Скрытый блок. Следующий начнется с новой строки --%>
            <div class="form-group col-6 row justify-content-center">
                <button type="submit" class="btn btn-primary col-10">Зарегистрировать</button>
            </div>
            <div class="form-group col-6 row justify-content-center">
                <button type="button" name="cancel" class="btn btn-primary col-10" onclick="history.back();">Отмена
                </button>
                <%-- history.back() - возрващает на предыдущую страницу --%>
            </div>

        </form>
    </div>
</div>

<%-- Скрипты выгодно подключать в конце тега body для более быстрой загрузки страницы --%>
<%-- подключаем скрипт валидации введенных данных --%>
<script src="${pageContext.request.contextPath}/static/scripts/registrationValidator.js"></script>
<%-- Скрипты всплывающего окна. Используеюся библиотека sweetalert2 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/sweetalert2.min.js"></script>
</body>
</html>