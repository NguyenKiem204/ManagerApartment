

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
  public  String getFormatInstallationDate() {
        if (installationDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return installationDate.format(formatter);
    }
}

