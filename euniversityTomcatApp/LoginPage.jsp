<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>e-university</title>
</head>

<body>

<div id="loginForm">
    <form action="" method="POST">
        <h1>Вход в систему e-university</h1>
        <p>
            <strong>Логин:</strong>
            <input type="text" name="login" maxlength="30" size="40" placeholder="введите логин" required>
        </p>
        <p>
            <strong>Пароль:</strong>
            <input type="password" name="password" maxlength="30" size="40" placeholder="введите пароль" required>
        </p>
        <p>
            <input type="submit" class="submitButton" value="Войти">
        </p>
    </form>
</div>
</body>
</html>