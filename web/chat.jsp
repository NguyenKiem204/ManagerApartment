<%-- Document : newjsp Created on : Feb 17, 2025, 1:40:24 AM Author : nkiem --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" />
        <link href="<%= request.getContextPath() %>/assets/css/chat.css" rel="stylesheet" />
    </head>

    <body>
        <div class="container">
            <div class="sidebar">
                <div class="sidebar-header">
                    <input placeholder="Tìm kiếm trên Messenger" type="text" />
                    <i class="fas fa-search"> </i>
                </div>
                <div class="sidebar-content">
                    <c:forEach var="user" items="${list}">
                        <a href="chat?email=${user.email}">
                            <div class="contact">
                                <img alt="Profile picture of ${user.fullName}" height="40" src="<%= request.getContextPath() %>/${user.image.imageURL}"
                                     width="40" />
                                <div>
                                    <div class="name">${user.fullName}</div>
                                    <div class="time">Đã đọc cho hết ❤️ 1 giờ</div>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                    <!--                        <a href="">
                                                <div class="contact">
                                                    <img alt="Profile picture of Đinh Nhật Quân" height="40"
                                                        src="https://placehold.co/40x40" width="40" />
                                                    <div>
                                                        <div class="name">Đinh Nhật Quân</div>
                                                        <div class="time">Đã bày tỏ cảm xúc ❤️ 2 giờ</div>
                                                    </div>
                                                </div>
                                            </a>
                                            <a href="">
                                                <div class="contact">
                                                    <img alt="Profile picture of Tuyển dụng NodeJS/ReactJS ViệtNam" height="40"
                                                        src="https://placehold.co/40x40" width="40" />
                                                    <div>
                                                        <div class="name">Tuyển dụng NodeJS/ReactJS ViệtNam</div>
                                                        <div class="time">Tony: 😆 2 giờ</div>
                                                    </div>
                                                </div>
                                            </a>
                                            <a href="">
                                                <div class="contact">
                                                    <img alt="Profile picture of Bảo Đào" height="40" src="https://placehold.co/40x40"
                                                        width="40" />
                                                    <div>
                                                        <div class="name">Bảo Đào</div>
                                                        <div class="time">Đã xác nhận 2 giờ</div>
                                                    </div>
                                                </div>
                                            </a>
                                            <a href="">
                                                <div class="contact">
                                                    <img alt="Profile picture of Tuyển dụng NodeJS/ReactJS ViệtNam" height="40"
                                                        src="https://placehold.co/40x40" width="40" />
                                                    <div>
                                                        <div class="name">Tuyển dụng NodeJS/ReactJS ViệtNam</div>
                                                        <div class="time">Minh: Tố Hậu - HN) MOR ch... 3 giờ</div>
                                                    </div>
                                                </div>
                                            </a>
                                            <a href="">
                                                <div class="contact">
                                                    <img alt="Profile picture of Nguyễn Chương" height="40" src="https://placehold.co/40x40"
                                                        width="40" />
                                                    <div>
                                                        <div class="name">Nguyễn Chương</div>
                                                        <div class="time">Chương đã gọi 1 lần 4 giờ</div>
                                                    </div>
                                                </div>
                                            </a>
                                            <a href="">
                                                <div class="contact">
                                                    <img alt="Profile picture of K15 CP Phanxico Assisi" height="40"
                                                        src="https://placehold.co/40x40" width="40" />
                                                    <div>
                                                        <div class="name">K15 CP Phanxico Assisi</div>
                                                        <div class="time">Lan Anh: Cả nhà nhớ lịch ... 5 giờ</div>
                                                    </div>
                                                </div>
                                            </a>
                                            <a href="">
                                                <div class="contact">
                                                    <img alt="Profile picture of Nguyễn Đăng Nguyễn" height="40"
                                                        src="https://placehold.co/40x40" width="40" />
                                                    <div>
                                                        <div class="name">Nguyễn Đăng Nguyễn</div>
                                                        <div class="time">5 giờ</div>
                                                    </div>
                                                </div>
                                            </a>
                                            <a href="">
                                                <div class="contact">
                                                    <img alt="Profile picture of Dũng Alan" height="40" src="https://placehold.co/40x40"
                                                        width="40" />
                                                    <div>
                                                        <div class="name">Dũng Alan</div>
                                                        <div class="time">Bạn: Mai này có 5 giờ</div>
                                                    </div>
                                                </div>
                                            </a>
                                            <a href="">
                                                <div class="contact">
                                                    <img alt="Profile picture of Thơ" height="40" src="https://placehold.co/40x40"
                                                        width="40" />
                                                    <div>
                                                        <div class="name">Thơ</div>
                                                        <div class="time">Bạn: 1 ngày 5 giờ</div>
                                                    </div>
                                                </div>
                                            </a>
                                            <a href="">
                                                <div class="contact">
                                                    <img alt="Profile picture of Nguyễn Trà My" height="40" src="https://placehold.co/40x40"
                                                        width="40" />
                                                    <div>
                                                        <div class="name">Nguyễn Trà My</div>
                                                        <div class="time">Bạn: Đã đọc báo cáo 5 giờ</div>
                                                    </div>
                                                </div>
                                            </a>
                                            <a href="">
                                                <div class="contact">
                                                    <img alt="Profile picture of Rèn luyện sức khỏe và thi giác" height="40"
                                                        src="https://placehold.co/40x40" width="40" />
                                                    <div>
                                                        <div class="name">Rèn luyện sức khỏe và thi giác</div>
                                                        <div class="time">Bạn: 5 giờ</div>
                                                    </div>
                                                </div>
                                            </a>
                                            <a href="">
                                                <div class="contact">
                                                    <img alt="Profile picture of Vinh" height="40" src="https://placehold.co/40x40"
                                                        width="40" />
                                                    <div>
                                                        <div class="name">Vinh</div>
                                                        <div class="time">Bạn: 5 giờ</div>
                                                    </div>
                                                </div>
                                            </a>-->
                </div>
            </div>
            <div class="chat-area">
                <div class="chat-header">
                    <c:set value="${chatwith}" var="user"></c:set>
                        <div class="user-info">
                            <img alt="Profile picture of ${user.fullName}" height="40" src="<%= request.getContextPath() %>/${user.image.imageURL}"
                             width="40" />
                        <div>${user.fullName}</div>
                        <input type="hidden" name="emailSend" value="${sessionScope.staff!=null?sessionScope.staff.email:sessionScope.resident.email}"/>  
                        <input type="hidden" name="emailRecieved" value="${user.email}"/>
                    </div>
                    <div>
                        <i class="fas fa-phone"> </i>
                        <i class="fas fa-video"> </i>
                        <i class="fas fa-info-circle"> </i>
                    </div>
                </div>
                <div class="chat-content">
                    <div class="message received">
                        <img alt="Profile picture of Đinh Nhật Quân" class="avatar" height="30"
                             src="https://placehold.co/30x30" width="30" />
                        <div>
                            <div class="text">
                                <img alt="Screenshot of a quiz on edX" height="400"
                                     src="https://placehold.co/200x400" width="200" />
                            </div>
                            <div class="time-left">21:51 CN</div>
                        </div>
                    </div>
                    <div class="message received">
                        <img alt="Profile picture of Đinh Nhật Quân" class="avatar" height="30"
                             src="https://placehold.co/30x30" width="30" />
                        <div>
                            <div class="text">Ko</div>
                            <div class="time-left">22:08 CN</div>
                        </div>
                    </div>

                    <div class="message sent">
                        <div>
                            <div class="text">Trả câu hỏi</div>
                            <div class="time-right">22:08 CN</div>
                        </div>
                    </div>
                    <div class="message sent">
                        <div>
                            <div class="text">Tìm quiz là nó có cả bài</div>
                            <div class="time-right">22:08 CN</div>
                        </div>
                    </div>

                </div>
                <div class="chat-footer">
                    <input placeholder="Aa" type="text" />
                    <i class="fas fa-thumbs-up"> </i>
                    <i class="fas fa-heart"> </i>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        var websocket = new WebSocket("ws://localhost:8080/ManagerApartment/chatRoomServer");

        websocket.onopen = function (message) {
            var senderEmail = document.querySelector('input[name="emailSend"]').value;
            websocket.send(JSON.stringify({type: "connect", email: senderEmail}));
            console.log("Server connected...");
            processOpen(message);
        };
        websocket.onmessage = function (message) {
            console.log("Message received: ", message.data); // Đảm bảo log này xuất hiện  
            processMessage(message);
        };
        websocket.onerror = function (event) {
            console.error("WebSocket error: ", event);
        };

        websocket.onclose = function (event) {
            console.log("WebSocket connection closed: ", event);
        };

        function processOpen(message) {
            console.log("Server connected...: " + message);
        }

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

            var chatContent = document.querySelector('.chat-content');
            var newMessage = document.createElement('div');
            var senderEmail = document.querySelector('input[name="emailSend"]').value;
            newMessage.className = 'message ' + (messageData.sender === senderEmail ? 'sent' : 'received');

            var wrapperDiv = document.createElement('div');

            var now = new Date();
            var timeString = now.getHours() + ':' + String(now.getMinutes()).padStart(2, '0');

            var textDiv = document.createElement('div');
            textDiv.className = 'text';
            textDiv.textContent = messageData.message;

            var timeDiv = document.createElement('div');
            timeDiv.className = 'time-right';
            timeDiv.textContent = timeString;

            wrapperDiv.appendChild(textDiv);
            wrapperDiv.appendChild(timeDiv);
            newMessage.appendChild(wrapperDiv);

            chatContent.appendChild(newMessage);
            console.log("New message appended to chat content");
            chatContent.scrollTop = chatContent.scrollHeight;

        }

        function processClose(message) {
            console.log("Server disconnected...");
        }

        function processError(message) {
            console.log("Error: " + message);
        }

        function sendMessage() {
            var input = document.querySelector('.chat-footer input');
            var messageText = input.value;

            if (messageText.trim() !== "") {
                var senderInput = document.querySelector('input[name="emailSend"]');
                var receiverInput = document.querySelector('input[name="emailRecieved"]');

                if (senderInput && receiverInput) {
                    var senderEmail = senderInput.value;
                    var receiverEmail = receiverInput.value;

                    var messageData = {
                        type: "message",
                        sender: senderEmail,
                        receiver: receiverEmail,
                        message: messageText
                    };

                    if (typeof websocket !== 'undefined' && websocket.readyState === WebSocket.OPEN) {
                        websocket.send(JSON.stringify(messageData));
                        console.log("Sent message:", messageData);
                        input.value = ""; // Clear input  
                    } else {
                        console.error("WebSocket is not open:", websocket.readyState);
                    }
                } else {
                    console.error("Email elements are null.");
                }
            } else {
                console.log("Message is empty, not sending.");
            }
        }

        document.addEventListener('DOMContentLoaded', function () {
            var sendMessageInput = document.querySelector('.chat-footer input');

            sendMessageInput.addEventListener('keypress', function (event) {
                if (event.key === 'Enter') {
                    event.preventDefault();
                    sendMessage();
                }
            });
        });
    </script>

</html>