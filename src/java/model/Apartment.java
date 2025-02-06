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

    public Apartment(String apartmentName, String block, String status, String type) {
        this.apartmentName = apartmentName;
        this.block = block;
        this.status = status;
        this.type = type;
    }
}
