<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>My Predictions - IPL Fantasy</title>
                    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
                    <style>
                        .locked-badge {
                            display: inline-block;
                            padding: 4px 10px;
                            background: #f44336;
                            color: white;
                            border-radius: 12px;
                            font-size: 11px;
                            font-weight: bold;
                            margin-left: 10px;
                        }

                        .editable-badge {
                            display: inline-block;
                            padding: 4px 10px;
                            background: #4CAF50;
                            color: white;
                            border-radius: 12px;
                            font-size: 11px;
                            font-weight: bold;
                            margin-left: 10px;
                        }

                        .prediction-details {
                            font-size: 12px;
                            color: #666;
                            margin-top: 5px;
                        }

                        .action-cell {
                            white-space: nowrap;
                        }
                    </style>
                </head>

                <body>
                    <div class="container">
                        <div class="header">
                            <h1>🏏 IPL Fantasy League</h1>
                            <h2>My Predictions</h2>
                        </div>

                        <div class="card">
                            <c:choose>
                                <c:when test="${not empty predictions}">
                                    <table>
                                        <thead>
                                            <tr>
                                                <th>Match</th>
                                                <th>Predictions</th>
                                                <th>Points Awarded</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="p" items="${predictions}">
                                                <tr>
                                                    <td>
                                                        <strong>${p.match.team1.teamName}</strong> vs
                                                        <strong>${p.match.team2.teamName}</strong>
                                                        <div class="prediction-details">
                                                            <c:if test="${p.match.matchStartTime != null}">
                                                                <c:set var="dateTimeStr"
                                                                    value="${p.match.matchStartTime.toString()}" />
                                                                <c:set var="formattedDate"
                                                                    value="${fn:replace(dateTimeStr, 'T', ' ')}" />
                                                                ${formattedDate}
                                                            </c:if>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div><strong>Winner:</strong> ${p.predictedTeam.teamName}</div>
                                                        <c:if test="${p.predictedTossWinner != null}">
                                                            <div class="prediction-details">Toss:
                                                                ${p.predictedTossWinner.teamName}</div>
                                                        </c:if>
                                                        <c:if test="${not empty p.predictedTopScorer}">
                                                            <div class="prediction-details">Top Scorer:
                                                                ${p.predictedTopScorer}</div>
                                                        </c:if>
                                                        <c:if test="${not empty p.predictedManOfTheMatch}">
                                                            <div class="prediction-details">MoM:
                                                                ${p.predictedManOfTheMatch}
                                                            </div>
                                                        </c:if>
                                                        <c:if
                                                            test="${p.predictedTotalRunsMin != null && p.predictedTotalRunsMax != null}">
                                                            <div class="prediction-details">Runs:
                                                                ${p.predictedTotalRunsMin}-${p.predictedTotalRunsMax}
                                                            </div>
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${p.pointsAwarded > 0}">
                                                                <span
                                                                    style="color: #3c3; font-weight: bold;">+${p.pointsAwarded}</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span style="color: #999;">${p.pointsAwarded != null ?
                                                                    p.pointsAwarded : 0}</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        ${p.match.matchStatus}
                                                        <c:choose>
                                                            <c:when
                                                                test="${p.locked || p.match.matchStatus == 'COMPLETED' || p.match.matchStatus == 'LIVE'}">
                                                                <span class="locked-badge">🔒 Locked</span>
                                                            </c:when>
                                                            <c:when test="${p.match.matchStatus == 'UPCOMING'}">
                                                                <span class="editable-badge">✏️ Editable</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="locked-badge">🔒 Locked</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="action-cell">
                                                        <c:choose>
                                                            <c:when
                                                                test="${p.locked || p.match.matchStatus == 'COMPLETED' || p.match.matchStatus == 'LIVE'}">
                                                                <span style="color: #999;">-</span>
                                                            </c:when>
                                                            <c:when test="${p.match.matchStatus == 'UPCOMING'}">
                                                                <a href="${pageContext.request.contextPath}/predict/${p.match.matchId}"
                                                                    class="btn"
                                                                    style="padding: 5px 15px; font-size: 12px;">Edit</a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span style="color: #999;">-</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <div class="empty-state">
                                        <h3>🎯</h3>
                                        <p>No predictions yet</p>
                                        <p style="margin-top: 15px;">
                                            <a href="${pageContext.request.contextPath}/match/upcoming" class="btn">Make
                                                Your First Prediction</a>
                                        </p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <a href="${pageContext.request.contextPath}/dashboard" class="back-link">← Back to Dashboard</a>
                    </div>
                </body>

                </html>