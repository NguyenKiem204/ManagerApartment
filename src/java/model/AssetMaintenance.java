/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.util.Date;
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
public class AssetMaintenance {
    int maintenanceID;
    ApartmentAssets asset;
    Date maintenanceDate;
    Staff staff;
    String description;
    BigDecimal cost;
    Date nextMaintenanceDate;

    public AssetMaintenance(ApartmentAssets asset, Date maintenanceDate, Staff staff, String description, BigDecimal cost, Date nextMaintenanceDate) {
        this.asset = asset;
        this.maintenanceDate = maintenanceDate;
        this.staff = staff;
        this.description = description;
        this.cost = cost;
        this.nextMaintenanceDate = nextMaintenanceDate;
    }

    
    
}
