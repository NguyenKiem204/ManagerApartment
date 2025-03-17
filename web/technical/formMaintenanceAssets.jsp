<%-- 
    Document   : formMaintenanceAssets
    Created on : Mar 17, 2025, 9:08:57 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }

            form {
                max-width: 600px;
                margin: auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 5px;
                background-color: #f9f9f9;
            }

            label {
                font-weight: bold;
            }

            input,
            textarea {
                width: 100%;
                padding: 8px;
                margin: 5px 0;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            button {
                background-color: #f5620d;
                color: white;
                padding: 10px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            button:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>
        <%@include file="/manager/menumanager.jsp" %>
        <div id="main">
            <div class="container">
                <div style="width: 100%; margin: auto;">

                    <form action="formmaintenanceassets" method="post">
                        <h2 style="text-align: center; color: #f5620d;">Asset Maintenance Form</h2>

                        <label for="assetID">Asset ID:</label>
                        <input type="text" id="assetID" name="assetID" required>

                        <label for="assetName">Asset Name:</label>
                        <input type="text" id="assetName" name="assetName" readonly>

                        <label for="maintenanceDate">Maintenance Date:</label>
                        <input type="input" id="boughtOn" name="maintenanceDate" placeholder="dd/MM/yyyy" required>

                        <label for="description">Description:</label>
                        <textarea id="description" name="description" rows="4" required></textarea>

                        <label for="cost">Cost:</label>
                        <input type="number" id="cost" name="cost" step="0.01" required>

                        <label for="nextMaintenanceDate">Next Maintenance Date:</label>
                        <input type="input" id="deadline" name="nextMaintenanceDate">
                        
                        <c:if test="${not empty error}">
                            <p style="color: red;">${error}</p>
                        </c:if>

                        <button type="submit">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
    <script>
        function fetchAssetName() {
            var assetID = document.getElementById("assetID").value;
            if (assetID) {
                fetch("getAssetName?assetID=" + assetID)
                        .then(response => response.text())
                        .then(data => {
                            document.getElementById("assetName").value = data;
                        })
                        .catch(error => console.error("Error fetching asset name:", error));
            } else {
                document.getElementById("assetName").value = "";
            }
        }
    </script>
</html>
