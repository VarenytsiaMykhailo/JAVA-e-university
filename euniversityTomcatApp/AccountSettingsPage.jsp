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
    <%-- Стили всплывающего окна. Скрипты подключаются перед. Используется библиотека sweetalert2 --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/sweetalert2.min.css">
    <%-- Стили Pages.css --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/Pages.css">


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

<nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark" style="height: 6vh">
    <div class="container" style="width: 60vw">
        <a class="navbar-brand" href="main" style="font-size: 150%">
            e-university
        </a>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item dropdown" style="font-size: 110%">
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


<div class="container" style="margin-top: 9vh">
    <!-- Заголовок -->
    <div class="row justify-content-center">
        <div class="header-h1 header-h1-left">
            <h1>Панель управления аккаунтом</h1>
        </div>
    </div>
    <c:if test="${successfulMoveGroupNotification}">

    </c:if>
    <div class="row justify-content-center" style="margin-top: 3vh">
        <form action="<c:url value="/change-email" />" class="row col-6"
              method="POST">

            <div class="row col-12 align-items-start justify-content-center" style="margin-bottom: 1vw">
                <label><b>Изменение email'а.</b></label>
            </div>

            <div class="row col-6">
                <%-- Секция нового email --%>
                <div class="form-group row col-12">
                    <label for="emailInput">Введите новый email:</label>
                    <input type="email" name="new_email" class="form-control" id="emailInput"
                           required/>
                    <c:if test="${invalidEmailInput}">
                    <div class="errors" style="height: 20px; color: red">Введите корректный email!</div>
                    </c:if>
                </div>
                <%-- Секция повторения нового email --%>
                <div class="form-group row col-12">
                    <label for="emailRepeatInput">Повторите новый email:</label>
                    <input type="email" name="new_email_repeat" class="form-control"
                           id="emailRepeatInput" required/>
                    <c:if test="${emailsDontEqualsInput}">
                        <div class="errors" style="height: 20px; color: red">Email'ы не совпадают!</div>
                    </c:if>
                </div>
            </div>

            <div class="row col-6">
                <%-- Кнопка --%>
                <div class="form-group row col-12 justify-content-center align-items-center">
                    <input type="submit" class="btn btn-primary" value="Сменить email" style="width: 100%; height: 40px"/>
                </div>
            </div>
        </form>
    </div>


    <div class="row justify-content-center">
        <form action="<c:url value="/change-password" />" class="row col-6"
              method="POST">

            <div class="row col-12 align-items-start justify-content-center" style="margin-bottom: 1vw">
                <label><b>Изменение пароля.</b></label>
            </div>

            <div class="row col-6">
                <%-- Секция нового пароля --%>
                <div class="form-group row col-12">
                    <label for="passwordInput">Введите новый пароль:</label>
                    <input type="password" name="new_password" class="form-control" id="passwordInput"
                           required/>
                    <div class="errors" style="height: 20px; color: red"></div>
                </div>
                <%-- Секция повторения нового пароля --%>
                <div class="form-group row col-12">
                    <label for="passwordRepeatInput">Повторите новый пароль:</label>
                    <input type="password" name="new_password_repeat" class="form-controlt"
                           id="passwordRepeatInput" required/>
                    <div class="errors" style="height: 20px; color: red"></div>
                </div>
            </div>

            <div class="row col-6">
                <%-- Секция пароля --%>
                <div class="form-group row col-12">
                    <label for="currentPasswordInput">Введите текущий пароль:</label>
                    <input type="password" name="current_password" class="form-control" id="currentPasswordInput"
                           required/>
                    <div class="errors" style="height: 20px; color: red"></div>
                </div>
                <%-- Кнопка --%>
                <div class="form-group row col-12 justify-content-center align-items-center">
                    <input type="submit" class="btn btn-primary" value="Сменить пароль" style="width: 100%; height: 40px"/>
                </div>
            </div>
        </form>
    </div>

</div>

<%-- Скрипты выгодно подключать в конце тега body для более быстрой загрузки страницы --%>
<%-- Скрипты всплывающего окна. Используеюся библиотека sweetalert2 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/sweetalert2.min.js"></script>

<%-- Скрипт всплывающего окна для успешной регистрации --%>
<c:if test="${successfulEmailChangingNotification}">
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
            title: 'Вы успешно изменили email!',
            text: "Остаться или перейти на главную страницу?",
            showCancelButton: true,
            confirmButtonText: 'На главную',
            cancelButtonText: 'Остаться',
            reverseButtons: true,
            backdrop: 'rgb(0, 255, 102, 0.1)'
        }).then((result) => {
            if (result.value) { // Yes
                window.location = 'main';
            } else if (result.dismiss === Swal.DismissReason.cancel) { // No
                window.location = 'AccountSettingsPage.jsp';
            }
        })
    </script>
</c:if>
</body>
</html>