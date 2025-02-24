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
public class Request {

    int requestID;
    String description;
    String title;
    LocalDate date;
    StatusRequest status;
    Resident resident;
    TypeRequest typeRq;
    Apartment apartment;
    LocalDate completedAt;
    LocalDate viewedDate;

//    public Request(int requestID, String description, String title, LocalDate date, StatusRequest status, Resident resident, TypeRequest typeRq, Apartment apartment, LocalDate completedAt, LocalDate viewedDate) {
//        this.requestID = requestID;
//        this.description = description;
//        this.title = title;
//        this.date = date;
//        this.status = status;
//        this.resident = resident;
//        this.typeRq = typeRq;
//        this.apartment = apartment;
//        this.completedAt = completedAt;
//        this.viewedDate = viewedDate;
//    }

    public Request(String description, String title, LocalDate date, StatusRequest status, Resident resident, TypeRequest typeRq, Apartment apartment) {
        this.description = description;
        this.title = title;
        this.date = date;
        this.status = status;
        this.resident = resident;
        this.typeRq = typeRq;
        this.apartment = apartment;
    }
    

    public Request(String description, String title, LocalDate date, StatusRequest status, Resident resident, TypeRequest typeRq, Apartment apartment, LocalDate completedAt, LocalDate viewedDate) {
        this.description = description;
        this.title = title;
        this.date = date;
        this.status = status;
        this.resident = resident;
        this.typeRq = typeRq;
        this.apartment = apartment;
        this.completedAt = completedAt;
        this.viewedDate = viewedDate;
    }

    

    public Date getFormattedDate() {
        return Date.valueOf(date); // Chuyển LocalDate -> SQL Date
    }
    
}
