<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        h1 { color: #333; }
        .message {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin-bottom: 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
        }
        th {
            background-color: #007bff;
            color: white;
            padding: 12px;
            text-align: left;
        }
        td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        tr:hover { background-color: #f8f9fa; }
        .action-link {
            color: #007bff;
            text-decoration: none;
            margin-right: 10px;
        }
        .delete-link { color: #dc3545; }

        .search-box{
            display : flex;
            align-items: center;
        }
         .search-box form {
            display: flex;
            gap: 8px;
            align-items: center;
            background: #fff;
            padding: 8px;
            border-radius: 6px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.08);
        }
        .search-box input[type="text"] {
            padding: 8px;
            border-radius: 4px;
            border: 1px solid #ccc;
            min-width: 240px;
        }
        .search-box button, .search-box .clear-btn {
            padding: 8px 12px;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
        }
        .search-box .clear-btn {
            background-color: #6c757d;
        }
    </style>
</head>
<body>
     <div class="navbar">
        <h2>📚 Student Management System</h2>
        <div class="navbar-right">
            <div class="user-info">
                <span>Welcome, ${sessionScope.fullName}</span>
                <span class="role-badge role-${sessionScope.role}">
                    ${sessionScope.role}
                </span>
            </div>
            <a href="dashboard" class="btn-nav">Dashboard</a>
            <a href="logout" class="btn-logout">Logout</a>
        </div>
    </div>
    <c:if test="${not empty param.message}">
        <div class="message success">
            ${param.message}
        </div>
    </c:if>
    
    <c:if test="${not empty param.error}">
        <div class="message error">
            ${param.error}
        </div>
    </c:if>
    <div class ="search-box">
        <form action = "student" method="get">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" placeholder="Search by name or code" value="${keyword != null ? keyword : ''}">
            <button type="submit">🔍 Search</button>
            <c:if test="${not empty keyword}">
                <p> Search results for "${keyword}" </p>
                <a href="student?action=list" class="clear-btn">❌ Clear</a>
            </c:if>
        </form>
    </div>
    <div class="container">
        <h1>📚 Student List</h1>
        
        <!-- Add button - Admin only -->
        <c:if test="${sessionScope.role eq 'admin'}">
            <div style="margin: 20px 0;">
                <a href="student?action=new" class="btn-add">➕ Add New Student</a>
            </div>
        </c:if>
    <div class="filter-box">
    <form action="student" method="get">
        <input type="hidden" name="action" value="filter">
        <label>Filter by Major:</label>
        <select name="major">
            <option value="">All Majors</option>
            <option value="Computer Science"  ${selectedMajor == 'Computer Science' ? 'selected' : ''} >Computer Science</option>
            <option value="Information Technology" ${selectedMajor == 'Information Technology' ? 'selected' : ''}>Information Technology</option>
            <option value="Software Engineering" ${selectedMajor == 'Software Engineering' ? 'selected' : ''}>Software Engineering</option>
            <option value="Business Administration" ${selectedMajor == 'Business Administration' ? 'selected' : ''}>Business Administration</option>
        </select>
        <button type="submit">Apply Filter</button>
        <c:if test="${not empty selectedMajor}">
            <a href="student?action=list">Clear Filter</a>
        </c:if>
    </form>
    </div>

    <table>
        <thead>
            <tr>
        <th><a href="student?action=sort&sortBy=id&order=${sortBy == 'id' && order == 'asc' ? 'desc' : 'asc'}">ID</a>
        <c:if test="${sortBy == 'id'}">
                ${order == 'asc' ? '▲' : '▼'}
            </c:if>
        </th>
        <th><a href="student?action=sort&sortBy=student_code&order=${sortBy == 'student_code' && order == 'asc' ? 'desc' : 'asc'}">Student Code</a>
        <c:if test="${sortBy == 'student_code'}">
                ${order == 'asc' ? '▲' : '▼'}
            </c:if>
        </th>
        <th><a href="student?action=sort&sortBy=full_name&order=${sortBy == 'full_name' && order == 'asc' ? 'desc' : 'asc'}">Full Name</a>
          <c:if test="${sortBy == 'full_name'}">
                ${order == 'asc' ? '▲' : '▼'}
            </c:if>
        </th>
        <th><a href="student?action=sort&sortBy=email&order=${sortBy == 'email' && order == 'asc' ? 'desc' : 'asc'}">Email</a>
        <c:if test="${sortBy == 'email'}">
                ${order == 'asc' ? '▲' : '▼'}
            </c:if>
        </th>
        <th><a href="student?action=sort&sortBy=major&order=${sortBy == 'major' && order == 'asc' ? 'desc' : 'asc'}">Major</a>
         <c:if test="${sortBy == 'major'}">
                ${order == 'asc' ? '▲' : '▼'}
            </c:if>
        </th>
        <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="student" items="${students}">
                <tr>
                    <td>${student.id}</td>
                    <td>${student.studentCode}</td>
                    <td>${student.fullName}</td>
                    <td>${student.email != null ? student.email : 'N/A'}</td>
                    <td>${student.major != null ? student.major : 'N/A'}</td>
                   <c:if test="${sessionScope.role eq 'admin'}">
    <td>
        <a href="student?action=edit&id=${student.id}">Edit</a>
        <a href="student?action=delete&id=${student.id}">Delete</a>
    </td>
</c:if>
                </tr>
            </c:forEach>
            
            <c:if test="${empty students}">
                <tr>
                    <td colspan="6" style="text-align: center;">
                        No students found.
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>
</body>
</html>
