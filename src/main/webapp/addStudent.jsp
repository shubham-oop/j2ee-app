<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ page import="com.example.config.SecurityConfig" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    boolean isLoggedIn = session != null && session.getAttribute(SecurityConfig.AUTH_USER) != null;

    if (!isLoggedIn) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Student</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
            margin: 0;
            padding-top: 50px;
            box-sizing: border-box;
        }

        .container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
            text-align: center;
        }

        h1 {
            color: #333;
            margin-bottom: 30px;
            font-size: 2em;
            font-weight: 600;
        }

        .form-group {
            margin-bottom: 20px;
            text-align: left;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-weight: 500;
            font-size: 0.95em;
        }

        .form-group input[type="text"],
        .form-group input[type="number"],
        .form-group input[type="email"] {
            width: calc(100% - 22px);
            padding: 12px 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 1em;
            transition: border-color 0.2s;
        }

        .form-group input:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.25);
        }

        .error-field {
            border: 2px solid #dc3545 !important;
        }

        .button-group {
            margin-top: 30px;
            display: flex;
            justify-content: center;
            gap: 15px;
        }

        button[type="submit"],
        .btn-cancel {
            padding: 12px 25px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 1em;
            font-weight: 600;
            transition: background-color 0.2s ease, transform 0.1s ease;
        }

        button[type="submit"] {
            background-color: #28a745;
            color: white;
        }

        button[type="submit"]:hover {
            background-color: #218838;
            transform: translateY(-1px);
        }

        .btn-cancel {
            background-color: #6c757d; /* Gray for cancel */
            color: white;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }

        .btn-cancel:hover {
            background-color: #5a6268;
            transform: translateY(-1px);
        }

        /* Responsive adjustments */
        @media (max-width: 600px) {
            .container {
                margin: 20px;
                padding: 30px 20px;
            }
            .button-group {
                flex-direction: column;
                gap: 10px;
            }
            button[type="submit"], .btn-cancel {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Add New Student</h1>
        <form onsubmit="handleSubmit(event)">
            <div class="form-group">
                <label for="roll_number">Roll Number:</label>
                <input type="number" id="roll_number" required>
            </div>

            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" required>
            </div>

            <div class="form-group">
                <label for="age">Age:</label>
                <input type="number" id="age">
            </div>

            <div class="form-group">
                <label for="phone_number">Phone:</label>
                <input type="number" id="phone_number">
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email">
            </div>

            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address">
            </div>

            <div class="form-group">
                <label for="studentClass">Class:</label>
                <select id="studentClass" required>
                    <option value="">-- Select Class --</option>
                    <c:forEach var="sClass" items="${studentClasses}">
                        <option value="${sClass.dbValue}">${sClass.dbValue}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="button-group">
                <button type="submit">Submit</button>
                <a href="index.jsp" class="btn-cancel">Cancel</a>
            </div>
        </form>
    </div>
    <script>
        <c:set var="CONTEXT_PATH" value="${pageContext.request.contextPath}"/>
        async function populateStudentClassDropdown() {
            const studentClassSelect = document.getElementById("studentClass");
            try {
                const response = await fetch(`${CONTEXT_PATH}/api/classes`);
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const classes = await response.json();

                classes.forEach(classValue => {
                    const option = document.createElement("option");
                    option.value = classValue;
                    option.textContent = classValue; // Display the same value
                    studentClassSelect.appendChild(option);
                });
            } catch (error) {
                console.error("Error fetching student classes:", error);
                alert("Could not load class options. Please try again.");
            }
        }

        window.onload = populateStudentClassDropdown;


        function handleSubmit(event) {
            event.preventDefault();

            const studentClassSelect = document.getElementById("studentClass");
            const selectedClassValue = studentClassSelect.value;

            if (!selectedClassValue) {
                alert("Please select a student class.");
                return;
            }

            const rollNumberInput = document.getElementById("roll_number");
            const student = {
                roll_number: parseInt(rollNumberInput.value),
                name: document.getElementById("name").value,
                age: document.getElementById("age").value ? parseInt(document.getElementById("age").value) : null,
                phone_number: document.getElementById("phone_number").value ? parseInt(document.getElementById("phone_number").value) : null,
                email: document.getElementById("email").value,
                address: document.getElementById("address").value,
                studentClass: selectedClassValue
            };

            rollNumberInput.classList.remove("error-field");
            fetch(`${CONTEXT_PATH}/api/student`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(student)
            })
            .then(response => {
                if (response.status === 409) {
                    return response.text().then(text => {
                        throw new Error(text);
                    });
                }
                if (!response.ok) {
                    throw new Error('Failed to add student. Status: ' + response.status);
                }
                return response.text();
            })
            .then(text => {
                alert(text);
                window.location.href = `${CONTEXT_PATH}/index.jsp`;
            })
            .catch(error => {
                if (error.message.includes("already exists")) {
                    rollNumberInput.classList.add("error-field");
                    rollNumberInput.focus();
                }
                alert(error.message);
            });
        }

        document.getElementById("roll_number").addEventListener('input', function() {
            this.classList.remove("error-field");
        });
    </script>
</body>
</html>