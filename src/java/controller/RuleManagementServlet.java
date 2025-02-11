/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.RuleDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Rule;


@WebServlet(name = "RuleManagementServlet", urlPatterns = {"/rule-management"})
public class RuleManagementServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);
        int pageNumber = 1;
        int pageSize = 10;
        if (request.getParameter("page") != null) {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        }
        RuleDAO ruleDAO = new RuleDAO();
        List<Rule> ruleList = ruleDAO.selectAll();
        int totalRules = ruleList.size();
        int totalPages = (int) Math.ceil((double) totalRules / pageSize);

        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalRules);

        List<Rule> rulesForPage = ruleList.subList(startIndex, endIndex);

        request.setAttribute("rulesList", rulesForPage);

        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", pageNumber);

        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalRules", totalRules);

        request.getRequestDispatcher("/rulemanagement.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
