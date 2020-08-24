<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>e-university</title>
</head>

<body>

<div id="loginForm">
    <form action="" method="POST">
        <h1>Вход в систему e-university</h1>

        <p>
            <strong>Логин:</strong>
            <br>
            <input type="text" name="login" maxlength="30" size="40" placeholder="введите логин" required/>
        </p>
        <p>
            <strong>Пароль:</strong>
            <br>
            <input type="password" name="password" maxlength="30" size="40" placeholder="введите пароль" required/>
        </p>
        <p>
            <c:if test="${incorrectLoginPassword}">
                Вы ввели неверный логин или пароль.
            </c:if>
        </p>
        <p>
            <input type="submit" value="Войти"/>
        </p>
        <p>
            Для регистрации обратитесь к администратору.
        </p>

    </form>
</div>
</body>
</html>