package controller;

import dao.MessageDAO;
import dao.ResidentDAO;
import dao.StaffDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Message;
import model.Resident;
import model.Staff;

@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Staff staff = (Staff) session.getAttribute("staff");
            Resident resident = (Resident) session.getAttribute("resident");

            String currentUserEmail = (staff != null) ? staff.getEmail() : (resident != null ? resident.getEmail() : null);

            if (currentUserEmail == null) {
                response.sendRedirect("login");
                return;
            }

            StaffDAO staffDAO = new StaffDAO();
            ResidentDAO residentDAO = new ResidentDAO();
            MessageDAO messageDAO = new MessageDAO();

            List<Staff> listStaff = staffDAO.selectAll();
            List<Resident> listResident = residentDAO.selectAll();

            listStaff.removeIf(s -> s.getEmail().equals(currentUserEmail));
            listResident.removeIf(r -> r.getEmail().equals(currentUserEmail));

            List<Object> combinedList = new ArrayList<>();
            combinedList.addAll(listStaff);
            combinedList.addAll(listResident);

            String chatWithEmail = request.getParameter("email");
            Object chatWithUser = null;
            String chatWithType = "";

            if (chatWithEmail != null) {
                chatWithUser = staffDAO.selectByEmail(chatWithEmail);
                chatWithType = "staff";
                if (chatWithUser == null) {
                    chatWithUser = residentDAO.selectByEmail(chatWithEmail);
                    chatWithType = "resident";
                }
            } else if (!combinedList.isEmpty()) {
                chatWithUser = combinedList.get(0);
                if (chatWithUser instanceof Staff) {
                    chatWithEmail = ((Staff) chatWithUser).getEmail();
                } else {
                    chatWithEmail = ((Resident) chatWithUser).getEmail();
                }
            }

            if (chatWithEmail != null && currentUserEmail != null) {
                try {
                    List<Message> messageHistory = messageDAO.getMessagesBetween(currentUserEmail, chatWithEmail);
                    request.setAttribute("messageHistory", messageHistory);
                    messageDAO.markMessagesAsRead(chatWithEmail, currentUserEmail);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            for (Object user : combinedList) {
                String email = "";
                if (user instanceof Staff) {
                    email = ((Staff) user).getEmail();
                } else if (user instanceof Resident) {
                    email = ((Resident) user).getEmail();
                }

                Message lastMessage = messageDAO.getLastMessageBetween(currentUserEmail, email);
                if (lastMessage != null) {
                    String preview = lastMessage.getMessageText();
                    if (preview.length() > 20) {
                        preview = preview.substring(0, 20) + "...";
                    }

                    if (user instanceof Staff) {
                        ((Staff) user).setLastMessage(preview);
                    } else if (user instanceof Resident) {
                        ((Resident) user).setLastMessage(preview);
                    }
                }
            }

            request.setAttribute("list", combinedList);
            request.setAttribute("chatwith", chatWithUser);
            request.setAttribute("chatwithType", chatWithType);
            request.getRequestDispatcher("chat.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error processing chat request: " + e.getMessage());
            request.setAttribute("errorMessage", "An error occurred while loading the chat. Please try again.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("sendMessage".equals(action)) {
            String sender = request.getParameter("sender");
            String receiver = request.getParameter("receiver");
            String message = request.getParameter("message");

            MessageDAO messageDAO = new MessageDAO();
            messageDAO.sendMessage(sender, receiver, message);

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\"}");
        }
    }

    @Override
    public String getServletInfo() {
        return "Chat Servlet for handling real-time messaging";
    }
}
