<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Teams Points Table - IPL Fantasy</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
            <style>
                .points-table {
                    width: 100%;
                    margin: 20px 0;
                    border-collapse: collapse;
                    background: white;
                    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                    border-radius: 8px;
                    overflow: hidden;
                }

                .points-table thead {
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: white;
                }

                .points-table th {
                    padding: 15px;
                    text-align: left;
                    font-weight: bold;
                    font-size: 14px;
                    text-transform: uppercase;
                    letter-spacing: 0.5px;
                }

                .points-table td {
                    padding: 12px 15px;
                    border-bottom: 1px solid #e0e0e0;
                }

                .points-table tbody tr {
                    transition: background-color 0.2s;
                }

                .points-table tbody tr:hover {
                    background-color: #f5f5f5;
                }

                .points-table tbody tr:last-child td {
                    border-bottom: none;
                }

                .rank-cell {
                    font-weight: bold;
                    font-size: 18px;
                    color: #667eea;
                    text-align: center;
                    width: 60px;
                }

                .team-name-cell {
                    font-weight: bold;
                    font-size: 16px;
                    color: #333;
                }

                .points-cell {
                    font-weight: bold;
                    font-size: 18px;
                    color: #4CAF50;
                    text-align: center;
                }

                .stat-cell {
                    text-align: center;
                    font-size: 15px;
                    color: #555;
                }

                .won-cell {
                    color: #4CAF50;
                    font-weight: bold;
                }

                .lost-cell {
                    color: #f44336;
                    font-weight: bold;
                }

                .drawn-cell {
                    color: #ff9800;
                    font-weight: bold;
                }

                .rank-1 {
                    background: linear-gradient(135deg, #FFD700 0%, #FFA500 100%);
                    color: white;
                }

                .rank-1 .rank-cell,
                .rank-1 .team-name-cell,
                .rank-1 .points-cell,
                .rank-1 .stat-cell {
                    color: white;
                }

                .rank-2 {
                    background: linear-gradient(135deg, #C0C0C0 0%, #A0A0A0 100%);
                    color: white;
                }

                .rank-2 .rank-cell,
                .rank-2 .team-name-cell,
                .rank-2 .points-cell,
                .rank-2 .stat-cell {
                    color: white;
                }

                .rank-3 {
                    background: linear-gradient(135deg, #CD7F32 0%, #B87333 100%);
                    color: white;
                }

                .rank-3 .rank-cell,
                .rank-3 .team-name-cell,
                .rank-3 .points-cell,
                .rank-3 .stat-cell {
                    color: white;
                }

                .medal-icon {
                    font-size: 24px;
                    margin-right: 8px;
                }

                .table-header {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    margin-bottom: 20px;
                }

                .table-title {
                    font-size: 24px;
                    font-weight: bold;
                    color: #333;
                }
            </style>
        </head>

        <body>
            <div class="container">
                <div class="header">
                    <h1>🏏 IPL Fantasy League</h1>
                    <h2>Teams Points Table</h2>
                </div>

                <ul class="nav-menu">
                    <li><a href="${pageContext.request.contextPath}/dashboard">🏠 Dashboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/match/upcoming">📅 Upcoming Matches</a></li>
                    <li><a href="${pageContext.request.contextPath}/my-predictions">🎯 My Predictions</a></li>
                    <li><a href="${pageContext.request.contextPath}/leaderboard">🏆 Leaderboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/teams-points">📊 Teams Points</a></li>
                    <li><a href="${pageContext.request.contextPath}/profile">👤 Profile</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout">🚪 Logout</a></li>
                </ul>

                <div class="card">
                    <div class="table-header">
                        <div class="table-title">📊 IPL Teams Standings</div>
                    </div>

                    <c:if test="${not empty teamStatistics}">
                        <table class="points-table">
                            <thead>
                                <tr>
                                    <th style="text-align: center; width: 80px;">Rank</th>
                                    <th>Team Name</th>
                                    <th style="text-align: center;">Played</th>
                                    <th style="text-align: center;">Won</th>
                                    <th style="text-align: center;">Lost</th>
                                    <th style="text-align: center;">Drawn</th>
                                    <th style="text-align: center;">Points</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="stats" items="${teamStatistics}" varStatus="status">
                                    <tr class="rank-${status.index + 1 <= 3 ? status.index + 1 : ''}">
                                        <td class="rank-cell">
                                            <c:choose>
                                                <c:when test="${status.index + 1 == 1}">
                                                    <span class="medal-icon">🥇</span> ${status.index + 1}
                                                </c:when>
                                                <c:when test="${status.index + 1 == 2}">
                                                    <span class="medal-icon">🥈</span> ${status.index + 1}
                                                </c:when>
                                                <c:when test="${status.index + 1 == 3}">
                                                    <span class="medal-icon">🥉</span> ${status.index + 1}
                                                </c:when>
                                                <c:otherwise>
                                                    ${status.index + 1}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="team-name-cell">${stats.team.teamName}</td>
                                        <td class="stat-cell">${stats.matchesPlayed}</td>
                                        <td class="stat-cell won-cell">${stats.matchesWon}</td>
                                        <td class="stat-cell lost-cell">${stats.matchesLost}</td>
                                        <td class="stat-cell drawn-cell">${stats.matchesDrawn}</td>
                                        <td class="points-cell">${stats.team.currentPoints}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>

                    <c:if test="${empty teamStatistics}">
                        <div style="text-align: center; padding: 40px; color: #666;">
                            <p style="font-size: 18px;">No teams data available.</p>
                        </div>
                    </c:if>
                </div>
            </div>
        </body>

        </html>