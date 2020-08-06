<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/hello.tld" prefix="jstlpg" %>
<html>
    <head>
        <title>
            Hello World Sample
        </title>
    </head>

    <body>
        <h1>
            <jstlpg:hello name='<%= request.getParameter("name") %>' />
        </h1>
    </body>
</html>