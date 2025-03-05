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

    public Apartment(String apartmentName, String block, String status, String type, int ownerId) {
        this.apartmentName = apartmentName;
        this.block = block;
        this.status = status;
        this.type = type;
        this.ownerId = ownerId;
    }

    
}
