<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Student</title>
    <script>
        function handleSubmit(event) {
            event.preventDefault();

            const student = {
                roll_number: parseInt(document.getElementById("roll_number").value),
                name: document.getElementById("name").value,
                age: document.getElementById("age").value ? parseInt(document.getElementById("age").value) : null,
                phone_number: document.getElementById("phone_number").value ? parseInt(document.getElementById("phone_number").value) : null,
                email: document.getElementById("email").value,
                address: document.getElementById("address").value
            };

            fetch(`${pageContext.request.contextPath}/api/student`, {
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
                    throw new Error('Failed to add student');
                }
                return response.text();
            })
            .then(text => {
                alert(text); // Shows "StudentName is added to Database with id = X"
                window.location.href = 'index.jsp';
            })
            .catch(error => {
                if (error.message.includes("already exists")) {
                    // Highlight roll number field for duplicate error
                    const rollField = document.getElementById("roll_number");
                    rollField.style.border = "2px solid red";
                    rollField.focus();
                }
                alert(error.message); // Shows error message from server
            });
        }
    </script>
    <style>
        input:focus {
            outline: none;
            border-color: initial;
        }
        .error {
            border: 2px solid red;
        }
    </style>
</head>
<body>
    <h1>Add New Student</h1>
    <form onsubmit="handleSubmit(event)">
        Roll Number: <input type="number" id="roll_number" required class="error"><br><br>
        Name: <input type="text" id="name" required><br><br>
        Age: <input type="number" id="age"><br><br>
        Phone: <input type="number" id="phone_number"><br><br>
        Email: <input type="email" id="email"><br><br>
        Address: <input type="text" id="address"><br><br>
        <button type="submit">Submit</button>
        <a href="index.jsp">Cancel</a>
    </form>

    <script>
        // Clear error styling when user starts typing in roll number field
        document.getElementById("roll_number").addEventListener('input', function() {
            this.style.border = "";
        });
    </script>
</body>
</html>