/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.administrative;

import dao.ApartmentDAO;
import dao.CommentDAO;
import dao.FeedbackDAO;
import dao.InvoiceDAO;
import dao.MessageDAO;
import dao.NewsDAO;
import dao.RequestDAO;
import dao.ResidentDAO;
import dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import model.Comment;
import model.Feedback;
import model.News;
import model.Resident;
import model.Staff;
import org.json.JSONArray;

/**
 *
 * @author admin
 */
@WebServlet(name = "HomeAdministrativeServlet", urlPatterns = {"/administrative/home"})
public class HomeAdministrativeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomeAdministrativeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeAdministrativeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ApartmentDAO apartmentDAO = new ApartmentDAO();
        ResidentDAO residentDAO = new ResidentDAO();
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        CommentDAO commentDAO = new CommentDAO();
        RequestDAO requestDAO = new RequestDAO();
        Map<String, Integer> requestData = requestDAO.getRequestCountLast7Days();

        JSONArray dateArray = new JSONArray();
        JSONArray valueArray = new JSONArray();

        for (Map.Entry<String, Integer> entry : requestData.entrySet()) {
            dateArray.put(entry.getKey() + "T00:00:00.000Z");
            valueArray.put(entry.getValue());
        }
        request.setAttribute("requests", requestData);
        request.setAttribute("chartDates", dateArray.toString());
        request.setAttribute("chartData", valueArray.toString());
        List<Comment> comments = commentDAO.selectTop2CommentRecent();
        int numberRequest = requestDAO.getCountStatusPending();
        int numberUnpaidInvoice = invoiceDAO.getCountInvoiceUnpaid();
        int numberAptm = apartmentDAO.numberApartment();
        int numberResident = residentDAO.numberResident();
        int numberApartmentOccupied = apartmentDAO.numberApartmentOccupied();
        int numberApartmentAvailable = apartmentDAO.numberApartmentAvailable();
        int year = LocalDate.now().getYear();
        List<Double> listRevenue = invoiceDAO.getRevenueByMonth(year);
        request.setAttribute("listRevenue", listRevenue);
        request.setAttribute("numberApartment", numberAptm);
        request.setAttribute("numberResident", numberResident);
        request.setAttribute("numberApartmentOccupied", numberApartmentOccupied);
        request.setAttribute("numberApartmentAvailable", numberApartmentAvailable);
        request.setAttribute("comments", comments);
        request.setAttribute("numberRequest", numberRequest);
        request.setAttribute("numberUnpaidInvoice", numberUnpaidInvoice);
        Staff staff = (Staff) request.getSession().getAttribute("staff");
        String currentUserEmail = staff.getEmail();
        StaffDAO staffDAO = new StaffDAO();
//        MessageDAO messageDAO = new MessageDAO();
//
//        List<Staff> listStaff = staffDAO.selectAllSortedByLastMessage(currentUserEmail);
//        List<Resident> listResident = residentDAO.selectAllSortedByLastMessage(currentUserEmail);
//
//        listStaff.removeIf(s -> s.getEmail().equals(currentUserEmail));
//        listResident.removeIf(r -> r.getEmail().equals(currentUserEmail));
//
//        List<Object> combinedList = new ArrayList<>();
//        combinedList.addAll(listStaff);
//        combinedList.addAll(listResident);
//
//        Map<String, java.sql.Timestamp> lastMessageMap = messageDAO.getLastMessageTimestamps(currentUserEmail);
//        combinedList.sort(Comparator.comparing(o -> {
//            String email = (o instanceof Staff) ? ((Staff) o).getEmail() : ((Resident) o).getEmail();
//            return lastMessageMap.getOrDefault(email, Timestamp.valueOf("1970-01-01 00:00:00"));
//        }, Comparator.reverseOrder()));
//        List<Object> list = combinedList.subList(0, 3);
//        request.setAttribute("list", list);

        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<Feedback> listFeedbacks = feedbackDAO.getLastestFeedback(2);
        request.setAttribute("latestFeedback", listFeedbacks);  // Truyền dữ liệu tin tức v

        NewsDAO newsDAO = new NewsDAO();
        List<News> listNews = newsDAO.getLastestNews(2);
        request.setAttribute("latestNews", listNews);  // Truyền dữ liệu tin tức v
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
