

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

    public Meter(int apartmentId, String meterType, String apartmentName) {
        this.apartmentId = apartmentId;
        this.meterType = meterType;
        this.apartmentName = apartmentName;
    }

    
}

