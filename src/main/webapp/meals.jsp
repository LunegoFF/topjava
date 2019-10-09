<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h1><a href="index.html">Home</a></h1>
<hr>
<h2>Meals</h2>

<hr>
<table border=1>
    <thead>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr bgcolor=${meal.value.excess ? "red" : "green"}>
            <form name="updateForm" method="post">
                <input type="hidden" name="id" value="${meal.key}" />
                <td><input type="datetime-local" name="date" value="${meal.value.dateTime}" /></td>
                <td><input type="text" name="description" value="${meal.value.description}" /></td>
                <td><input type="text" name="calories" value="${meal.value.calories}" /></td>
                <td><input type="submit" value="Update" /></td>
            </form>
            <form name="deleteForm" method="get">
                <input type="hidden" name="id" value="${meal.key}" />
                <td><input type="submit" value="Delete"/></td>
            </form>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form name="AddForm" method="post">
    <label>Date/Time : </label><input type="datetime-local" name="date" /><br />
    <label>Description : </label><input type="text" name="description" /><br />
    <label>Calories : </label><input type="text" name="calories" /><br />
    <input type="submit" value="Add" />
</form>
</body>
</html>