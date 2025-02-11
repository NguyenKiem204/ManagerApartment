/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.time.LocalDate;
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
public class Resident {
    int residentId;
    String fullName;
    String password;
    String phoneNumber;
    String cccd;
    String email;
    LocalDate dob;
    String sex;
    String status;
    Image image;
    Role role;

    public Resident(String fullName, String password, String phoneNumber, String cccd, String mail, LocalDate dob, String sex, String status, Image image, Role role) {
        this.fullName = fullName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.cccd = cccd;
        this.email = mail;
        this.dob = dob;
        this.sex = sex;
        this.status = status;
        this.image = image;
        this.role = role;
    }

    
}

