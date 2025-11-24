<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Change Password</title>
  </head>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      min-height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    h2 {
      color: rgb(255, 255, 255);
      margin-bottom: 20px;
    }
    form {
      background: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
      width: 100%;
      max-width: 400px;
    }
    form div {
      margin-bottom: 15px;
    }
    form label {
      display: block;
      margin-bottom: 5px;
      font-weight: 600;
      color: #333;
    }
    form input {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
      font-size: 14px;
    }
    form button {
      width: 100%;
      padding: 12px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
      border: none;
      border-radius: 5px;
      font-size: 16px;
      font-weight: 600;
      cursor: pointer;
      transition: transform 0.2s;
    }
    form button:hover {
      transform: translateY(-2px);
    }
    a {
      display: block;
      margin-top: 15px;
      color: white;
      text-align: center;
      text-decoration: none;
      font-weight: 600;
    }
    .btn-back {
      width: 100%;
      padding: 12px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
      border: none;
      border-radius: 5px;
      font-size: 16px;
      font-weight: 600;
      cursor: pointer;
      transition: transform 0.2s;
      text-align: center;
      margin-top: 10px;
    }

    .btn-back:hover {
      transform: translateY(-2px);
    }
  </style>
  <body>
    <div class="forgot-container">
      <div>
        <h2>Change Password</h2>
      </div>
      <!-- Display error message -->
      <c:if test="${not empty error}">
        <div style="color: red">${error}</div>
      </c:if>

      <!-- Display success message -->
      <c:if test="${not empty message}">
        <div style="color: green">${message}</div>
      </c:if>

      <form action="change-password" method="post">
        <div>
          <label>Current Password:</label>
          <input
            type="password"
            name="currentPassword"
            placeholder="Current Password"
          />
        </div>
        <div>
          <label>New Password (min 8 characters):</label>
          <input
            type="password"
            name="newPassword"
            placeholder="New Password"
            minlength="8"
          />
        </div>
        <div>
          <label>Confirm New Password:</label>
          <input
            type="password"
            name="confirmPassword"
            placeholder="Confirm Password"
          />
        </div>
        <button type="submit">Change Password</button>
      </form>
      <div>
        <a href="login" class="btn-back">Back to Login</a>
      </div>
    </div>
  </body>
</html>
