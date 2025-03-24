/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private TypeFund typeFund;
    private int createdBy;
    private List<TransactionFund> transaction;

    public FundManagement(int fundID, String fundName, double totalAmount, double currentBalance, LocalDate createdAt, String status, TypeFund typeFund, int createdBy, List<TransactionFund> transaction) {
        this.fundID = fundID;
        this.fundName = fundName;
        this.totalAmount = totalAmount;
        this.currentBalance = currentBalance;
        this.createdAt = createdAt;
        this.status = status;
        this.typeFund = typeFund;
        this.createdBy = createdBy;
        this.transaction = transaction;
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

    public String getCreatedAtft() {
        if (createdAt == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return createdAt.format(formatter);
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

    public TypeFund getTypeFund() {
        return typeFund;
    }

    public void setTypeFund(TypeFund typeFund) {
        this.typeFund = typeFund;
    }

    public List<TransactionFund> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<TransactionFund> transaction) {
        this.transaction = transaction;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
