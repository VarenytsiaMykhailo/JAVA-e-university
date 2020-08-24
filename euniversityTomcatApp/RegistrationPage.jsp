<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>e-university</title>
</head>

<body>

<div id="registrationForm">
    <form action="<c:url value="/registration" />" method="POST"> <%-- Вызов сервелета RegistrationPageServlet --%>

        <%-- Секция логина --%>
        <table class="registrationFormTable">
            <tr>
                <td>Введите логин:</td>
                <td>
                    <input type="text" name="login" placeholder="введите логин" required/>
                </td>
            </tr>
            <tr>
                <td>Повторите логин:</td>
                <td>
                    <input type="text" name="login_repeat" placeholder="повторите логин" required/>
                </td>
            </tr>
        </table>

        <p></p>

        <%-- Секция емаила --%>
        <table class="registrationFormTable">
            <tr>
                <td>Введите e-mail:</td>
                <td>
                    <input type="text" name="email" placeholder="введите e-mail" required/>
                </td>
            </tr>
            <tr>
                <td>Повторите e-mail:</td>
                <td>
                    <input type="text" name="email_repeat" placeholder="повторите e-mail" required/>
                </td>
            </tr>
        </table>

        <p></p>

        <table class="registrationFormTable">  <%-- Секция пароля --%>
            <tr>
                <td>Введите пароль:</td>
                <td>
                    <input type="text" name="password" placeholder="введите пароль" required/>
                </td>
            </tr>
            <tr>
                <td>Повторите пароль:</td>
                <td>
                    <input type="text" name="password_repeat" placeholder="повторите пароль" required/>
                </td>
            </tr>
        </table>

        <p></p>

        <table class="registrationFormTable">  <%-- Секция типа учетной записи --%>
            <tr>
                <td>Тип учетной записи:</td>
                <td>
                    <select name="role"> <%-- Список доступных типов учетной записи--%>
                        <option value="user" selected>
                            <c:out value="пользователь"/>
                        </option>
                        <option value="admin">
                            <c:out value="администратор"/>
                        </option>
                    </select>
                </td>
            </tr>
        </table>

        <p></p>

        <table class="registrationFormTable">
            <tr>
                <td>
                    <input type="submit" value="Зарегистрировать">
                </td>
                <td>
                    <input type="button" name="cancel" onclick="history.back();" value="Отмена"> <%-- history.back() - возрващает на предыдущую страницу --%>
                </td>
            </tr>
        </table>

    </form>

</div>
</body>
</html>