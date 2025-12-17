<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Match - IPL Fantasy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <style>
        .error-text {
            color: red;
            font-size: 14px;
            margin-top: 6px;
            display: none;
        }
    </style>
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

                <div id="teamError" class="error-text">
                    Team 1 and Team 2 cannot be the same
                </div>

                <div class="form-group">
                    <label for="startTime">Match Start Time</label>
                    <input type="datetime-local" id="startTime" name="startTime" required>
                </div>

                <button type="submit">Create Match</button>
            </form>
        </div>

        <a href="${pageContext.request.contextPath}/admin" class="back-link">
            ← Back to Admin Panel
        </a>
    </div>

    <script>
        const team1 = document.getElementById("team1Id");
        const team2 = document.getElementById("team2Id");
        const form = document.querySelector("form");
        const errorMsg = document.getElementById("teamError");

        function updateDropdown(changed, other) {
            const selectedValue = changed.value;

            Array.from(other.options).forEach(option => {
                option.disabled = false;
            });

            if (selectedValue) {
                Array.from(other.options).forEach(option => {
                    if (option.value === selectedValue) {
                        option.disabled = true;
                    }
                });
            }

            validateTeams();
        }

        function validateTeams() {
            if (team1.value && team2.value && team1.value === team2.value) {
                errorMsg.style.display = "block";
                return false;
            } else {
                errorMsg.style.display = "none";
                return true;
            }
        }

        team1.addEventListener("change", () => updateDropdown(team1, team2));
        team2.addEventListener("change", () => updateDropdown(team2, team1));

        form.addEventListener("submit", function (e) {
            if (!validateTeams()) {
                e.preventDefault();
            }
        });
    </script>

</body>
</html>
