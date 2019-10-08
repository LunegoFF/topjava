<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<hr>
<table border=1>
    <thead>
    <tr>
        <th>ID</th>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Excess</th>
        <th colspan="2"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr>
            <form name="updateForm" method="get">
                <input type="hidden" name="action" value="update"/>
                <td><input type="text" name="mealId" value="<c:out value="${meal.id}" />" /></td>
                <td><input type="datetime-local" name="date" value="<c:out value="${meal.dateTime}" />" /></td>
                <td><input type="text" name="description" value="<c:out value="${meal.description}" />" /></td>
                <td><input type="text" name="calories" value="<c:out value="${meal.calories}" />" /></td>
                <td><c:out value="${meal.excess}"/></td>
                <td><button type="submit" formaction="/topjava/meals">Update</button></td>
            </form>

                <td><button type="submit" onclick="location.href='/topjava/meals?action=delete&mealId=${meal.id}'">Delete</button></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<hr>
<form method="POST" name="AddForm">
    <label>ID : </label><input type="text" name="id" value="<c:out value="${meal.id}" />" /> <br />
    <label>Date/Time : </label><input type="datetime-local" name="date" value="<c:out value="${meal.date}" />" /> <br />
    <label>Description : </label><input type="text" name="description" value="<c:out value="${meal.description}" />" /> <br />
    <label>Calories : </label><input type="text" name="calories" value="<c:out value="${meal.calories}" />" /> <br />
    <input type="submit" value="Add" />
</form>
</body>
</html>