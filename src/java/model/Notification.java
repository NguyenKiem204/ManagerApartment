/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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
public class Notification {
    
    int notificationID;
    String message;
    String type;
    LocalDate createdAt;
    boolean isRead;
    int referenceId;
    String referenceTable;
    Staff staff;
    Resident resident;

    public Notification(String message, String type, LocalDate createdAt, boolean isRead, int referenceId, String referenceTable, Staff staff, Resident resident) {
        this.message = message;
        this.type = type;
        this.createdAt = createdAt;
        this.isRead = isRead;
        this.referenceId = referenceId;
        this.referenceTable = referenceTable;
        this.staff = staff;
        this.resident = resident;
    }
}
