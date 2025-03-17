/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
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
public class UtilityBill {

    int billId;
    int apartmentId;
    LocalDateTime billingPeriodStart;
    LocalDateTime billingPeriodEnd;
    BigDecimal electricityConsumption;
    BigDecimal electricityCost;
    BigDecimal waterConsumption;
    BigDecimal waterCost;
    BigDecimal totalAmount;
    LocalDateTime generatedDate;
    LocalDateTime dueDate;
    String status;
    Integer invoiceId;
    int billingMonth;
    int billingYear;

    String apartmentName;
    String ownerName;
    String buildingName;
    Apartment apartment;
    Resident owner;

    public String getFormattedGeneratedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return generatedDate.format(formatter);
    }
    public String getFormattedDueDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dueDate.format(formatter);
    }
    public String getFormattedBillingPeriodStart() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return billingPeriodStart.format(formatter);
    }
    public String getFormattedBillingPeriodEnd() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return billingPeriodEnd.format(formatter);
    }
}
