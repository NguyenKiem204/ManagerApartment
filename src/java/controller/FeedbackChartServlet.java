/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.FeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author fptshop
 */
@WebServlet(name="FeedbackChartServlet", urlPatterns={"/feedback-stats"})
public class FeedbackChartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONArray jsonArray = new JSONArray();
        Map<Integer, Integer> feedbackCounts = FeedbackDAO.getFeedbackCountsByMonth();

        for (Map.Entry<Integer, Integer> entry : feedbackCounts.entrySet()) {
            JSONObject obj = new JSONObject();
            obj.put("month", entry.getKey());
            obj.put("total", entry.getValue());
            jsonArray.put(obj);
        }

        response.getWriter().write(jsonArray.toString());
    }
}