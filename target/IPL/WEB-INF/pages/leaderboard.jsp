<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Leaderboard - IPL Fantasy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🏏 IPL Fantasy League</h1>
            <h2>🏆 Leaderboard</h2>
        </div>

        <div class="card">
            <table>
                <thead>
                    <tr>
                        <th>Rank</th>
                        <th>Username</th>
                        <th>Total Points</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="row" items="${leaderboard}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${row.rank == 1}">🥇</c:when>
                                    <c:when test="${row.rank == 2}">🥈</c:when>
                                    <c:when test="${row.rank == 3}">🥉</c:when>
                                    <c:otherwise>#${row.rank}</c:otherwise>
                                </c:choose>
                            </td>
                            <td><strong>${row.username}</strong></td>
                            <td><strong style="color: #ff6b35; font-size: 18px;">${row.totalPoints}</strong></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <a href="${pageContext.request.contextPath}/dashboard" class="back-link">← Back to Dashboard</a>
    </div>
</body>
</html>
