<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String error = (String) request.getAttribute("error");
    List<?> databases = (List<?>) request.getAttribute("databases");
%>
<h1>Робота з БД</h1>
<ul>
    <li>Встановлюємо СУБД</li>
    <li>Створюємо БД та користувача для неї</li>
    <li>Додаємо залежність до драйвера БД (конектора, mysql-connector-j)</li>
    <li>Див. коментарі у DbServlet</li>
</ul>
<% if(error == null) { %>
    <p>Робота з БД успішна</p>
<% } else { %>
    <b>Виникла помилка <%= error %></b>
<% } %>

<% if( databases == null ) { %>
    <p>Дані не передані</p>
<% } else {
    for( Object str : databases ) { %>
        <p><%= str.toString() %></p>
<% } } %>
