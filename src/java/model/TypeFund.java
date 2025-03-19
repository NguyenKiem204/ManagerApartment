/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nguye
 */
public class TypeFund {
    private int typeFundID;
    private String typeName;
    private double defaultAmount;

    public TypeFund() {
    }

    public TypeFund(int typeFundID, String typeName, double defaultAmount) {
        this.typeFundID = typeFundID;
        this.typeName = typeName;
        this.defaultAmount = defaultAmount;
    }

    public int getTypeFundID() {
        return typeFundID;
    }

    public void setTypeFundID(int typeFundID) {
        this.typeFundID = typeFundID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(double defaultAmount) {
        this.defaultAmount = defaultAmount;
    }
}
