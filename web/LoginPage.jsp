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
</head>

<body>

<div class="container">
    <!-- Заголовок -->
    <div class="row justify-content-center">
        <div class="header-h1 header-h1-left" style="margin-top: 6ex">
            <h1>Вход в систему e-university</h1>
        </div>
    </div>

    <div class="row justify-content-center" style="margin-top: 6ex">
        <form action="" class="justify-content-center"
              method="POST"> <%-- Запрос перехватит фильтр и если авторизация выполнится, он переотправит на /main сервелет MainPage --%>


            <input type="hidden" name="is_login_action"
                   value="${true}"> <%-- Для передачи student_id из studentDataForm в сервлет StudentEditPageServlet --%>

            <%-- Секция логина --%>
            <div class="form-group">
                <label for="loginInput"><b>Введите логин:</b></label>
                <input type="text" name="login" class="form-control" id="loginInput" size="50" required/>
            </div>

            <%-- Секция пароля --%>
            <div class="form-group">
                <label for="passwordInput"><b>Введите пароль:</b></label>
                <input type="password" name="password" class="form-control" id="passwordInput" size="50" required/>
            </div>

            <%-- Секция ошибки (если пользователь введет неверные данные) --%>
            <div class="errors" style="height: 20px; color: red; margin-bottom: 3ex">
                <c:if test="${incorrectLoginPassword}">
                    Вы ввели неверный логин или пароль.
                </c:if>
            </div>

            <div class="form-group row justify-content-center">
                <input type="submit" class="btn btn-primary col-8" value="Войти"/>
            </div>

            <div class="form-group row justify-content-center">
                Для регистрации обратитесь к администратору.
            </div>


        </form>
    </div>
</div>
</body>
</html>