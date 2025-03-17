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
import model.Rule;

/**
 *
 * @author Hoang-Tran
 */
@WebServlet(name="EditRuleManagementServlet", urlPatterns={"/manager/rule/edit"})
public class EditRuleManagementServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ruleID = request.getParameter("id");

        if (ruleID != null && !ruleID.trim().isEmpty()) {
            RuleDAO ruleDAO = new RuleDAO();
            Rule rule = ruleDAO.selectById(Integer.parseInt(ruleID));
            request.setAttribute("rule", rule);
            request.getRequestDispatcher("/manager/edit_rule.jsp").forward(request, response);
        }
    }
}
