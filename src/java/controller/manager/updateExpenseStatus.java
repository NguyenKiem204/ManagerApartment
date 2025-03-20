/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dao.ExpenseDAO;
import dao.FundDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ExpenseDetail;
import model.TypeExpense;

/**
 *
 * @author nguye
 */
@WebServlet(name = "updateExpenseStatus", urlPatterns = {"/manager/updateExpenseStatus"})
public class updateExpenseStatus extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet updateExpenseStatus</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateExpenseStatus at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String expenseDetailID = request.getParameter("expenseDetailID");
        String status = request.getParameter("status");
        String typeid = request.getParameter("typeid");
        String amo = request.getParameter("amount");
        String description = request.getParameter("des");
        ExpenseDAO edao = new ExpenseDAO();
        FundDAO fdao = new FundDAO();
        try {
            int id = Integer.parseInt(expenseDetailID);
            int typeexp = Integer.parseInt(typeid);
            double amount = Double.parseDouble(amo);
            if (status.equals("Approved")) {
                edao.updateStatusExpense(id, status);
                TypeExpense typeExpense = edao.getTypeExpenseById(typeexp);
                int fundID = fdao.getFundIDByTypeFund(typeExpense.getTypeFundID());
                double currentBalance = fdao.getCurrentBalance(fundID);
                if (currentBalance < amount) {
                    request.setAttribute("errorMessage", "Không đủ số dư trong quỹ để thực hiện giao dịch.");
                    request.getRequestDispatcher("FundManager").forward(request, response);
                    return;
                }

                boolean transactionInserted = fdao.insertTransaction(fundID, amount, "Expense", description, 3);
                if (!transactionInserted) {
                    throw new SQLException("Không thể thêm giao dịch vào bảng TransactionFund.");
                }

                edao.updateTotalAmount(id);
                request.getRequestDispatcher("FundManager").forward(request, response);
            } else {
                try {
                    edao.updateStatusExpense(id, status);
                    request.getRequestDispatcher("FundManager").forward(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(updateExpenseStatus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(updateExpenseStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
