/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nguye
 */
public class TypeExpense {

    private int typeExpenseID;
    private String typeName;
    private boolean fixed; // 1 (true) là cố định, 0 (false) là không cố định
    private int typeFundID;

    // Constructors
    public TypeExpense() {
    }

    public TypeExpense(int typeExpenseID, String typeName, boolean fixed) {
        this.typeExpenseID = typeExpenseID;
        this.typeName = typeName;
        this.fixed = fixed;
    }

    public TypeExpense(int typeExpenseID, String typeName, boolean fixed, int typeFundID) {
        this.typeExpenseID = typeExpenseID;
        this.typeName = typeName;
        this.fixed = fixed;
        this.typeFundID = typeFundID;
    }

    public int getTypeFundID() {
        return typeFundID;
    }

    public void setTypeFundID(int typeFundID) {
        this.typeFundID = typeFundID;
    }

   
    // Getters and Setters
    public int getTypeExpenseID() {
        return typeExpenseID;
    }

    public void setTypeExpenseID(int typeExpenseID) {
        this.typeExpenseID = typeExpenseID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

}
