/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Feedback {
    int feedbackID;
    String title;
    String description;
    LocalDate date;
    int rate;
    Staff staff;
    Resident resident;

    public Feedback(String title, String description, LocalDate date, int rate, Staff staff, Resident resident) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.rate = rate;
        this.staff = staff;
        this.resident = resident;
    }
    
}
