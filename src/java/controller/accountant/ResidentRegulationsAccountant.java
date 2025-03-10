/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.accountant;

import controller.tenant.*;
import dao.RuleDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Rule;

@WebServlet(name = "ResidentRegulationsAccountant", urlPatterns = {"/accountant/regulations"})
public class ResidentRegulationsAccountant extends HttpServlet {

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
        request.getRequestDispatcher("/accountant/view_regulations.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RuleDAO ruleDAO = new RuleDAO();
        int pageSize = 5;
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        String searchName = "";
        if (request.getParameter("search") != null) {
            searchName = request.getParameter("search").trim(); // remove trailing spaces
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
        request.getRequestDispatcher("/accountant/view_regulations.jsp").forward(request, response);
    }
}
