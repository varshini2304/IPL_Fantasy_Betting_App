<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Predict Winner - IPL Fantasy</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
                <style>
                    .countdown {
                        text-align: center;
                        padding: 15px;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white;
                        border-radius: 8px;
                        margin: 20px 0;
                        font-size: 18px;
                        font-weight: bold;
                    }

                    .countdown.expired {
                        background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
                    }

                    .countdown-timer {
                        font-size: 24px;
                        margin: 10px 0;
                    }

                    .prediction-section {
                        margin: 20px 0;
                        padding: 15px;
                        border: 1px solid #ddd;
                        border-radius: 8px;
                    }

                    .prediction-section h3 {
                        margin-top: 0;
                        color: #333;
                    }

                    .form-group {
                        margin: 15px 0;
                    }

                    .form-group label {
                        display: block;
                        margin-bottom: 5px;
                        font-weight: bold;
                        color: #555;
                    }

                    .form-group input[type="text"],
                    .form-group input[type="number"],
                    .form-group select {
                        width: 100%;
                        padding: 10px;
                        border: 1px solid #ddd;
                        border-radius: 4px;
                        font-size: 14px;
                    }

                    .form-group input[type="number"] {
                        width: 48%;
                        display: inline-block;
                    }

                    .form-group .range-separator {
                        display: inline-block;
                        width: 4%;
                        text-align: center;
                        font-weight: bold;
                    }

                    .existing-prediction {
                        background: #e8f5e9;
                        padding: 10px;
                        border-radius: 4px;
                        margin: 10px 0;
                    }
                </style>
            </head>

            <body>
                <div class="container">
                    <div class="header">
                        <h1>🏏 IPL Fantasy League</h1>
                        <h2>Make Your Predictions</h2>
                    </div>

                    <div class="card">
                        <div class="match-card">
                            <div class="team-name">${match.team1.teamName}</div>
                            <div class="match-vs">VS</div>
                            <div class="team-name">${match.team2.teamName}</div>
                        </div>

                        <!-- Countdown Timer -->
                        <div class="countdown" id="countdown">
                            <div>⏰ Time Remaining to Predict:</div>
                            <div class="countdown-timer" id="countdown-timer">Calculating...</div>
                        </div>

                        <c:if test="${not empty existing}">
                            <div class="existing-prediction">
                                <strong>📋 Your Current Predictions:</strong>
                                <div style="margin-top: 10px; line-height: 1.8;">
                                    <div><strong>Winner:</strong> ${existing.predictedTeam.teamName}</div>
                                    <c:if test="${existing.predictedTossWinner != null}">
                                        <div><strong>Toss Winner:</strong> ${existing.predictedTossWinner.teamName}
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty existing.predictedTopScorer}">
                                        <div><strong>Top Scorer:</strong> ${existing.predictedTopScorer}</div>
                                    </c:if>
                                    <c:if test="${not empty existing.predictedManOfTheMatch}">
                                        <div><strong>Man of the Match:</strong> ${existing.predictedManOfTheMatch}</div>
                                    </c:if>
                                    <c:if
                                        test="${existing.predictedTotalRunsMin != null && existing.predictedTotalRunsMax != null}">
                                        <div><strong>Total Runs:</strong>
                                            ${existing.predictedTotalRunsMin}-${existing.predictedTotalRunsMax}</div>
                                    </c:if>
                                </div>
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/predict/save" method="post"
                            id="predictionForm">
                            <input type="hidden" name="matchId" value="${match.matchId}">

                            <!-- Match Winner Prediction -->
                            <div class="prediction-section">
                                <h3>🏆 Match Winner</h3>
                                <div class="prediction-buttons">
                                    <button type="button" name="teamId" value="${match.team1.teamId}"
                                        class="btn ${existing != null && existing.predictedTeam.teamId == match.team1.teamId ? 'btn-selected' : ''}"
                                        onclick="selectTeam('${match.team1.teamId}', this)">
                                        ${match.team1.teamName}
                                    </button>
                                    <button type="button" name="teamId" value="${match.team2.teamId}"
                                        class="btn btn-secondary ${existing != null && existing.predictedTeam.teamId == match.team2.teamId ? 'btn-selected' : ''}"
                                        onclick="selectTeam('${match.team2.teamId}', this)">
                                        ${match.team2.teamName}
                                    </button>
                                </div>
                                <input type="hidden" name="teamId" id="selectedTeamId"
                                    value="${existing != null ? existing.predictedTeam.teamId : ''}" required>
                            </div>

                            <!-- Toss Winner Prediction -->
                            <div class="prediction-section">
                                <h3>🪙 Toss Winner</h3>
                                <c:choose>
                                    <c:when test="${match.tossWinnerTeam != null}">
                                        <div
                                            style="padding: 10px; background: #fff3cd; border-radius: 5px; margin-bottom: 10px;">
                                            <strong>⚠️ Toss Result Updated:</strong> ${match.tossWinnerTeam.teamName}
                                            won the toss. Predictions are now locked.
                                        </div>
                                        <input type="hidden" name="tossWinnerId" id="selectedTossId"
                                            value="${existing != null && existing.predictedTossWinner != null ? existing.predictedTossWinner.teamId : ''}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="prediction-buttons">
                                            <button type="button"
                                                class="btn ${existing != null && existing.predictedTossWinner != null && existing.predictedTossWinner.teamId == match.team1.teamId ? 'btn-selected' : ''}"
                                                onclick="selectToss('${match.team1.teamId}', this)">
                                                ${match.team1.teamName}
                                            </button>
                                            <button type="button"
                                                class="btn btn-secondary ${existing != null && existing.predictedTossWinner != null && existing.predictedTossWinner.teamId == match.team2.teamId ? 'btn-selected' : ''}"
                                                onclick="selectToss('${match.team2.teamId}', this)">
                                                ${match.team2.teamName}
                                            </button>
                                        </div>
                                        <input type="hidden" name="tossWinnerId" id="selectedTossId"
                                            value="${existing != null && existing.predictedTossWinner != null ? existing.predictedTossWinner.teamId : ''}">
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <!-- Top Scorer -->
                            <div class="prediction-section">
                                <h3>⚡ Top Scorer</h3>
                                <div class="form-group">
                                    <label for="topScorer">Select Player:</label>
                                    <!-- Debug: Team1 players count: ${team1Players.size()}, Team2 players count: ${team2Players.size()} -->
                                    <select id="topScorer" name="topScorer">
                                        <option value="">-- Select Player --</option>
                                        <c:choose>
                                            <c:when test="${not empty team1Players}">
                                                <optgroup label="${match.team1.teamName}">
                                                    <c:forEach var="player" items="${team1Players}">
                                                        <option value="${player.playerName}" ${existing !=null &&
                                                            existing.predictedTopScorer !=null &&
                                                            existing.predictedTopScorer==player.playerName ? 'selected'
                                                            : '' }>
                                                            ${player.playerName}
                                                        </option>
                                                    </c:forEach>
                                                </optgroup>
                                            </c:when>
                                            <c:otherwise>
                                                <optgroup label="${match.team1.teamName}">
                                                    <option value="" disabled>No players loaded for this team</option>
                                                </optgroup>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${not empty team2Players}">
                                                <optgroup label="${match.team2.teamName}">
                                                    <c:forEach var="player" items="${team2Players}">
                                                        <option value="${player.playerName}" ${existing !=null &&
                                                            existing.predictedTopScorer !=null &&
                                                            existing.predictedTopScorer==player.playerName ? 'selected'
                                                            : '' }>
                                                            ${player.playerName}
                                                        </option>
                                                    </c:forEach>
                                                </optgroup>
                                            </c:when>
                                            <c:otherwise>
                                                <optgroup label="${match.team2.teamName}">
                                                    <option value="" disabled>No players loaded for this team</option>
                                                </optgroup>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </div>

                            <!-- Man of the Match -->
                            <div class="prediction-section">
                                <h3>⭐ Man of the Match</h3>
                                <div class="form-group">
                                    <label for="manOfTheMatch">Select Player:</label>
                                    <select id="manOfTheMatch" name="manOfTheMatch">
                                        <option value="">-- Select Player --</option>
                                        <c:choose>
                                            <c:when test="${not empty team1Players}">
                                                <optgroup label="${match.team1.teamName}">
                                                    <c:forEach var="player" items="${team1Players}">
                                                        <option value="${player.playerName}" ${existing !=null &&
                                                            existing.predictedManOfTheMatch !=null &&
                                                            existing.predictedManOfTheMatch==player.playerName
                                                            ? 'selected' : '' }>
                                                            ${player.playerName}
                                                        </option>
                                                    </c:forEach>
                                                </optgroup>
                                            </c:when>
                                            <c:otherwise>
                                                <optgroup label="${match.team1.teamName}">
                                                    <option value="" disabled>No players loaded for this team</option>
                                                </optgroup>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${not empty team2Players}">
                                                <optgroup label="${match.team2.teamName}">
                                                    <c:forEach var="player" items="${team2Players}">
                                                        <option value="${player.playerName}" ${existing !=null &&
                                                            existing.predictedManOfTheMatch !=null &&
                                                            existing.predictedManOfTheMatch==player.playerName
                                                            ? 'selected' : '' }>
                                                            ${player.playerName}
                                                        </option>
                                                    </c:forEach>
                                                </optgroup>
                                            </c:when>
                                            <c:otherwise>
                                                <optgroup label="${match.team2.teamName}">
                                                    <option value="" disabled>No players loaded for this team</option>
                                                </optgroup>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </div>

                            <!-- Total Runs Range -->
                            <div class="prediction-section">
                                <h3>📊 Total Runs (Range)</h3>
                                <div class="form-group">
                                    <label>Runs Range:</label>
                                    <input type="number" name="totalRunsMin" id="totalRunsMin" placeholder="Min" min="0"
                                        value="${existing != null && existing.predictedTotalRunsMin != null ? existing.predictedTotalRunsMin : ''}">
                                    <span class="range-separator">-</span>
                                    <input type="number" name="totalRunsMax" id="totalRunsMax" placeholder="Max" min="0"
                                        value="${existing != null && existing.predictedTotalRunsMax != null ? existing.predictedTotalRunsMax : ''}">
                                </div>
                            </div>

                            <div style="text-align: center; margin-top: 20px;">
                                <button type="submit" class="btn" id="submitBtn">Save Predictions</button>
                            </div>
                        </form>
                    </div>

                    <a href="${pageContext.request.contextPath}/match/upcoming" class="back-link">← Back to Matches</a>
                </div>

                <script>
                    // Countdown Timer
                    <c:if test="${match.matchStartTime != null}">
                        (function() {
                        // LocalDateTime.toString() returns ISO format like "2025-12-16T19:00"
                        const matchStartTimeStr = '${match.matchStartTime}';
                        console.log('Match start time string:', matchStartTimeStr);

                        // Parse the date properly - LocalDateTime format is "YYYY-MM-DDTHH:mm"
                        let matchStartTime;
                        try {
                            // Ensure proper format for JavaScript Date parsing
                            const dateStr = matchStartTimeStr.replace('T', ' ');
                        // Parse as: "YYYY-MM-DD HH:mm"
                        const [datePart, timePart] = dateStr.split(' ');
                        const [year, month, day] = datePart.split('-');
                        const [hour, minute] = timePart.split(':');
                        // Create date in local timezone (month is 0-indexed in JS)
                        matchStartTime = new Date(parseInt(year), parseInt(month) - 1, parseInt(day), parseInt(hour), parseInt(minute), 0).getTime();
                        console.log('Parsed match start time:', new Date(matchStartTime));
                        } catch (e) {
                            console.error('Error parsing date:', e);
                        // Fallback to ISO string parsing
                        matchStartTime = new Date(matchStartTimeStr).getTime();
                        }

                        function updateCountdown() {
                            const now = new Date().getTime();
                        const distance = matchStartTime - now;

                        const countdownEl = document.getElementById('countdown');
                        const timerEl = document.getElementById('countdown-timer');
                        const form = document.getElementById('predictionForm');
                        const submitBtn = document.getElementById('submitBtn');

                        if (isNaN(matchStartTime) || isNaN(now)) {
                                if (timerEl) timerEl.innerHTML = "⏰ Calculating...";
                        return;
                            }

                        if (distance < 0) {
                                if (timerEl) timerEl.innerHTML = "⏰ Deadline Passed!";
                        if (countdownEl) countdownEl.classList.add('expired');
                        if (form) form.style.opacity = '0.5';
                        if (submitBtn) {
                            submitBtn.disabled = true;
                        submitBtn.textContent = 'Prediction Window Closed';
                                }
                        return;
                            }

                        const days = Math.floor(distance / (1000 * 60 * 60 * 24));
                        const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                        const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                        const seconds = Math.floor((distance % (1000 * 60)) / 1000);

                        if (timerEl) {
                            timerEl.innerHTML = `${days}d ${hours}h ${minutes}m ${seconds}s`;
                            }
                        }

                        // Initialize countdown immediately
                        updateCountdown();
                        // Update every second
                        setInterval(updateCountdown, 1000);
                    })();
                    </c:if>

                    function selectTeam(teamId, button) {
                        document.getElementById('selectedTeamId').value = teamId;
                        // Update button styles
                        document.querySelectorAll('[name="teamId"]').forEach(btn => {
                            btn.classList.remove('btn-selected');
                        });
                        button.classList.add('btn-selected');
                    }

                    function selectToss(teamId, button) {
                        document.getElementById('selectedTossId').value = teamId;
                        // Update button styles
                        const parent = button.parentElement;
                        parent.querySelectorAll('button').forEach(btn => {
                            btn.classList.remove('btn-selected');
                        });
                        button.classList.add('btn-selected');
                    }
                </script>
            </body>

            </html>