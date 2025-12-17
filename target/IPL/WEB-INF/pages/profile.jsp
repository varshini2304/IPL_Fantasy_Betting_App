<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile - IPL Fantasy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🏏 IPL Fantasy League</h1>
            <h2>My Profile</h2>
        </div>

        <div class="card">
            <div class="stats-box">
                <div class="stat-item">
                    <h3>👤</h3>
                    <p>Username</p>
                    <p style="font-size: 18px; margin-top: 10px;">${user.username}</p>
                </div>
                <div class="stat-item">
                    <h3>📧</h3>
                    <p>Email</p>
                    <p style="font-size: 18px; margin-top: 10px;">${user.email}</p>
                </div>
                <div class="stat-item">
                    <h3>🔑</h3>
                    <p>Role</p>
                    <p style="font-size: 18px; margin-top: 10px;">${user.userType}</p>
                </div>
                <div class="stat-item">
                    <h3>📅</h3>
                    <p>Member Since</p>
                    <p style="font-size: 18px; margin-top: 10px;">${user.createdAt}</p>
                </div>
            </div>

            <a href="${pageContext.request.contextPath}/dashboard" class="back-link">← Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
