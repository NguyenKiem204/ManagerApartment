/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import dao.RuleDAO;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author Hoang-Tran
 */
@WebServlet(name="RuleManagementServlet", urlPatterns={"/manager/rules"})
public class RuleManagementServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RuleDAO ruleDAO = new RuleDAO();
        int pageSize = 5;
        int pageNumber = 1;

        List<Rule> ruleList = ruleDAO.selectAllPaging(pageNumber, pageSize);
        int totalPages = (int) Math.ceil((double) ruleDAO.selectAll().size() / pageSize);

        request.setAttribute("rulesList", ruleList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageNumber", pageNumber);
        request.getRequestDispatcher("/manager/rule_management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("_method");

        if (method.equalsIgnoreCase("SEARCH")) {
            searchOrPagination(request, response); // search/pagination: SEARCH
        } else if (method.equalsIgnoreCase("PUT")) {
            doPut(request, response); // update rule: method PUT
        } else if (method.equalsIgnoreCase("DELETE")) { // delete rule: method DELETE
            doDelete(request, response);
        } else { // create rule: method POST
            String ruleName = "";
            String errorMsg = "";
            try {
                ruleName = Validate.validateRuleName(request.getParameter("ruleName"));
            } catch (Exception ex) {
                errorMsg += ex.getMessage() + "\n";
            }
            String description = "";
            try {
                description = Validate.validateRuleDescription(request.getParameter("ruleDescription"));
            } catch (Exception ex) {
                errorMsg += ex.getMessage() + "\n";
            }

            if (!errorMsg.isEmpty()) { // có lỗi validate ruleName/description
                request.setAttribute("error", errorMsg);
                request.getRequestDispatcher("/manager/add_rule.jsp").forward(request, response);
            } else { // ok
                LocalDate publicDate = LocalDate.parse(
                        request.getParameter("publicDate"),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                Rule rule = new Rule(ruleName, description, publicDate);
                RuleDAO ruleDAO = new RuleDAO();
                int inserted = ruleDAO.insert(rule);
                doGet(request, response);
            }
        }

    }
    private void searchOrPagination(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RuleDAO ruleDAO = new RuleDAO();
        int pageSize = 5;
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        String searchName = "";
        if (request.getParameter("search") != null) {
            searchName = Validate.normalizeSearchString(request.getParameter("search")); // remove trailing spaces
        }

        List<Rule> ruleList;
        int totalPages;
        if (searchName.isEmpty()) { // search emtpy -> return full rule list
            ruleList = ruleDAO.selectAllPaging(pageNumber, pageSize);
            totalPages = (int) Math.ceil((double) ruleDAO.selectAll().size() / pageSize);
        } else {
            ruleList = ruleDAO.searchByNamePaging(searchName, pageNumber, pageSize);
            totalPages = (int) Math.ceil(
                    (double) ruleDAO.searchByName(searchName).size() / pageSize
            );
        }

        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("rulesList", ruleList);
        request.setAttribute("searchName", searchName);
        request.getRequestDispatcher("/manager/rule_management.jsp").forward(request, response);
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
        int id = Integer.parseInt(request.getParameter("ruleID"));

        String ruleName = "";
        String errorMsg = "";
        try {
            ruleName = Validate.validateRuleName(request.getParameter("ruleName"));
        } catch (Exception ex) {
            errorMsg += ex.getMessage() + "\n";
        }
        String description = "";
        try {
            description = Validate.validateRuleDescription(request.getParameter("ruleDescription"));
        } catch (Exception ex) {
            errorMsg += ex.getMessage() + "\n";
        }

        if (!errorMsg.isEmpty()) { // có lỗi validate ruleName/description
            request.getSession().setAttribute("error", errorMsg);
            response.sendRedirect(request.getContextPath() + "/manager/rule/edit?id=" + id);
        } else { // ok
            request.getSession().removeAttribute("error");
            LocalDate publicDate = LocalDate.parse(
                    request.getParameter("publicDate"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Rule rule = new Rule(id, ruleName, description, publicDate);
            RuleDAO ruleDAO = new RuleDAO();
            int update = ruleDAO.update(rule);
            doGet(request, response);
        }
    }
}
