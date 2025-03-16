/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author nguye
 */
public class Expense {

    private int expenseID;
    private LocalDate expenseDate;
    private int staffID;
    private double totalAmount;
    private List<ExpenseDetail> expenseDetails; // Danh sách các ExpenseDetail

    // Constructor
    public Expense(int expenseID, LocalDate expenseDate, int staffID, double totalAmount, List<ExpenseDetail> expenseDetails) {
        this.expenseID = expenseID;
        this.expenseDate = expenseDate;
        this.staffID = staffID;
        this.totalAmount = totalAmount;
        this.expenseDetails = expenseDetails;
    }

    // Getters và Setters
    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<ExpenseDetail> getExpenseDetails() {
        return expenseDetails;
    }

    public void setExpenseDetails(List<ExpenseDetail> expenseDetails) {
        this.expenseDetails = expenseDetails;
    }
     public String getexpenseDateft() {
        if (expenseDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return expenseDate.format(formatter);
    }
}
