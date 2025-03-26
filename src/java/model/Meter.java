
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public class Meter {

    int meterId;
    int apartmentId;
    String meterType;
    String meterNumber;
    LocalDate installationDate;
    String status;

    String apartmentName;
    String ownerName;
    BigDecimal lastReading;

    public Meter(int apartmentId, String meterType, String meterNumber, LocalDate installDateTime, String status) {
        this.apartmentId = apartmentId;
        this.meterType = meterType;
        this.meterNumber = meterNumber;
        this.installationDate = installationDate;
        this.status = status;
    }
    public  String getFormatInstallationDate() {
        if (installationDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return installationDate.format(formatter);
    }
}
