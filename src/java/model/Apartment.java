package model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Apartment {
    int apartmentId;
    String apartmentName;
    String block;
    String status;
    String type;
    int ownerId;
    String ownerName;

    public Apartment(int apartmentId, String apartmentName, String block, String status, String type, int ownerId) {
        this.apartmentId = apartmentId;
        this.apartmentName = apartmentName;
        this.block = block;
        this.status = status;
        this.type = type;
        this.ownerId = ownerId;
    }

    
    public Apartment(String apartmentName, String block, String status, String type, int ownerId) {
        this.apartmentName = apartmentName;
        this.block = block;
        this.status = status;
        this.type = type;
        this.ownerId = ownerId;
    }

    public Apartment(int apartmentId, String apartmentName, String block, String status, String type) {
        this.apartmentId = apartmentId;
        this.apartmentName = apartmentName;
        this.block = block;
        this.status = status;
        this.type = type;
    }

    public Apartment(int apartmentId) {
        this.apartmentId = apartmentId;
    }
}
