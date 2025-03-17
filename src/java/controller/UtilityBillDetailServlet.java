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
import model.UtilityBill;
import model.UtilityRate;

/**
 *
 * @author nkiem
 */
@WebServlet(name="UtilityBillDetailServlet", urlPatterns={"/utility-detail"})
public class UtilityBillDetailServlet extends HttpServlet {
  
   
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        UtilityBillDAO utilityBillDAO = new UtilityBillDAO();
        UtilityRateDAO utilityRateDAO = new UtilityRateDAO();
        UtilityRate electricity = utilityRateDAO.getCurrentUtilityRate("Electricity", LocalDateTime.now());
        UtilityRate water = utilityRateDAO.getCurrentUtilityRate("Water", LocalDateTime.now());
        
        String utilityIdParam = request.getParameter("utilityId");
        Integer uilityId = null;
        if(utilityIdParam!=null){
            uilityId = Integer.parseInt(utilityIdParam);
            UtilityBill utilityBill = utilityBillDAO.getBillDetails(uilityId);
            request.setAttribute("utilityBill", utilityBill);
        }
        System.out.println(water);
        request.setAttribute("electricity", electricity);
        request.setAttribute("water", water);
       request.getRequestDispatcher("utilitybill-detail.jsp").forward(request, response);
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
