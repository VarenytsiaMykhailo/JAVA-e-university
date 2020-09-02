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
                <c:when test="${studentDataForm.studentId==0}">
                    <h1>Добавление студента</h1>
                </c:when>
                <c:otherwise>
                    <h1>Редактирование информации о студенте</h1>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="row justify-content-center" style="margin-top: 4ex">
        <form action="<c:url value="/student-edit" />" class="justify-content-center"
              method="POST"> <%-- Вызов сервелета StudentEditPageServlet --%>
            <input type="hidden" name="student_id"
                   value="${studentDataForm.studentId}"> <%-- Для передачи student_id из studentDataForm в сервлет StudentEditPageServlet --%>

            <%-- Секция фамилии --%>
            <div class="form-group">
                <label for="lastNameInput"><b>Фамилия:</b></label>
                <input type="text" name="last_name" class="form-control" id="lastNameInput" size="50"
                       value="${studentDataForm.lastName}"/>
            </div>

            <%-- Секция имени --%>
            <div class="form-group">
                <label for="firstNameInput"><b>Имя:</b></label>
                <input type="text" name="first_name" class="form-control" id="firstNameInput" size="50"
                       value="${studentDataForm.firstName}"/>
            </div>

            <%-- Секция отчества --%>
            <div class="form-group">
                <label for="patronymicInput"><b>Отчество:</b></label>
                <input type="text" name="patronymic" class="form-control" id="patronymicInput" size="50"
                       value="${studentDataForm.patronymic}"/>

            </div>

            <%-- Секция даты рождения --%>
            <fmt:parseDate value="${studentDataForm.dateOfBirth}" var="parsedDate" pattern="dd.MM.yyyy"/>
            <div class="form-group">
                <label for="dateOfBirthInput"><b>Дата рождения:</b></label>
                <input type="date" name="date_of_birth" class="form-control" id="dateOfBirthInput" size="50"
                       value="<fmt:formatDate value="${parsedDate}"  pattern="yyyy-MM-dd"/>"/>
            </div>

            <%-- Секция выбора пола --%>
            <label for="sex"><b>Пол:</b></label>
            <div class="form-group" id="sex">
                <c:choose>
                    <c:when test="${studentDataForm.sex==0}"> <%-- Если пол известен и он мужской --%>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="0"
                                       checked/> <%-- Тогда будет выбран мужской пол, но будет возможность его изменить --%>
                                Мужской
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="1"/>
                                Женский
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="3"/>
                                Неопределенный
                            </label>
                        </div>
                    </c:when>
                    <c:when test="${studentDataForm.sex==1}"> <%-- Если пол известен и он женский --%>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="0"/>
                                Мужской
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="1"
                                       checked/> <%-- Тогда будет выбран женский пол, но будет возможность его изменить --%>
                                Женский
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="3"/>
                                Неопределенный
                            </label>
                        </div>
                    </c:when>
                    <c:otherwise> <%-- Если пол не мужской и не женский т.е. неизвестен --%>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="0"/>
                                Мужской
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="1"/>
                                Женский
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" name="sex" class="form-check-input" value="3"
                                       checked/> <%-- Тогда будет выбран неизвестный пол, но будет возможность его изменить --%>
                                Неопределенный
                            </label>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <%-- Секция выбора группы --%>
            <div class="form-group">
                <label for="groupSelectInput"><b>Группа:</b></label>
                <select name="group_id" class="form-control" id="groupSelectInput"> <%-- Список групп--%>
                    <c:forEach var="group" items="${studentDataForm.allGroups}">
                        <c:choose>
                            <c:when test="${group.groupId==studentDataForm.groupId}">
                                <option value="${group.groupId}" selected>
                                    <c:out value="${group.groupName}"/>
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="${group.groupId}">
                                    <c:out value="${group.groupName}"/>
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>


            <%-- Секция выбора года обучения --%>
            <div class="form-group">
                <label for="educationYearInput"><b>Год обучения:</b></label>
                <input type="number" name="education_year" class="form-control" id="educationYearInput" min="1900"
                       value="${studentDataForm.educationYear}"/>
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
</body>
</html>