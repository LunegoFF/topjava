<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="users.jsp">Switch user</a></h3>
    <hr/>
    <jsp:useBean id="user" class="ru.javawebinar.topjava.web.SecurityUtil"/>
    <h2>Meals of user <jsp:getProperty property="authUserId" name="user" /></h2>
    <a href="meals?action=create">Add Meal</a>
    <br>
    <table border="1" cellpadding="8" cellspacing="0">
    <form action="meals" method="post">
        <thead>
        <input type="hidden" name="action" value="filter">
        <tr>
            <th>startDate</th>
            <th>endDate</th>
            <th>startTime</th>
            <th>endTime</th>
        </tr>
        </thead>
        <tr>
            <td><input type="date" name="startDate" value=""></td>
            <td><input type="date" name="endDate" value=""></td>
            <td><input type="time" name="startTime" value=""></td>
            <td><input type="time" name="endTime" value=""></td>
        </tr>
        <tr><input type="submit" value="Filter"></tr>
    </form>
    </table>
    <br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>