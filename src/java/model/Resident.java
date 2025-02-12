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
    String mail;
    LocalDate dob;
    String sex;
    String status;
    int imageId;
    int roleId;

    public Resident(String fullName, String password, String phoneNumber, String cccd, String mail, LocalDate dob, String sex, String status, int imageId, int roleId) {
        this.fullName = fullName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.cccd = cccd;
        this.mail = mail;
        this.dob = dob;
        this.sex = sex;
        this.status = status;
        this.imageId = imageId;
        this.roleId = roleId;
    }
     public Resident(int residentId, String fullName, String phoneNumber, String mail) {
        this.residentId = residentId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }
<<<<<<< Updated upstream
=======
    public Resident(int residentId, String fullName, String phoneNumber, String email) {
        this.residentId = residentId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
>>>>>>> Stashed changes
}

