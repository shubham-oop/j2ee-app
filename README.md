# j2ee-app
College backend project built using Java EE, JSP, and Servlets.

## 📘 APIs

### 🔹 Get All Students
GET http://localhost:8080/j2ee-app/api/student/list

### 🔹 Get Student by Roll Number
GET http://localhost:8080/j2ee-app/api/student?roll_number=11

### 🔹 Add New Student (Requires Admin Access)
POST http://localhost:8080/j2ee-app/api/student  
Headers:  
Content-Type: application/json  

Body:
{
    "roll_number": 6,
    "name": "NAME",
    "age": 26,
    "phone_number": 707129XXXX,
    "email": "google@gmail.com",
    "address": "Bangalore, India"
}

---

## 🖥️ JSP Pages

- http://localhost:8080/j2ee-app/index.jsp
- http://localhost:8080/j2ee-app/login.jsp
- http://localhost:8080/j2ee-app/addStudent.jsp
- http://localhost:8080/j2ee-app/findStudent.jsp




