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

    <div class="card" style="margin-bottom: 20px; text-align: center;">
        <h3>👤 Your Position</h3>

        <c:set var="currentUser" value="${sessionScope.user.username}" />

        <c:set var="yourRank" value="-" />
        <c:set var="yourPoints" value="0" />

        <c:set var="rankCalc" value="0" />
        <c:set var="prevPts" value="-1" />

        <c:forEach var="row" items="${leaderboard}" varStatus="status">
            <c:if test="${row.totalPoints != prevPts}">
                <c:set var="rankCalc" value="${status.index + 1}" />
            </c:if>

            <c:if test="${row.username eq currentUser}">
                <c:set var="yourRank" value="${rankCalc}" />
                <c:set var="yourPoints" value="${row.totalPoints}" />
            </c:if>

            <c:set var="prevPts" value="${row.totalPoints}" />
        </c:forEach>

        <div style="font-size: 22px; margin-top: 10px;">
            🏆 Rank:
            <strong style="color:#667eea;">${yourRank}</strong>
        </div>

        <div style="font-size: 18px; margin-top: 5px;">
            ⭐ Points:
            <strong style="color:#ff6b35;">${yourPoints}</strong>
        </div>
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

            <c:set var="rank" value="0" />
            <c:set var="prevPoints" value="-1" />

            <c:forEach var="row" items="${leaderboard}" varStatus="status">

                <c:if test="${row.totalPoints != prevPoints}">
                    <c:set var="rank" value="${status.index + 1}" />
                </c:if>

                <tr
                    style="${row.username eq currentUser ? 'background:#eef4ff;font-weight:bold;' : ''}">
                    <td>${rank}</td>
                    <td>
                        ${row.username}
                        <c:if test="${row.username eq currentUser}">
                            <span style="color:#667eea;"> (You)</span>
                        </c:if>
                    </td>
                    <td>
                        <strong style="color:#ff6b35; font-size: 18px;">
                            ${row.totalPoints}
                        </strong>
                    </td>
                </tr>

                <c:set var="prevPoints" value="${row.totalPoints}" />

            </c:forEach>

            </tbody>
        </table>
    </div>

    <a href="${pageContext.request.contextPath}/dashboard" class="back-link">
        ← Back to Dashboard
    </a>

</div>
</body>
</html>
