<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<form action="users" method="post">
    <label>Choose the User:</label>
    <select name="userId">
        <option value="1">User 1</option>
        <option value="2">User 2</option>
    </select>
    <input type="submit" value="Set user"><a href="meals">Look at meal</a>
</form>
</body>
</html>