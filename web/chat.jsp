<%-- Document : newjsp Created on : Feb 17, 2025, 1:40:24 AM Author : nkiem --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat Messenger</title>
        <link rel="shortcut icon" href="<%= request.getContextPath() %>/assets/images/favicon/favicon.png" type="image/x-icon" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/assets/css/chat.css" rel="stylesheet" />
    </head>

    <body>
        <%@include file="manager/menumanager.jsp" %>
        <div id="main">
            <div class="container">
                <div class="sidebar">
                    <div class="sidebar-header">
                        <input placeholder="Tìm kiếm trên Messenger" type="text" />
                        <i class="fas fa-search"> </i>
                    </div>
                    <div class="sidebar-content">
                        <c:forEach var="user" items="${list}">
                            <a href="chat?email=${user.email}">
                                <div class="contact ${user.email == chatwith.email ? 'active' : ''}">
                                    <img alt="Profile picture of ${user.fullName}" height="40" src="<%= request.getContextPath() %>/${user.image.imageURL}"
                                         width="40" />
                                    <div>
                                        <div class="name">${user.fullName}</div>
                                        <div class="time">
                                            <c:choose>
                                                <c:when test="${user.lastMessage != null}">
                                                    ${user.lastMessage}
                                                </c:when>
                                                <c:otherwise>
                                                    Bắt đầu cuộc trò chuyện
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
                <div class="chat-area">
                    <div class="chat-header">
                        <c:if test="${not empty chatwith}">
                            <c:set var="user" value="${chatwith}" />
                            <div class="user-info">
                                <img alt="Profile picture of ${user.fullName}" height="40" 
                                     src="<%= request.getContextPath() %>/${user.image.imageURL}" width="40" />
                                <div>
                                    ${user.fullName} 
                                    <c:if test="${chatwithType == 'staff'}">(Staff)</c:if>
                                    <c:if test="${chatwithType == 'resident'}">(Resident)</c:if>
                                    </div>
                                    <input type="hidden" id="emailSend" name="emailSend"
                                           value="${sessionScope.staff != null ? sessionScope.staff.email : sessionScope.resident.email}" />
                                <input type="hidden" id="emailRecieved" name="emailRecieved" value="${user.email}" />
                            </div>
                            <div>
                                <i class="fas fa-phone"></i>
                                <i class="fas fa-video"></i>
                                <i class="fas fa-info-circle"></i>
                            </div>
                        </c:if>
                    </div>


                    <div class="chat-content" id="chat-content">
                        <c:forEach var="message" items="${messageHistory}">
                            <c:set var="currentUserEmail" value="${sessionScope.staff != null ? sessionScope.staff.email : sessionScope.resident.email}" />
                            <div class="message ${message.senderEmail eq currentUserEmail ? 'sent' : 'received'}">
                                <c:if test="${message.senderEmail ne currentUserEmail}">
                                    <img alt="Profile picture" class="avatar" height="30"
                                         src="<%= request.getContextPath() %>/${chatwith.image.imageURL}" width="30" />
                                </c:if>
                                <div>
                                    <div class="text">${message.messageText}</div>
                                    <div class="${message.senderEmail eq currentUserEmail ? 'time-right' : 'time-left'}">
                                        ${message.formattedDate}
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="chat-footer">
                        <input id="message-input" placeholder="Aa" type="text" />
                        <button id="send-button" type="button">
                            <i class="fas fa-paper-plane"></i>
                        </button>
                    </div>
                </div>
            </div>
            
        </div>
    </body>
    <script type="text/javascript">
        var websocket = new WebSocket("ws://localhost:8080/ManagerApartment/chatRoomServer");
        var isConnected = false;

        websocket.onopen = function (message) {
            isConnected = true;
            var senderEmail = document.getElementById('emailSend').value;
            websocket.send(JSON.stringify({type: "connect", email: senderEmail}));
            console.log("Server connected...");

            scrollToBottom();
        };

        websocket.onmessage = function (message) {
            console.log("Message received: ", message.data);
            processMessage(message);
        };

        websocket.onerror = function (event) {
            console.error("WebSocket error: ", event);
            isConnected = false;
        };

        websocket.onclose = function (event) {
            console.log("WebSocket connection closed: ", event);
            isConnected = false;
        };

        function processMessage(message) {
            console.log("Raw message data:", message.data);
            try {
                var messageData = JSON.parse(message.data);
                console.log("Parsed message data:", messageData);
            } catch (error) {
                console.error("Error parsing message data:", error);
                return;
            }

            if (!messageData || !messageData.message) {
                console.warn("No message property found in the data.");
                return;
            }

            var chatContent = document.getElementById('chat-content');
            var newMessage = document.createElement('div');
            var senderEmail = document.getElementById('emailSend').value;
            var receiverEmail = document.getElementById('emailRecieved').value;

            if (messageData.sender !== senderEmail && messageData.sender !== receiverEmail &&
                    messageData.receiver !== senderEmail && messageData.receiver !== receiverEmail) {
                console.log("Message not for this conversation");
                return;
            }

            newMessage.className = 'message ' + (messageData.sender === senderEmail ? 'sent' : 'received');

            var messageContent = document.createElement('div');

            if (messageData.sender !== senderEmail) {
                var avatar = document.createElement('img');
                avatar.className = 'avatar';
                avatar.alt = 'Profile picture';
                avatar.height = 30;
                avatar.width = 30;
                avatar.src = document.querySelector('.user-info img').src;
                newMessage.appendChild(avatar);
            }

            var now = new Date();
            var timeString = now.getHours() + ':' + String(now.getMinutes()).padStart(2, '0');

            var textDiv = document.createElement('div');
            textDiv.className = 'text';
            textDiv.textContent = messageData.message;

            var timeDiv = document.createElement('div');
            timeDiv.className = messageData.sender === senderEmail ? 'time-right' : 'time-left';
            timeDiv.textContent = timeString;

            messageContent.appendChild(textDiv);
            messageContent.appendChild(timeDiv);
            newMessage.appendChild(messageContent);

            chatContent.appendChild(newMessage);
            scrollToBottom();
        }

        function scrollToBottom() {
            var chatContent = document.getElementById('chat-content');
            chatContent.scrollTop = chatContent.scrollHeight;
        }

        function sendMessage() {
            var input = document.getElementById('message-input');
            var messageText = input.value;

            if (messageText.trim() !== "") {
                if (!isConnected) {
                    reconnectWebSocket();
                    setTimeout(function () {
                        if (isConnected) {
                            sendMessage();
                        } else {
                            alert("Cannot connect to the chat server. Please try again later.");
                        }
                    }, 1000);
                    return;
                }

                var senderEmail = document.getElementById('emailSend').value;
                var receiverEmail = document.getElementById('emailRecieved').value;

                if (senderEmail && receiverEmail) {
                    var messageData = {
                        type: "message",
                        sender: senderEmail,
                        receiver: receiverEmail,
                        message: messageText
                    };

                    websocket.send(JSON.stringify(messageData));
                    console.log("Sent message:", messageData);
                    input.value = "";
                } else {
                    console.error("Email elements not found.");
                }
            } else {
                console.log("Message is empty, not sending.");
            }
        }

        document.addEventListener('DOMContentLoaded', function () {
            var messageInput = document.getElementById('message-input');
            var sendButton = document.getElementById('send-button');

            messageInput.addEventListener('keypress', function (event) {
                if (event.key === 'Enter') {
                    event.preventDefault();
                    sendMessage();
                }
            });

            sendButton.addEventListener('click', function () {
                sendMessage();
            });

            scrollToBottom();
        });
        function reconnectWebSocket() {
            if (!isConnected) {
                console.log("Attempting to reconnect...");
                if (websocket) {
                    websocket.close();
                }
                websocket = new WebSocket("ws://localhost:8080/ManagerApartment/chatRoomServer");

                websocket.onopen = function (message) {
                    isConnected = true;
                    var senderEmail = document.getElementById('emailSend').value;
                    websocket.send(JSON.stringify({type: "connect", email: senderEmail}));
                    console.log("Server reconnected successfully");
                };

                websocket.onmessage = function (message) {
                    console.log("Message received: ", message.data);
                    processMessage(message);
                };

                websocket.onerror = function (event) {
                    console.error("WebSocket error: ", event);
                    isConnected = false;
                    setTimeout(reconnectWebSocket, 5000);
                };

                websocket.onclose = function (event) {
                    console.log("WebSocket connection closed: ", event);
                    isConnected = false;
                    setTimeout(reconnectWebSocket, 5000);
                };
            }
        }
    </script>
</html>