/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dao.CommentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Comment;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author nkiem
 */
@WebServlet(name = "CommentServlet", urlPatterns = {"/comment"})
public class CommentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int newsId = Integer.parseInt(request.getParameter("newsId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        String userType = request.getParameter("userType");
        String content = request.getParameter("content");

        // Create new comment
        Comment comment = new Comment();
        comment.setNewsId(newsId);
        comment.setUserId(userId);
        comment.setUserType(userType);
        comment.setContent(content);
        comment.setCommentDate(LocalDateTime.now());

        // Save to database
        CommentDAO commentDAO = new CommentDAO();
        // Sử dụng insertAndGetId thay vì insert để lấy được ID
        int commentId = commentDAO.insertAndGetId(comment);

        // Prepare response
        JSONObject jsonResponse = new JSONObject();

        if (commentId > 0) {
            jsonResponse.put("success", true);
            jsonResponse.put("commentId", commentId);
            jsonResponse.put("userId", comment.getUserId());
            jsonResponse.put("userType", comment.getUserType());
            jsonResponse.put("content", comment.getContent());
            jsonResponse.put("date", "just now");
            jsonResponse.put("formattedDate", comment.getFormattedDate());
            jsonResponse.put("userName", request.getParameter("userName"));
            jsonResponse.put("userAvatar", request.getParameter("userAvatar"));
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Failed to add comment");
        }

        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int newsId = Integer.parseInt(request.getParameter("newsId"));
            int offset = 0;
            int limit = 4;

            if (request.getParameter("offset") != null && !request.getParameter("offset").isEmpty()) {
                offset = Integer.parseInt(request.getParameter("offset"));
            }

            CommentDAO commentDAO = new CommentDAO();
            List<Comment> comments;
            if (offset > 0) {
                comments = commentDAO.getCommentsByNewsId(newsId, offset, limit);
            } else {
                comments = commentDAO.getCommentsByNewsId(newsId, 0, limit);
            }

            JSONArray jsonArray = new JSONArray();

            for (Comment comment : comments) {
                JSONObject jsonComment = new JSONObject();
                jsonComment.put("commentID", comment.getCommentId());
                jsonComment.put("newsID", comment.getNewsId());
                jsonComment.put("userID", comment.getUserId());
                jsonComment.put("userType", comment.getUserType());
                jsonComment.put("content", comment.getContent());
                jsonComment.put("commentDate", comment.getCommentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                jsonComment.put("date", comment.getTimeAgo());
                jsonComment.put("formattedDate", comment.getFormattedDate());
                jsonComment.put("userName", comment.getUserName());
                jsonComment.put("userAvatar", comment.getUserAvatar());

                jsonArray.put(jsonComment);
            }
            System.out.println("Loading comments for newsId=" + newsId + ", offset=" + offset + ", found: " + jsonArray.length());

            response.getWriter().write(jsonArray.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid newsId or offset");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading comments: " + e.getMessage());
        }
    }
}
