/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import org.json.JSONObject;
import dao.MessageDAO;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chatRoomServer")
public class ChatRoomServerEndpoint {

    private static Set<Session> users = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void handleOpen(Session session) {
        String email = (String) session.getUserProperties().get("email");
        if (email == null) {
            session.getUserProperties().put("email", "pending");
        }
        users.add(session);
        System.out.println("Client connected: " + session.getId());
    }

    @OnMessage
    public void handleMessage(String message, Session userSession) throws IOException {
        JSONObject jsonMessage = new JSONObject(message);

        if (jsonMessage.has("type") && jsonMessage.getString("type").equals("connect")) {
            String email = jsonMessage.getString("email");
            userSession.getUserProperties().put("email", email);
            System.out.println("User connected with email: " + email);
        } else if (jsonMessage.has("type") && jsonMessage.getString("type").equals("message")) {
            String sender = jsonMessage.getString("sender");
            String receiver = jsonMessage.getString("receiver");
            String messageText = jsonMessage.getString("message");

            MessageDAO messageDAO = new MessageDAO();
            messageDAO.sendMessage(sender, receiver, messageText);
            boolean receiverConnected = false;
            for (Session session : users) {
                if (session.getUserProperties().get("email").equals(receiver)) {
                    session.getBasicRemote().sendText(message);
                    receiverConnected = true;
                    System.out.println("Message sent to: " + receiver + " - " + message);
                }
            }

            if (!receiverConnected) {
                System.out.println("Receiver " + receiver + " is not connected. Message saved.");
            }

            JSONObject response = new JSONObject();
            response.put("type", "message");
            response.put("sender", sender);
            response.put("receiver", receiver); 
            response.put("message", messageText);

            userSession.getBasicRemote().sendText(response.toString());
            System.out.println("Message sent to sender: " + sender + " - " + response.toString());
        }
    }

    @OnClose
    public void handleClose(Session session) {
        users.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }
}
