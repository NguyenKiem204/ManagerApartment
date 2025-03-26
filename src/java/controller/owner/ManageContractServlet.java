/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.owner;

import dao.ApartmentDAO;
import dao.ContractDAO;
import dao.ResidentDAO;
import dao.RoleDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;
import model.Apartment;
import model.Contract;
import model.EmailUtil;
import model.Resident;
import model.Role;
import validation.Validate;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import org.json.JSONObject;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10, // 10MB
    maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(name="ManageContractServlet", urlPatterns={"/owner/manageContract"})
public class ManageContractServlet extends HttpServlet {
    private ResidentDAO residentDAO = new ResidentDAO();
    private ContractDAO contractDAO = new ContractDAO();
    private ApartmentDAO apartmentDAO = new ApartmentDAO();
    private RoleDAO roleDAO = new RoleDAO();
    private static final String UPLOAD_DIR = "/assets/contract/";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    Resident loggedInResident = (Resident) request.getSession().getAttribute("resident");

    if (loggedInResident == null || loggedInResident.getRole().getRoleID() != 7) {
        response.sendRedirect("/login.jsp");
        return;
    }

    String keyword = request.getParameter("search");
    String sex = request.getParameter("sex");
    String status = request.getParameter("status");

    int page = 1;
    int pageSize = 5;

    if (request.getParameter("page") != null) {
        page = Integer.parseInt(request.getParameter("page"));
    }

    ResidentDAO residentDAO = new ResidentDAO();
    List<Resident> listTenant = residentDAO.getTenantsByOwner(
        loggedInResident.getResidentId(), keyword, sex, status, page, pageSize
    );

    int totalRecords = residentDAO.countTenantsByOwner(loggedInResident.getResidentId(), keyword, sex, status);
    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
    ApartmentDAO apartmentDAO = new ApartmentDAO();
    List<Apartment> apartmentList = apartmentDAO.getApartmentsByOwner(loggedInResident.getResidentId());
    request.setAttribute("apartmentList", apartmentList);
    request.setAttribute("listTenant", listTenant);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("currentPage", page);
    request.setAttribute("search", keyword);
    request.setAttribute("sex", sex);
    request.setAttribute("status", status);

