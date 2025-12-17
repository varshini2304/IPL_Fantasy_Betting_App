<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Match List - IPL Fantasy</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body>
            <div class="container">
                <div class="header">
                    <h1>🏏 IPL Fantasy League</h1>
                    <h2>Match Management</h2>
                </div>

                <div class="card">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Teams</th>
                                <th>Start Time</th>
                                <th>Toss Time</th>
                                <th>Status</th>
                                <th>Winner</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="m" items="${matches}">
                                <tr>
                                    <td>${m.matchId}</td>
                                    <td><strong>${m.team1.teamName}</strong> vs <strong>${m.team2.teamName}</strong>
                                    </td>
                                    <td>${m.matchStartTime}</td>
                                    <td>${m.tossTime != null ? m.tossTime : 'Not Set'}</td>
                                    <td>
                                        <span
                                            style="padding: 5px 10px; border-radius: 5px; background: #667eea; color: white; font-size: 12px;">
                                            ${m.matchStatus}
                                        </span>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty m.winnerTeam}">
                                                <strong style="color: #3c3;">${m.winnerTeam.teamName}</strong>
                                            </c:when>
                                            <c:otherwise>--</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${m.winnerTeam != null}">
                                                <!-- Match Completed - Show Read-Only Results -->
                                                <div style="display: flex; flex-direction: column; gap: 10px;">
                                                    <div
                                                        style="padding: 10px; background: #e8f5e9; border-radius: 5px; border: 2px solid #4CAF50;">
                                                        <strong style="font-size: 14px; color: #2e7d32;">✅ Match
                                                            Completed</strong>
                                                        <div style="margin-top: 8px;">
                                                            <strong>Match Won By:</strong> ${m.winnerTeam.teamName}
                                                        </div>
                                                        <c:if test="${m.tossWinnerTeam != null}">
                                                            <div style="margin-top: 5px;">
                                                                <strong>Toss Won By:</strong>
                                                                ${m.tossWinnerTeam.teamName}
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <!-- Match Not Completed - Show Forms -->
                                                <div style="display: flex; flex-direction: column; gap: 10px;">
                                                    <!-- Set Toss Time -->
                                                    <form action="${pageContext.request.contextPath}/match/admin/toss"
                                                        method="post" class="form-inline">
                                                        <input type="hidden" name="matchId" value="${m.matchId}">
                                                        <input type="datetime-local" name="tossTime" required
                                                            style="width: auto;">
                                                        <button type="submit"
                                                            style="padding: 8px 15px; font-size: 14px;">Set
                                                            Toss Time</button>
                                                    </form>

                                                    <!-- Set Toss Winner (Separate from Match Result) -->
                                                    <c:choose>
                                                        <c:when test="${m.tossWinnerTeam != null}">
                                                            <div
                                                                style="padding: 10px; background: #e8f5e9; border-radius: 5px;">
                                                                <strong>Toss Won By:</strong>
                                                                ${m.tossWinnerTeam.teamName}
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form
                                                                action="${pageContext.request.contextPath}/match/admin/toss-winner"
                                                                method="post"
                                                                style="padding: 10px; border: 1px solid #ddd; border-radius: 5px; background: #fff3cd;">
                                                                <input type="hidden" name="matchId"
                                                                    value="${m.matchId}">
                                                                <label
                                                                    style="display: block; margin-bottom: 5px; font-weight: bold; font-size: 12px;">Toss
                                                                    Winner:</label>
                                                                <select name="tossWinnerTeamId" required
                                                                    style="width: 100%; padding: 5px;">
                                                                    <option value="">-- Select Toss Winner --</option>
                                                                    <option value="${m.team1.teamId}">
                                                                        ${m.team1.teamName}
                                                                    </option>
                                                                    <option value="${m.team2.teamId}">
                                                                        ${m.team2.teamName}
                                                                    </option>
                                                                </select>
                                                                <button type="submit"
                                                                    style="padding: 8px 15px; font-size: 14px; margin-top: 5px; width: 100%;">Set
                                                                    Toss Winner</button>
                                                            </form>
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <!-- Match Result Form -->
                                                    <form action="${pageContext.request.contextPath}/match/admin/result"
                                                        method="post"
                                                        style="display: flex; flex-direction: column; gap: 8px; padding: 10px; border: 1px solid #ddd; border-radius: 5px; background: #f9f9f9;">
                                                        <input type="hidden" name="matchId" value="${m.matchId}">
                                                        <div>
                                                            <label
                                                                style="display: block; margin-bottom: 5px; font-weight: bold; font-size: 12px;">Winner
                                                                Team:</label>
                                                            <select name="winnerTeamId" required
                                                                style="width: 100%; padding: 5px;">
                                                                <option value="">Select Winner</option>
                                                                <option value="${m.team1.teamId}">${m.team1.teamName}
                                                                </option>
                                                                <option value="${m.team2.teamId}">${m.team2.teamName}
                                                                </option>
                                                            </select>
                                                        </div>
                                                        <div>
                                                            <label
                                                                style="display: block; margin-bottom: 5px; font-weight: bold; font-size: 12px;">Man
                                                                of the Match:</label>
                                                            <select name="manOfTheMatch"
                                                                style="width: 100%; padding: 5px;">
                                                                <option value="">-- Select Player --</option>
                                                                <c:if test="${not empty m.team1.players}">
                                                                    <optgroup label="${m.team1.teamName}">
                                                                        <c:forEach var="player"
                                                                            items="${m.team1.players}">
                                                                            <option value="${player.playerName}">
                                                                                ${player.playerName}</option>
                                                                        </c:forEach>
                                                                    </optgroup>
                                                                </c:if>
                                                                <c:if test="${not empty m.team2.players}">
                                                                    <optgroup label="${m.team2.teamName}">
                                                                        <c:forEach var="player"
                                                                            items="${m.team2.players}">
                                                                            <option value="${player.playerName}">
                                                                                ${player.playerName}</option>
                                                                        </c:forEach>
                                                                    </optgroup>
                                                                </c:if>
                                                                <c:if
                                                                    test="${empty m.team1.players && empty m.team2.players}">
                                                                    <option value="" disabled>No players loaded. Please
                                                                        import
                                                                        players first.</option>
                                                                </c:if>
                                                            </select>
                                                        </div>
                                                        <div>
                                                            <label
                                                                style="display: block; margin-bottom: 5px; font-weight: bold; font-size: 12px;">Top
                                                                Scorer:</label>
                                                            <select name="topScorer" style="width: 100%; padding: 5px;">
                                                                <option value="">-- Select Player --</option>
                                                                <c:if test="${not empty m.team1.players}">
                                                                    <optgroup label="${m.team1.teamName}">
                                                                        <c:forEach var="player"
                                                                            items="${m.team1.players}">
                                                                            <option value="${player.playerName}">
                                                                                ${player.playerName}</option>
                                                                        </c:forEach>
                                                                    </optgroup>
                                                                </c:if>
                                                                <c:if test="${not empty m.team2.players}">
                                                                    <optgroup label="${m.team2.teamName}">
                                                                        <c:forEach var="player"
                                                                            items="${m.team2.players}">
                                                                            <option value="${player.playerName}">
                                                                                ${player.playerName}</option>
                                                                        </c:forEach>
                                                                    </optgroup>
                                                                </c:if>
                                                                <c:if
                                                                    test="${empty m.team1.players && empty m.team2.players}">
                                                                    <option value="" disabled>No players loaded. Please
                                                                        import
                                                                        players first.</option>
                                                                </c:if>
                                                            </select>
                                                        </div>
                                                        <div>
                                                            <label
                                                                style="display: block; margin-bottom: 5px; font-weight: bold; font-size: 12px;">Winning
                                                                Team Score:</label>
                                                            <input type="number" name="winningTeamScore"
                                                                placeholder="Total runs" min="0"
                                                                style="width: 100%; padding: 5px;">
                                                        </div>
                                                        <button type="submit"
                                                            style="padding: 8px 15px; font-size: 14px; background: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer;">Set
                                                            Match Result</button>
                                                    </form>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <a href="${pageContext.request.contextPath}/admin" class="back-link">← Back to Admin Panel</a>
            </div>
        </body>

        </html>