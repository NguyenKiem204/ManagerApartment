/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.sql.Date;
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
    String lastMessage;

    public Resident(int residentId, String fullName, String password, String phoneNumber, String cccd, String email, LocalDate dob, String sex, String status, Image image, Role role) {
        this.residentId = residentId;
        this.fullName = fullName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.cccd = cccd;
        this.email = email;
        this.dob = dob;
        this.sex = sex;
        this.status = status;
        this.image = image;
        this.role = role;
    }

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

    public Resident(String fullName, String password, String phoneNumber, String cccd, String email, LocalDate dob, String sex, String status, Role role) {
        this.fullName = fullName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.cccd = cccd;
        this.email = email;
        this.dob = dob;
        this.sex = sex;
        this.status = status;
        this.role = role;
    }

    public Resident(int residentId, String fullName, String phoneNumber, String email) {
        this.residentId = residentId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Resident(int residentId) {
        this.residentId = residentId;
    }

    public Date getFormattedDate() {
        return Date.valueOf(dob); // Chuyển LocalDate -> SQL Date
    }
}
