<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CSV Import - IPL Fantasy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🏏 IPL Fantasy League</h1>
            <h2>Import Teams CSV</h2>
        </div>

        <div class="card">
            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <c:if test="${not empty msg}">
                <div class="alert alert-success">${msg}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/admin/csv/teams"
                  method="post"
                  enctype="multipart/form-data">
                <div class="form-group">
                    <label for="file">Select CSV File</label>
                    <input type="file" id="file" name="file" accept=".csv" required>
                    <small style="color: #666; display: block; margin-top: 5px;">
                        Please select a CSV file containing team data
                    </small>
                </div>

                <button type="submit">Upload Teams CSV</button>
            </form>
        </div>

        <a href="${pageContext.request.contextPath}/admin" class="back-link">← Back to Admin Panel</a>
    </div>
</body>
</html>
