<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Match - IPL Fantasy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🏏 IPL Fantasy League</h1>
            <h2>Create New Match</h2>
        </div>

        <div class="card">
            <form action="${pageContext.request.contextPath}/match/admin/add" method="post">
                <div class="form-group">
                    <label for="team1Id">Team 1</label>
                    <select id="team1Id" name="team1Id" required>
                        <option value="">Select Team 1</option>
                        <c:forEach var="t" items="${teams}">
                            <option value="${t.teamId}">${t.teamName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="team2Id">Team 2</label>
                    <select id="team2Id" name="team2Id" required>
                        <option value="">Select Team 2</option>
                        <c:forEach var="t" items="${teams}">
                            <option value="${t.teamId}">${t.teamName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="startTime">Match Start Time</label>
                    <input type="datetime-local" id="startTime" name="startTime" required>
                </div>

                <button type="submit">Create Match</button>
            </form>
        </div>

        <a href="${pageContext.request.contextPath}/admin" class="back-link">← Back to Admin Panel</a>
    </div>
</body>
</html>
