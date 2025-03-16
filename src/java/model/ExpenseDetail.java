/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nguye
 */
public class ExpenseDetail {

    private int expenseDetailID;
    private int expenseID;
    private TypeExpense typeExpense;
    private double amount;
    private String status;
    private String description;

    public ExpenseDetail() {
    }

    public ExpenseDetail(int expenseDetailID, int expenseID, TypeExpense typeExpense, double amount, String status, String description) {
        this.expenseDetailID = expenseDetailID;
        this.expenseID = expenseID;
        this.typeExpense = typeExpense;
        this.amount = amount;
        this.status = status;
        this.description = description;
    }

    public int getExpenseDetailID() {
        return expenseDetailID;
    }

    public void setExpenseDetailID(int expenseDetailID) {
        this.expenseDetailID = expenseDetailID;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TypeExpense getTypeExpense() {
        return typeExpense;
    }

    public void setTypeExpense(TypeExpense typeExpense) {
        this.typeExpense = typeExpense;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount > 0) {
            this.amount = amount;
        } else {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
