<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find Student</title>
</head>
<body>
    <h1>Find Student by Roll Number</h1>
    <form action="${pageContext.request.contextPath}/api/student" method="get">
      <input type="text" name="roll_number">
      <button type="submit">Search</button>
    </form>
    <a href="index.jsp">Back to Home</a>
</body>
</html>