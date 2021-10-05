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

<%-- Подключение navBar. Требуется подключение соответствующих скриптов bootstrap: js, jquery, css --%>
<jsp:include page="navBar.jsp" />

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
                    <c:if test="${invalidPasswordInput}">
                        <div class="errors" style="height: 20px; color: red">Введите корректный пароль!</div>
                    </c:if>
                </div>
                <%-- Секция повторения нового пароля --%>
                <div class="form-group row col-12">
                    <label for="passwordRepeatInput">Повторите новый пароль:</label>
                    <input type="password" name="new_password_repeat" class="form-control"
                           id="passwordRepeatInput" required/>
                    <c:if test="${passwordsDontEqualsInput}">
                        <div class="errors" style="height: 20px; color: red">Пароли не совпадают!</div>
                    </c:if>
                </div>
            </div>

            <div class="row col-6">
                <%-- Секция пароля --%>
                <div class="form-group row col-12">
                    <label for="currentPasswordInput">Введите текущий пароль:</label>
                    <input type="password" name="current_password" class="form-control" id="currentPasswordInput"
                           required/>
                    <c:if test="${incorrectCurrentPasswordInput}">
                        <div class="errors" style="height: 20px; color: red">Вы ввели неверный текущий пароль!</div>
                    </c:if>
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

<%-- Скрипт всплывающего окна для успешного изменения email --%>
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

<%-- Скрипт всплывающего окна для успешного изменения password --%>
<c:if test="${successfulPasswordChangingNotification}">
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
            title: 'Вы успешно изменили пароль!',
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

<%-- Скрипт всплывающего окна-подсказки для email --%>
<c:if test="${invalidEmailInput}">
    <script>
        // Конфигурация всплывающих подсказок
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 4000,
            timerProgressBar: false,
            onOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })

        Toast.fire({ // Всплывающая подсказка
            icon: 'info',
            html: '<p align="center"><h5>Email должен иметь вид:</h5></p>' +
                '<p align="left" style="margin-left: 3ex">example@example.com</p>' +
                '<p align="left" style="margin-left: 3ex">и обязательно содержать символ "@"</p>',
        })
    </script>
</c:if>

<%-- Скрипт всплывающего окна-подсказки для password --%>
<c:if test="${invalidPasswordInput}">
    <script>
        // Конфигурация всплывающих подсказок
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 4000,
            timerProgressBar: false,
            onOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer)
                toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
        })

        Toast.fire({ // Всплывающая подсказка
            icon: 'info',
            html: '<p align="center"><h5>Пароль должен содержать:</h5></p>' +
                '<p align="left" style="margin-left: 3ex">- Минимум 6 символов</p>' +
                '<p align="left" style="margin-left: 3ex">- Допускаются строчные и заглавные <br> латинские буквы и цифры</p>',
        })
    </script>
</c:if>
</body>
</html>