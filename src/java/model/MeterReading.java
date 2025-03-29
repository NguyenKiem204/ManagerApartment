/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class MeterReading {
    int readingId;
    int meterId;
    LocalDateTime readingDate;
    BigDecimal previousReading;
    BigDecimal currentReading;
    BigDecimal consumption;
    int staffId;
    int readingMonth;
    int readingYear;
    String status;
    
    String meterNumber;
    String meterType;
    int apartmentId;
    String apartmentName;
    String ownerName;
    String staffName;
    
    public String getFormattedDate() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return readingDate.format(formatter);
}
}
