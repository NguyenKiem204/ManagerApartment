/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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
public class Role {
    private int roleID;
    private String roleName;
    private String description;

    public Role(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }
    
}
