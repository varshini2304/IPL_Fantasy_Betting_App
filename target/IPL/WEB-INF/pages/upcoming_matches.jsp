<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Upcoming Matches - IPL Fantasy</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body>
            <div class="container">
                <div class="header">
                    <h1>🏏 IPL Fantasy League</h1>
                    <h2>Upcoming Matches</h2>
                </div>

                <c:choose>
                    <c:when test="${not empty matches}">
                        <c:forEach var="m" items="${matches}">
                            <div class="match-card">
                                <div class="team-name">${m.team1.teamName}</div>
                                <div class="match-vs">VS</div>
                                <div class="team-name">${m.team2.teamName}</div>
                                <div class="match-info">
                                    <p><strong>Start Time:</strong> ${m.matchStartTime}</p>
                                    <p><strong>Toss Time:</strong> ${m.tossTime != null ? m.tossTime : 'TBD'}</p>
                                </div>
                                <div style="text-align: center; margin-top: 15px;">
                                    <a href="${pageContext.request.contextPath}/predict/${m.matchId}"
                                        class="btn">Predict Winner</a>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="card">
                            <div class="empty-state">
                                <h3>📅</h3>
                                <p>No upcoming matches at the moment</p>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>

                <a href="${pageContext.request.contextPath}/dashboard" class="back-link">← Back to Dashboard</a>
            </div>
        </body>

        </html>