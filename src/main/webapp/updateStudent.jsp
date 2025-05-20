<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Student</title>
    <script>
        let currentStudent = null;

        function fetchStudent() {
            const rollNumber = document.getElementById("search_roll").value;
            if (!rollNumber) return;

            fetch(`${pageContext.request.contextPath}/api/student/${rollNumber}`)
                .then(response => {
                    if (!response.ok) throw new Error('Student not found');
                    return response.json();
                })
                .then(student => {
                    currentStudent = student;
                    document.getElementById("roll_number").value = student.roll_number;
                    document.getElementById("name").value = student.name;
                    document.getElementById("age").value = student.age;
                    document.getElementById("phone_number").value = student.phone_number;
                    document.getElementById("email").value = student.email;
                    document.getElementById("address").value = student.address;
                    document.getElementById("updateForm").style.display = 'block';
                })
                .catch(error => {
                    alert(error.message);
                    document.getElementById("updateForm").style.display = 'none';
                });
        }

        function updateStudent() {
            const updatedData = {
                roll_number: currentStudent.roll_number,
                name: document.getElementById("name").value,
                age: document.getElementById("age").value,
                phone_number: document.getElementById("phone_number").value,
                email: document.getElementById("email").value,
                address: document.getElementById("address").value
            };

            fetch(`${pageContext.request.contextPath}/api/student/${currentStudent.roll_number}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedData)
            })
            .then(response => {
                if (!response.ok) throw new Error('Update failed');
                return response.json();
            })
            .then(data => {
                alert('Student updated successfully!');
                window.location.reload();
            })
            .catch(error => {
                alert(`Error: ${error.message}`);
            });
        }
    </script>
</head>
<body>
    <h1>Update Student</h1>
    <div>
        Search Roll Number:
        <input type="number" id="search_roll">
        <button onclick="fetchStudent()">Search</button>
    </div>

    <div id="updateForm" style="display:none; margin-top:20px;">
        <h2>Edit Student Details</h2>
        <form onsubmit="event.preventDefault(); updateStudent();">
            Roll Number: <input type="number" id="roll_number" disabled><br><br>
            Name: <input type="text" id="name" required><br><br>
            Age: <input type="number" id="age"><br><br>
            Phone: <input type="text" id="phone_number"><br><br>
            Email: <input type="email" id="email"><br><br>
            Address: <input type="text" id="address"><br><br>
            <button type="submit">Update</button>
            <a href="index.jsp">Cancel</a>
        </form>
    </div>
</body>
</html>