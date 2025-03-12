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

    public TypeExpense() {
    }

    public TypeExpense(int typeExpenseID, String typeName) {
        this.typeExpenseID = typeExpenseID;
        this.typeName = typeName;
    }

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

  
    
}
