<%@ page session="false" %> <!-- For login.jsp -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
    .login-container {
        background-color: #fff;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        width: 300px;
        text-align: center;
    }
    .login-container h2 {
        margin-bottom: 25px;
        color: #333;
    }
    .form-group {
        margin-bottom: 15px;
        text-align: left;
    }
    .form-group label {
        display: block;
        margin-bottom: 8px;
        color: #555;
    }
    .form-group input[type="text"],
    .form-group input[type="password"] {
        width: calc(100% - 20px);
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box;
    }
    .form-group input[type="submit"] {
        background-color: #007bff;
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
        width: 100%;
    }
    .form-group input[type="submit"]:hover {
        background-color: #0056b3;
    }
    .error-message {
        color: red;
        margin-top: 10px;
        text-align: center;
    }
</style>
</head>
<body>

<div class="login-container">
    <h2>User Login</h2>

    <%
        // Get any error message passed from the servlet (if you forward)
        // Or from query parameter if you redirect from Jersey
        String errorMessage = request.getParameter("error"); // Read from query param

        // Map the 'error' parameter to a user-friendly message
        if ("invalid".equals(errorMessage)) {
            errorMessage = "Invalid username or password.";
        } else if ("session_expired".equals(errorMessage)) { // Example
            errorMessage = "Your session has expired. Please login again.";
        } else {
            errorMessage = null; // Clear if no recognized error
        }

        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
        <p class="error-message"><%= errorMessage %></p>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/api/auth/login" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <input type="submit" value="Login">
        </div>
    </form>
</div>

</body>
</html>