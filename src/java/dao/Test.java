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

/**
 *
 * @author nkiem
 */
public class Test {

    public static void main(String[] args) {
//        DBContext.testConnection();
//        AccountDAO accountDAO = new AccountDAO();
//        Account account = new Account("nkiem347@gmail.com", "kiem@12345", "ACTIVE", "Male", "0402748385", "Nguyễn Văn Kiểm", "0336780144", 1, "img");
//        int row = accountDAO.insert(account);
//        Account account = new Account("nkiem347@gmail.com", "kiem@12345", "ACTIVE", "Male", "0402748385", "Nguyễn Văn Kiểm", "0336780144", "img", 1);
//        int row = accountDAO.insert(account);
//        Account accountRe = new Account("re1@gmail.com", "123", "ACTIVE", "Male", "0402123477", "Hoang Thi Cau", "0336123144",2, "img.prn");
//        int row1 = accountDAO.insert(accountRe);
//        System.out.println(row);
//        Account account = new Account("kiemnvhe186025@fpt.edu.vn", "kiem@123456", "ACTIVE", "Male", "0402748385", "Nguyễn Văn Kiểm", "0336780144", "ahaha", 1);
//        int row = accountDAO.insert(account);
//        System.out.println(row);
//        if (accountDAO.existEmail("kiemnvhe186025@fpt.edu.vn")) {
//            System.out.println("True");
//        } else {
//            System.out.println("faile");
//        }

//        Account account = accountDAO.checkLogin("nkiem347@gmail.com", "kiem@12345");
//        if (account != null) {
//            System.out.println("Login success");
//        } else {
//            System.out.println("Fail");
//        }
        StaffDAO staffDAO = new StaffDAO();
        ResidentDAO residentDAO = new ResidentDAO();
        RoleDAO roleDAO = new RoleDAO();
        ImageDAO imageDAO = new ImageDAO();
        ApartmentDAO apartmentDAO = new ApartmentDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        RequestDAO requestDAO = new RequestDAO();
        List<Role> listr = roleDAO.selectAll();
        for (Role role : listr) {
            System.out.println(role.toString());
        }
        
//        Role role = new Role("OWNER", "Some Permission");
//        Image image = new Image("./djhdjdhf/lo");
//        roleDAO.insert(role);
//Apartment apartment = new Apartment("606", "6", "ACTIVE", "RENT");
//apartmentDAO.insert(apartment);
//        Resident resident = new Resident("Nguyễn Đăng Nguyên", "123", "048758747", "598695869", "nguyen05082004@gmail.com", LocalDate.of(2004, 04, 10), "Female", "ACTIVE", imageDAO.selectById(1), roleDAO.selectById(7));
//        residentDAO.insert(resident);
//        imageDAO.insert(image);
        Staff staff = new Staff("Nguyễn Văn Kiểm", "kiem@12345", "0336780144", "3984934834394", "kiemnvhe186025@fpt.edu.vn",  LocalDate.of(2020, 12, 12), "Female", "ACTIVE", new Image().builder().imageID(1).build(), new Role().builder().roleID(6).build());
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
//StaffDetail staff = new StaffDetail(4, "Nguyễn Kiểm", "033456789", "837483487", "kiemnvhe186025@fpt.edu.vn", LocalDate.of(2004, 04, 10), "Male", "", "/asset/images/hi.jpn", "", 1, 1);
//staffDAO.updateProfileStaff(staff);
//    }
}
