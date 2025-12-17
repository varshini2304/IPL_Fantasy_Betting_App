<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Register - IPL Fantasy</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body>
            <div class="container">
                <div class="header">
                    <h1>🏏 IPL Fantasy League</h1>
                    <h2>Create Account</h2>
                </div>

                <div class="card">
                    <c:if test="${not empty error}">
                        <div class="alert alert-error">${error}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/register" method="post">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username" required placeholder="Choose a username">
                        </div>

                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" required placeholder="Enter your email">
                        </div>

                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password" required
                                placeholder="Create a password">
                        </div>

                        <div class="form-group">
                            <label style="display: flex; align-items: center; gap: 8px; cursor: pointer;">
                                <input type="checkbox" id="acceptTerms" name="acceptTerms" required
                                    style="width: auto; cursor: pointer;">
                                <span>I accept all terms and conditions</span>
                            </label>
                        </div>

                        <button type="submit">Register</button>
                    </form>

                    <p style="margin-top: 20px; text-align: center;">
                        Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a>
                    </p>
                </div>
            </div>
        </body>

        </html>