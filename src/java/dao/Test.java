/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.time.LocalDate;
import java.util.List;
import model.Apartment;
import model.Feedback;
import model.Image;
import model.Request;
import model.Resident;
import model.Role;
import model.RoleEnum;
import model.Staff;
import model.TypeRequest;

/**
 *
 * @author nkiem
 */
public class Test {

    public static void main(String[] args) {
//        DBContext.testConnection();
        StaffDAO staffDAO = new StaffDAO();
        ResidentDAO residentDAO = new ResidentDAO();
        RoleDAO roleDAO = new RoleDAO();
        ImageDAO imageDAO = new ImageDAO();
        ApartmentDAO apartmentDAO = new ApartmentDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        RequestDAO requestDAO = new RequestDAO();
        TypeRequestDAO typeRequestDAO = new TypeRequestDAO();
        StatusRequestDAO statusRequestDAO = new StatusRequestDAO();
        
        requestDAO.updateStatus(2, 2);
        requestDAO.updateStatus(1, 1);
  
//        Role role = new Role("OWNER", "Some Permission");
//        Image image = new Image("./djhdjdhf/lo");
//        roleDAO.insert(role);
//Apartment apartment = new Apartment("606", "6", "ACTIVE", "RENT");
//apartmentDAO.insert(apartment);
//        Resident resident = new Resident("Nguyễn Văn Kiểm", "kiem@12345", "0336780144", "3984934834394", "nkiem347@gmail.com",  LocalDate.of(2020, 12, 12), "Female", "ACTIVE", new Image().builder().imageID(3).build(), new Role().builder().roleID(6).build());
//        residentDAO.insert(resident);
//        Resident resident = new Resident("Nguyễn Đăng Nguyên", "123", "1234560144", "0123944394", "nguyen05082004@gmail.com",  LocalDate.of(2020, 12, 12), "Female", "ACTIVE", new Image().builder().imageID(1).build(), new Role().builder().roleID(7).build());
//        residentDAO.insert(resident);
//        imageDAO.insert(image);
        Staff staff = new Staff("Nguyễn Quang Dũng", "123", "0325476666", "035204003553", "quangdungvt5822@gmail.com",  LocalDate.of(2004, 6, 27), "Male", "Active", new Image().builder().imageID(2).build(), new Role().builder().roleID(1).build());
        staffDAO.insert(staff);


//        Feedback fb = new Feedback("Account not good", "Good", LocalDate.of(2025, 01, 10), 4, 2, 1);
//        feedbackDAO.insert(fb);
//         List list = feedbackDAO.getAllFeedbacksSortedByStaff();
        
//         for (Object object : list) {
//             System.out.println(object.toString());
//        }

//        Request rq = new Request("Lam lai hanh lang cho tang 8", "Hanh lang bi hu hai", "hong", LocalDate.now(), 3, 2, 1);
//        System.out.println(requestDAO.insert(rq));
//        requestDAO.insert(rq);
//        List<Request> listrq = requestDAO.selectAll();
//        for (Request request : listrq) {
//            System.out.println(request.toString());
        }
//        System.out.println(residentDAO.getResidentDetailByID(3));
//        List<Staff> list = staffDAO.selectAll();
//        for (Staff staff : list) {
//            System.out.println(staff);
//        }
//
//    }
}
