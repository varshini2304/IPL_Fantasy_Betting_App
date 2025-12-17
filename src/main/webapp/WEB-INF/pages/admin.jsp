<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel - IPL Fantasy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🏏 IPL Fantasy League</h1>
            <h2>Admin Panel</h2>
            <p style="color: #666; margin-top: 10px;">Welcome, ${username}</p>
        </div>

        <ul class="nav-menu">
            <li><a href="${pageContext.request.contextPath}/match/admin/add">➕ Create Match</a></li>
            <li><a href="${pageContext.request.contextPath}/match/admin/list">📋 Match List</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/csv">📁 CSV Import</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">🚪 Logout</a></li>
        </ul>
    </div>
</body>
</html>
