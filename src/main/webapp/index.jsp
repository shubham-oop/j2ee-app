<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.config.SecurityConfig" %>
<%
    // Check if user is logged in
    boolean isLoggedIn = session != null && session.getAttribute(SecurityConfig.AUTH_USER) != null;

    // Redirect to login if not authenticated
    if (!isLoggedIn) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Student Management System</title>
    <style>
        .menu {
            margin: 20px auto;
            margin-top: 100px;
            padding: 20px;
            border: 1px solid #ddd;
            max-width: 500px;
            text-align: center;
        }
        .btn {
            display: inline-block;
            padding: 10px 15px;
            margin: 5px;
            background: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .user-info {
            float: right;
            margin: 10px;
            padding: 5px 10px;
            background: #f5f5f5;
            border-radius: 4px;
        }
        .logout-btn {
            color: #d9534f;
            text-decoration: none;
            margin-left: 10px;
        }
    </style>
</head>
<body>
    <div class="user-info">
        Welcome, <%= session.getAttribute(SecurityConfig.AUTH_USER) %>
        <form action="${pageContext.request.contextPath}/api/auth/logout" method="post" style="display:inline;">
            <button type="submit" class="logout-btn">Logout</button>
        </form>
    </div>

    <div class="menu">
        <h1>Student Management</h1>
        <a href="addStudent.jsp" class="btn">Add New Student</a>
        <a href="findStudent.jsp" class="btn">Find Student</a>
        <a href="updateStudent.jsp" class="btn">Update Student</a>
    </div>

    <script>
        // Auto-redirect to login if session expires
        if (<%= !isLoggedIn %>) {
            window.location.href = 'login.jsp';
        }
    </script>
</body>
</html>