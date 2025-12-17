<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login - IPL Fantasy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🏏 IPL Fantasy League</h1>
            <h2>🔐 Admin Login</h2>
        </div>

        <div class="card">
            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/admin/login" method="post">
                <div class="form-group">
                    <label for="username">Admin Username</label>
                    <input type="text" id="username" name="username" required placeholder="Enter admin username">
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required placeholder="Enter admin password">
                </div>

                <button type="submit">Admin Login</button>
            </form>

            <div style="margin-top: 20px; text-align: center;">
                <a href="${pageContext.request.contextPath}/login" class="back-link">← Back to User Login</a>
            </div>
        </div>
    </div>
</body>
</html>

