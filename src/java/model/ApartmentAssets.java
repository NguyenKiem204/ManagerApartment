/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Locale;
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
public class ApartmentAssets {
    
    int assetId;
    String assetName;
    AssetCategory category;
    Date boughtOn;
    int quantity;
    LocalDateTime updatedAt;
    String location;
    StatusApartmentAssets status;

    public ApartmentAssets(String assetName, AssetCategory category, Date boughtOn, int quantity, LocalDateTime updatedAt, String location, StatusApartmentAssets status) {
        this.assetName = assetName;
        this.category = category;
        this.boughtOn = boughtOn;
        this.quantity = quantity;
        this.updatedAt = updatedAt;
        this.location = location;
        this.status = status;
    }
    
    public String getFormattedUpdatedAt() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        return updatedAt.format(dateTimeFormatter); // Chuyển LocalDate -> SQL Date
    }
}
