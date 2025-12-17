<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Dashboard - IPL Fantasy</title>
                    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
                    <style>
                        .current-matches {
                            margin: 20px 0;
                        }

                        .match-status-badge {
                            display: inline-block;
                            padding: 5px 12px;
                            border-radius: 20px;
                            font-size: 12px;
                            font-weight: bold;
                            text-transform: uppercase;
                        }

                        .status-upcoming {
                            background: #4CAF50;
                            color: white;
                        }

                        .status-live {
                            background: #f44336;
                            color: white;
                            animation: pulse 2s infinite;
                        }

                        .status-completed {
                            background: #2196F3;
                            color: white;
                        }

                        @keyframes pulse {

                            0%,
                            100% {
                                opacity: 1;
                            }

                            50% {
                                opacity: 0.7;
                            }
                        }

                        .match-item {
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            padding: 15px;
                            margin: 10px 0;
                            background: #f9f9f9;
                            border-radius: 8px;
                            border-left: 4px solid #667eea;
                        }

                        .match-info {
                            flex: 1;
                        }

                        .match-teams {
                            font-weight: bold;
                            font-size: 16px;
                            margin-bottom: 5px;
                        }

                        .match-time {
                            color: #666;
                            font-size: 14px;
                        }
                    </style>
                </head>

                <body>
                    <div class="container">
                        <div class="header">
                            <h1>🏏 IPL Fantasy League</h1>
                            <h2>Welcome, ${username}!</h2>
                        </div>

                        <ul class="nav-menu">
                            <li><a href="${pageContext.request.contextPath}/match/upcoming">📅 Upcoming Matches</a></li>
                            <li><a href="${pageContext.request.contextPath}/my-predictions">🎯 My Predictions</a></li>
                            <li><a href="${pageContext.request.contextPath}/leaderboard">🏆 Leaderboard</a></li>
                            <li><a href="${pageContext.request.contextPath}/profile">👤 Profile</a></li>
                            <li><a href="${pageContext.request.contextPath}/logout">🚪 Logout</a></li>
                        </ul>
                        <!-- Current Matches Section -->
                        <c:if test="${not empty currentMatches}">
                            <div class="card current-matches">
                                <h2 style="margin-top: 0;">📊 Current Matches</h2>
                                <c:forEach var="match" items="${currentMatches}">
                                    <div class="match-item">
                                        <div class="match-info">
                                            <div class="match-teams">
                                                ${match.team1.teamName} vs ${match.team2.teamName}
                                            </div>
                                            <div class="match-time">
                                                <c:if test="${match.matchStartTime != null}">
                                                    <c:set var="dateTimeStr"
                                                        value="${match.matchStartTime.toString()}" />
                                                    <c:set var="formattedDate"
                                                        value="${fn:replace(dateTimeStr, 'T', ' ')}" />
                                                    ${formattedDate}
                                                </c:if>
                                            </div>
                                        </div>
                                        <div>
                                            <c:choose>
                                                <c:when test="${match.matchStatus == 'UPCOMING'}">
                                                    <span class="match-status-badge status-upcoming">⏰ Starting
                                                        Soon</span>
                                                </c:when>
                                                <c:when test="${match.matchStatus == 'LIVE'}">
                                                    <span class="match-status-badge status-live">🔴 LIVE</span>
                                                </c:when>
                                                <c:when test="${match.matchStatus == 'COMPLETED'}">
                                                    <span class="match-status-badge status-completed">✅ Completed</span>
                                                    <c:if test="${match.winnerTeam != null}">
                                                        <div style="margin-top: 5px; font-size: 12px; color: #666;">
                                                            Winner: ${match.winnerTeam.teamName}
                                                        </div>
                                                    </c:if>
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>

                    </div>
                </body>

                </html>