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
public class Contract {
    Resident resident;
    Apartment apartment;
    LocalDate leaseStartDate;
    LocalDate leaseEndDate;
    public Date getFormattedDate1() {
        return Date.valueOf(leaseStartDate); // Chuyển LocalDate -> SQL Date
    }
    public Date getFormattedDate2() {
        return Date.valueOf(leaseEndDate); // Chuyển LocalDate -> SQL Date
    }
}

