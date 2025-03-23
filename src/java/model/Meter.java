/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class Meter {

    int meterId;
    int apartmentId;
    String meterType;
    String meterNumber;
    LocalDateTime installationDate;
    String status;

    String apartmentName;
    String ownerName;
    BigDecimal lastReading;

    public Meter() {
    }

    public Meter(int meterId, int apartmentId, String meterType, String meterNumber, LocalDateTime installationDate, String status, String apartmentName, String ownerName, BigDecimal lastReading) {
        this.meterId = meterId;
        this.apartmentId = apartmentId;
        this.meterType = meterType;
        this.meterNumber = meterNumber;
        this.installationDate = installationDate;
        this.status = status;
        this.apartmentName = apartmentName;
        this.ownerName = ownerName;
        this.lastReading = lastReading;
    }   
}

