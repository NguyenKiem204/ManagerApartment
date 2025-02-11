/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.ResidentDAO;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Resident;

/**
 *
 * @author fptshop
 */
@WebServlet(name="ManageResidentControl", urlPatterns={"/manageResident"})
public class ManageResidentServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    String sex = request.getParameter("sex");
    String status = request.getParameter("status");
    String searchKeyword = request.getParameter("searchKeyword"); 

    ResidentDAO residentDAO = new ResidentDAO();
    List<Resident> listResident;

    if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
        listResident = residentDAO.searchResidents(searchKeyword.trim(), sex, status);
    } else {
        listResident = residentDAO.getAllResidents(sex, status);
    }

    request.setAttribute("listResident", listResident);
    request.setAttribute("selectedSex", sex);
    request.setAttribute("selectedStatus", status);
    request.setAttribute("searchKeyword", searchKeyword); 

    request.getRequestDispatcher("mngresident.jsp").forward(request, response);
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String txtSearch = request.getParameter("valueSearch");
        ResidentDAO dao = new ResidentDAO();

        //List<Resident> list = dao.selectById(Integer.parseInt(txtSearch));

        //request.setAttribute("listUser", list);
        //request.setAttribute("searchValue", txtSearch);
        //request.getRequestDispatcher("dashboard/mngaccount.jsp").forward(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
