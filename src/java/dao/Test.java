/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.time.LocalDate;
import model.Account;
import model.Image;
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
        RoleDAO roleDAO = new RoleDAO();
        ImageDAO imageDAO = new ImageDAO();
        
        Role role = new Role("ADMIN", "All Permission");
        Image image = new Image("dhdhdhd");
        roleDAO.insert(role);
        imageDAO.insert(image);
        Staff staff = new Staff("Nguyễn Văn Kiểm", "12345", "0336780144", "4985489535", "nkiem347@gmail.com",  LocalDate.of(2020, 12, 12), "Female", "ACTIVE", 1, 1);
        staffDAO.insert(staff);
    }
}
