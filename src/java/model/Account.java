/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author nkiem
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {
    int accountId; 
    String mail;
    String password;
    String status;
    String sex;
    String cccd;
    String fullName;
    String phoneNumber;
    int roleId;

    public Account(String mail, String password, String status, String sex, String cccd, String fullName, String phoneNumber, int roleId) {
        this.mail = mail;
        this.password = password;
        this.status = status;
        this.sex = sex;
        this.cccd = cccd;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
    }
    
}
