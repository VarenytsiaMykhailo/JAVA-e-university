<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Требуется подключение соответствующих скриптов bootstrap: js, jquery, css в вызывающем файле --%>

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
                        <a class="dropdown-item hoverBlack" style="color: aliceblue" href="department-staff">Штат</a>

                        <a class="dropdown-item hoverBlack" style="color: aliceblue" href="AccountSettingsPage.jsp">Настройки
                            аккаунта</a>

                        <c:if test="${role eq 'ADMIN'}">
                            <a class="dropdown-item hoverBlack" style="color: aliceblue" href="registration">Регистрация
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