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
        DBContext.testConnection();
        AccountDAO accountDAO = new AccountDAO();
//        Account account = new Account("nkiem347@gmail.com", "kiem@12345", "ACTIVE", "Male", "0402748385", "Nguyễn Văn Kiểm", "0336780144", 1, "img");
//        int row = accountDAO.insert(account);
        Account account = new Account("nkiem347@gmail.com", "kiem@12345", "ACTIVE", "Male", "0402748385", "Nguyễn Văn Kiểm", "0336780144", 1, "img");
        int row = accountDAO.insert(account);
//        Account accountRe = new Account("re1@gmail.com", "123", "ACTIVE", "Male", "0402123477", "Hoang Thi Cau", "0336123144",2, "img.prn");
//        int row1 = accountDAO.insert(accountRe);
//        System.out.println(row);
        Account account1 = accountDAO.checkLogin("st1@gmail.com", "123");
        if (account1 != null) {
            System.out.println("Login success");
        } else {
            System.out.println("Fail");
        }
    }
}
