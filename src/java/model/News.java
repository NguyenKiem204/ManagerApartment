/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.*;
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
public class News {

    int newsID;
    String title;
    String description;
    LocalDateTime sentDate;
    Staff staff;
    Image image;

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return sentDate.format(formatter);
    }

}
