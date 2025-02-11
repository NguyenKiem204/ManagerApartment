/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.time.LocalDate;
import java.util.List;
import model.Account;
import model.Apartment;
import model.Image;
import model.Resident;
import model.Role;
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
        
//        Role role = new Role("OWNER", "Some Permission");
//        Image image = new Image("./djhdjdhf/lo");
//        roleDAO.insert(role);
//Apartment apartment = new Apartment("606", "6", "ACTIVE", "RENT");
//apartmentDAO.insert(apartment);
        //Resident resident = new Resident("Nguyễn Đăng Nguyên", "123", "048758747", "598695869", "nguyen05082004@gmail.com", LocalDate.of(2004, 04, 10), "Female", "ACTIVE", 1, 2);
       //residentDAO.insert(resident);
       Resident resident1 = new Resident("Nguyễn Đăng Nguyênnnn", "123", "0487587471", "5986958691", "nguyenn05082004@gmail.com", LocalDate.of(2004, 04, 10), "Female", "ACTIVE", 1, 2);
       residentDAO.insert(resident1);
       Resident resident2 = new Resident("Hương béo", "123", "0487587472", "5986958692", "huongbeo@gmail.com", LocalDate.of(2004, 04, 10), "Male", "ACTIVE", 1, 2);
       residentDAO.insert(resident2);
//        imageDAO.insert(image);
//        Staff staff = new Staff("Lonel Messi", "12345", "0985747574", "989999989989", "kiemnvhe186025@fpt.edu.vn",  LocalDate.of(2020, 12, 12), "Female", "ACTIVE", 1, 2);
//        staffDAO.insert(staff);
 /*ResidentDAO dao = new ResidentDAO();
        List<Resident> residents = dao.selectAll();

        if (residents.isEmpty()) {
            System.out.println("⚠️ Không có dữ liệu trong danh sách Resident!");
        } else {
            System.out.println("✅ Dữ liệu Resident:");
            for (Resident r : residents) {
                System.out.println("ID: " + r.getResidentId() + ", Name: " + r.getFullName());
            }
        }*/



}}


