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

@WebServlet(name = "ResidentRegulations", urlPatterns = {"/owner/regulations"})
public class ResidentRegulations extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RuleDAO dao = new RuleDAO();
        List<Rule> list = dao.selectAll();

        int pageNumber = 1;
        int pageSize = 1;
        if (request.getParameter("page") != null) {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        }

        int totalRules = list.size();
        int totalPages = (int) Math.ceil((double) totalRules / pageSize);
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalRules);

        List<Rule> rulesForPage = list.subList(startIndex, endIndex);

        request.setAttribute("rulesList", rulesForPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageNumber", pageNumber);

//        System.out.println(list.size() + "test");
        request.getRequestDispatcher("/owner/Regulations.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchName = request.getParameter("search").trim();
        RuleDAO ruleDAO = new RuleDAO();
        List<Rule> rules;
        if (searchName.isEmpty()) {
            doGet(request, response);
            return;
        } else {
            rules = ruleDAO.searchByName(searchName);
        }

        int pageNumber = 1;
        int pageSize = 1;

        int totalRules = rules.size();
        int totalPages = (int) Math.ceil((double) totalRules / pageSize);
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalRules);

        List<Rule> rulesForPage = rules.subList(startIndex, endIndex);

        request.setAttribute("rulesList", rulesForPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageNumber", pageNumber);

        request.setAttribute("rulesList", rules);
        request.setAttribute("searchName", searchName);

        request.getRequestDispatcher("/owner/Regulations.jsp").forward(request, response);
    }
}
