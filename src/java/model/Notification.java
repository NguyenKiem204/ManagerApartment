/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    int notificationId;
    int senderId;
    String senderTable;
    String message;
    String type;
    LocalDateTime createdAt;
    boolean isRead;
    int referenceId;
    String referenceTable;
    Staff staff;
    Resident resident;

    public Notification(int senderId, String senderTable, String message, String type, LocalDateTime createdAt, boolean isRead, int referenceId, String referenceTable, Staff staff, Resident resident) {
        this.senderId = senderId;
        this.senderTable = senderTable;
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
