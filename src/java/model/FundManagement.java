/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author nguye
 */
public class FundManagement {
    private int fundID;
    private String fundName;
    private double totalAmount;
    private double currentBalance;
    private LocalDate createdAt;
    private String status;
    private int typeFundID;
    private int createdBy;

    public FundManagement() {
    }

    public FundManagement(int fundID, String fundName, double totalAmount, double currentBalance, LocalDate createdAt, String status, int typeFundID, int createdBy) {
        this.fundID = fundID;
        this.fundName = fundName;
        this.totalAmount = totalAmount;
        this.currentBalance = currentBalance;
        this.createdAt = createdAt;
        this.status = status;
        this.typeFundID = typeFundID;
        this.createdBy = createdBy;
    }

    

    public int getFundID() {
        return fundID;
    }

    public void setFundID(int fundID) {
        this.fundID = fundID;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTypeFundID() {
        return typeFundID;
    }

    public void setTypeFundID(int typeFundID) {
        this.typeFundID = typeFundID;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    } 
}
