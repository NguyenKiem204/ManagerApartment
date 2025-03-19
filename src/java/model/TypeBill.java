/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nguye
 */
public class TypeBill {

    private int id;
    private String name;
    private int typeFundID;

    public TypeBill(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TypeBill(int id, String name, int typeFundID) {
        this.id = id;
        this.name = name;
        this.typeFundID = typeFundID;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
