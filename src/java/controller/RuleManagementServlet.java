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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Rule;
import validation.Validate;

@WebServlet(name = "RuleManagementServlet", urlPatterns = {"/manager/rule-management"})
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
        int pageSize = 2;
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

        request.getRequestDispatcher("/manager/rulemanagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("_method");

        if (method.equalsIgnoreCase("PUT")) {
            doPut(request, response); // update rule: method PUT
        } else if (method.equalsIgnoreCase("DELETE")) { // delete rule: method DELETE
            doDelete(request, response);
        } else { // create rule: method POST
            String name = request.getParameter("editRuleName").trim();
            String description = request.getParameter("editRuleDescription").trim();
            LocalDate publicDate = LocalDate.parse(request.getParameter("editPublicDate").trim(), DateTimeFormatter.ISO_DATE);
       
            Rule rule = new Rule(name, description, publicDate);
            RuleDAO ruleDAO = new RuleDAO();
            int inserted = ruleDAO.insert(rule);
            doGet(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("deleteRuleID"));
        Rule rule = new Rule(id);

        RuleDAO ruleDAO = new RuleDAO();
        int delete = ruleDAO.delete(rule);

        doGet(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("editRuleID"));
        String name = request.getParameter("editRuleName").trim();
        String description = request.getParameter("editRuleDescription").trim();
        LocalDate publicDate = LocalDate.parse(request.getParameter("editPublicDate").trim(),
                DateTimeFormatter.ISO_DATE);
        Rule rule = new Rule(id, name, description, publicDate);

        RuleDAO ruleDAO = new RuleDAO();
        int update = ruleDAO.update(rule);
        doGet(request, response);
    }
}
