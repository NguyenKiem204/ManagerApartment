/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Account;

/**
 *
 * @author nkiem
 */
public class Test {

    public static void main(String[] args) {
//        DBContext.testConnection();
        AccountDAO accountDAO = new AccountDAO();
//        Account account = new Account("nkiem347@gmail.com", "kiem@12345", "ACTIVE", "Male", "0402748385", "Nguyễn Văn Kiểm", "0336780144", 1);
//        int row = accountDAO.insert(account);
//        System.out.println(row);
//        Account account = new Account("kiemnvhe186025@fpt.edu.vn", "kiem@123456", "ACTIVE", "Male", "0402748385", "Nguyễn Văn Kiểm", "0336780144", "ahaha", 1);
//        int row = accountDAO.insert(account);
//        System.out.println(row);
if(accountDAO.existEmail("kiemnvhe186025@fpt.edu.vn")){
    System.out.println("True");
}else System.out.println("faile");
        
//        Account account = accountDAO.checkLogin("nkiem347@gmail.com", "kiem@12345");
//        if (account != null) {
//            System.out.println("Login success");
//        } else {
//            System.out.println("Fail");
//        }
    }
}
