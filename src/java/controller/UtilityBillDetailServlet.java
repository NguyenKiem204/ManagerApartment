/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;
import dao.UtilityBillDAO;
import dao.UtilityRateDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.Resident;
import model.UtilityBill;
import model.UtilityRate;

/**
 *
 * @author nkiem
 */
@WebServlet(name="UtilityBillDetailServlet", urlPatterns={"/utility-detail"})
public class UtilityBillDetailServlet extends HttpServlet {
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        UtilityBillDAO utilityBillDAO = new UtilityBillDAO();
        UtilityRateDAO utilityRateDAO = new UtilityRateDAO();
        UtilityRate electricity = utilityRateDAO.getCurrentUtilityRate("Electricity", LocalDateTime.now());
        UtilityRate water = utilityRateDAO.getCurrentUtilityRate("Water", LocalDateTime.now());
        Resident resident = (Resident)request.getSession().getAttribute("resident");
        
        String utilityIdParam = request.getParameter("utilityId");
        Integer uilityId = null;
        if(utilityIdParam!=null){
            uilityId = Integer.parseInt(utilityIdParam);
            UtilityBill utilityBill = utilityBillDAO.getBillDetails(uilityId);
            if(resident==null || resident.getResidentId() != utilityBill.getOwner().getResidentId()){
                forwardToErrorPage(request, response, 403, "You do not have permission to access this resource.");
                return;
            }
            System.out.println("Status của InvoiceID : " + utilityBillDAO.isInvoicePaid(utilityBill.getInvoiceId()));

            if(utilityBillDAO.isInvoicePaid(utilityBill.getInvoiceId())){
                request.setAttribute("status", true);
            }else{
                request.setAttribute("status", false);
            }
            request.setAttribute("utilityBill", utilityBill);
        }
        System.out.println(water);
        request.setAttribute("electricity", electricity);
        request.setAttribute("water", water);
       request.getRequestDispatcher("utilitybill-detail.jsp").forward(request, response);
    } 
    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response, int statusCode, String errorMessage) throws ServletException, IOException {
        request.setAttribute("errorCode", statusCode);
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/error-exception.jsp").forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
