/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.owner;

import dao.ContractDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Contract;

/**
 *
 * @author fptshop
 */
@WebServlet(name="ViewContractServlet", urlPatterns={"/owner/viewContract"})
public class ViewContractServlet extends HttpServlet {
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int tenantId = Integer.parseInt(request.getParameter("tenantId"));
        ContractDAO contractDAO = new ContractDAO();
        Contract contract = contractDAO.getContractByResidentId(tenantId);

        request.setAttribute("contract", contract);
        request.getRequestDispatcher("viewcontract.jsp").forward(request, response);
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

}
