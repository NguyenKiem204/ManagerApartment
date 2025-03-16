<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Apartment Management</title>
        <style>
            .export-btn {
                padding: 10px 15px;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
            }
            .export-btn:hover {
                background-color: #45a049;
            }
            .toast {
                position: fixed;
                bottom: 20px;
                left: 50%;
                transform: translateX(-50%);
                padding: 12px 20px;
                font-size: 16px;
                border-radius: 8px;
                color: white;
                background: #333;
                opacity: 1;
                transition: opacity 0.5s ease-in-out;
                z-index: 1000;
            }

            .toast.success {
                background: #28a745;
            }

            .toast.error {
                background: #dc3545;
            }

        </style>
    </head>
    <body>
        <h1>Apartment Management</h1>

        <form id="exportForm">
            <button type="button" class="export-btn" id="exportBtn">Export to Excel</button>
        </form>

        <script>
            document.getElementById("exportBtn").addEventListener("click", async function () {
                try {
                    const folderHandle = await window.showDirectoryPicker({mode: "readwrite"});

                    const response = await fetch("<%= request.getContextPath() %>/accountant/export-apartment-excel", {
                        method: "POST"
                    });
                    if (!response.ok)
                        throw new Error("Failed to download the file.");

                    const blob = await response.blob();

                    let fileHandle;
                    try {
                        fileHandle = await folderHandle.getFileHandle("apartments.xlsx");
                    } catch {
                        fileHandle = await folderHandle.getFileHandle("apartments.xlsx", {create: true});
                    }

                    const writable = await fileHandle.createWritable();
                    await writable.write(blob);
                    await writable.close();

                    showToast("✅ File downloaded successfully!", "success");
                } catch (error) {
                    console.error("Error:", error);
                    showToast("❌ Failed to save the file!", "error");
                }
            });
            function showToast(message, type) {
                const toast = document.createElement("div");
                toast.textContent = message;
                toast.className = `toast ${type}`;
                document.body.appendChild(toast);
                setTimeout(() => {
                    toast.style.opacity = "0";
                    setTimeout(() => toast.remove(), 500);
                }, 3000);
            }

        </script>

    </body>
</html>