    request.getRequestDispatcher("mngcontract.jsp").forward(request, response);
}

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
//        JSONObject jsonResponse = new JSONObject();
//
//        try {
//            // 1️⃣ Nhận dữ liệu từ request
//            String fullName = request.getParameter("fullName");
//            String phoneNumber = request.getParameter("phoneNumber");
//            String cccd = request.getParameter("cccd");
//            String email = request.getParameter("email");
//            String sex = request.getParameter("sex");
//            String dobStr = request.getParameter("dob");
//            String apartmentIdRaw = request.getParameter("apartmentId");
//            String leaseStartDateStr = request.getParameter("leaseStartDate");
//            String leaseEndDateStr = request.getParameter("leaseEndDate");
//
//            // 2️⃣ Kiểm tra dữ liệu đầu vào
//            if (fullName == null || fullName.trim().isEmpty() || !fullName.matches("^[A-Za-zÀ-ỹ\\s]+$")) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "Full name cannot be empty or contains special characters!");
//                out.write(jsonResponse.toString());
//                return;
//            }
//            String dobError = Validate.validateDob(dobStr);
//            if(dobError != null) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", dobError);
//                out.write(jsonResponse.toString());
//                return;
//            }
//            if (!phoneNumber.matches("\\d{10}")) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "Phone number must have 10 digits!");
//                out.write(jsonResponse.toString());
//                return;
//            }
//
//            if (!cccd.matches("\\d{12}")) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "CCCD must have 12 digits!");
//                out.write(jsonResponse.toString());
//                return;
//            }
//            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "Invalid email format!");
//                out.write(jsonResponse.toString());
//                return;
//            }
//
//            if (residentDAO.isResidentExists(phoneNumber, cccd, email)) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "PhoneNumber, CCCD, or Email already exists!");
//                out.write(jsonResponse.toString());
//                return;
//            }
//
//            if (apartmentIdRaw == null || apartmentIdRaw.isEmpty()) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "Apartment is required!");
//                out.write(jsonResponse.toString());
//                return;
//            }
//
//            int apartmentId = Integer.parseInt(apartmentIdRaw);
//
//            if (leaseStartDateStr == null || leaseEndDateStr == null) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "Lease start and end dates are required!");
//                out.write(jsonResponse.toString());
//                return;
//            }
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
//            LocalDate leaseStartDate = LocalDate.parse(leaseStartDateStr, formatter);
//            LocalDate leaseEndDate = LocalDate.parse(leaseEndDateStr, formatter);
//
//            if (leaseStartDate.isAfter(leaseEndDate)) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "Lease start date must be before end date!");
//                out.write(jsonResponse.toString());
//                return;
//            }
//            if (contractDAO.isOverlappingContract(apartmentId, leaseStartDate, leaseEndDate)) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "This apartment is already rented during the selected period!");
//                out.write(jsonResponse.toString());
//                return;
//            }
//
//
//            
//            LocalDate dob = LocalDate.parse(dobStr, formatter);
//            String password = generateRandomPassword(5);
//            Role role = roleDAO.selectById(6); // Tenant role
//            Resident newTenant = new Resident(fullName, password, phoneNumber, cccd, email, dob, sex, "Active", role);
//
//            int tenantId = residentDAO.insert1(newTenant);
//            if (tenantId <= 0) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "Failed to insert tenant.");
//                out.write(jsonResponse.toString());
//                return;
//            }
//
//            // 4️⃣ Tạo hợp đồng thuê (Contract)
//            Contract contract = new Contract();
//            contract.setResident(new Resident(tenantId));
//            contract.setApartment(new Apartment(apartmentId));
//            contract.setLeaseStartDate(leaseStartDate);
//            contract.setLeaseEndDate(leaseEndDate);
//
//            int contractResult = contractDAO.insert(contract);
//            if (contractResult <= 0) {
//                jsonResponse.put("success", false);
//                jsonResponse.put("message", "Failed to create lease contract.");
//                out.write(jsonResponse.toString());
//                return;
//            }
//
//            // 5️⃣ Gửi email chứa mật khẩu
//            EmailUtil.sendEmail(email, password);
//
//            jsonResponse.put("success", true);
//            jsonResponse.put("message", "Tenant added successfully! Password sent to email.");
//        } catch (Exception e) {
//            jsonResponse.put("success", false);
//            jsonResponse.put("message", "Error: " + e.getMessage());
//        }
//
//        out.write(jsonResponse.toString());
//    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();

        try {
            //Nhận dữ liệu từ request
            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String cccd = request.getParameter("cccd");
            String email = request.getParameter("email");
            String sex = request.getParameter("sex");
            String dobStr = request.getParameter("dob");
            String apartmentIdRaw = request.getParameter("apartmentId");
            String leaseStartDateStr = request.getParameter("leaseStartDate");
            String leaseEndDateStr = request.getParameter("leaseEndDate");

            // 2️⃣ Kiểm tra dữ liệu đầu vào
            if (fullName == null || fullName.trim().isEmpty() || !fullName.matches("^[A-Za-zÀ-ỹ\\s]+$")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Full name cannot be empty or contain special characters!");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            String dobError = Validate.validateDob(dobStr);
            if (dobError != null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", dobError);
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            if (!phoneNumber.matches("\\d{10}")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Phone number must have 10 digits!");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            if (!cccd.matches("\\d{12}")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "CCCD must have 12 digits!");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Invalid email format!");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            if (residentDAO.isResidentExists(phoneNumber, cccd, email)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Phone number, CCCD, or Email already exists!");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            if (apartmentIdRaw == null || apartmentIdRaw.isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Apartment is required!");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            int apartmentId = Integer.parseInt(apartmentIdRaw);

            if (leaseStartDateStr == null || leaseEndDateStr == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Lease start and end dates are required!");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate leaseStartDate = LocalDate.parse(leaseStartDateStr, formatter);
            LocalDate leaseEndDate = LocalDate.parse(leaseEndDateStr, formatter);

            if (leaseStartDate.isAfter(leaseEndDate)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Lease start date must be before end date!");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            if (contractDAO.isOverlappingContract(apartmentId, leaseStartDate, leaseEndDate)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "This apartment is already rented during the selected period!");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            // nhận file từ request
            Part filePart = request.getPart("contractFile");
            String filePath = null;

            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

                if (!fileExtension.equals(".doc") && !fileExtension.equals(".docx") && !fileExtension.equals(".pdf")) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Only .doc, .docx and .pdf files are allowed!");
                    response.getWriter().write(jsonResponse.toString());
                    return;
                }

                // Lưu file
                String uploadPath = getServletContext().getRealPath(UPLOAD_DIR);
                //String uploadPath = "E:\\ManagerApartment\\web\\assets\\contract"; // Đường dẫn tuyệt đối
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String newFileName = UUID.randomUUID() + "_" + fileName;
                File file = new File(uploadPath, newFileName);
                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                filePath = UPLOAD_DIR + newFileName;
            }

            // Tạo tenant
            LocalDate dob = LocalDate.parse(dobStr, formatter);
            String password = generateRandomPassword(5);
            Role role = roleDAO.selectById(6); // Tenant role
            Resident newTenant = new Resident(fullName, password, phoneNumber, cccd, email, dob, sex, "Active", role);

            int tenantId = residentDAO.insert1(newTenant);
            if (tenantId <= 0) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to insert tenant.");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            // Tạo contract
            Contract contract = new Contract();
            contract.setResident(new Resident(tenantId));
            contract.setApartment(new Apartment(apartmentId));
            contract.setLeaseStartDate(leaseStartDate);
            contract.setLeaseEndDate(leaseEndDate);
            contract.setFilePath(filePath); // Lưu đường dẫn file hợp đồng

            int contractResult = contractDAO.insert1(contract);
            if (contractResult <= 0) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to create lease contract.");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            // 6️⃣ Gửi email
            EmailUtil.sendEmail(email, password);

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Tenant added successfully! Contract uploaded.");
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error: " + e.getMessage());
        }

        response.getWriter().write(jsonResponse.toString());
    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
