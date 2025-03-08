<%-- 
    Document   : addRegulations
    Created on : Mar 5, 2025, 2:41:07 AM
    Author     : Hoang-Tran
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Add New Regulation</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2>Add New Regulation</h2>
        <form action="rule-management" method="POST">
            <input type="hidden" name="_method" value="POST"/>
            <div class="form-group">
                <label>Rule Name</label>
                <input type="text" class="form-control" name="editRuleName" required>
            </div>
            <div class="form-group">
                <label>Rule Description</label>
                <textarea name="editRuleDescription" class="form-control" required></textarea>
            </div>
            <div class="form-group">
                <label>Public Date</label>
                <input type="date" class="form-control" name="editPublicDate" required>
            </div>
            <button type="submit" class="btn btn-primary">Add Regulation</button>
            <a href="yourPreviousPage.jsp" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>