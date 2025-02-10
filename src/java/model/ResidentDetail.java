
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.time.LocalDate;
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
public class ResidentDetail {
    int residentId;
    String fullName;
    String phoneNumber;
    String cccd;
    String email;
    LocalDate dob;
    String sex;
    String status;
    String imageURL;
    String roleName; 
    int imageID;
    int roleID;
}
