/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class Rule {

    int ruleID;
    String ruleName;
    String ruleDescription;
    LocalDate publicDate;
    Staff staff;

    public Rule(int ruleID, String ruleName, String ruleDescription, LocalDate publicDate) {
        this.ruleID = ruleID;
        this.ruleName = ruleName;
        this.ruleDescription = ruleDescription;
        this.publicDate = publicDate;
    }

    public Rule(String ruleName, String ruleDescription, LocalDate publicDate) {
        this.ruleName = ruleName;
        this.ruleDescription = ruleDescription;
        this.publicDate = publicDate;
        Staff s = new Staff();
        s.setStaffId(1);
        this.staff = s;
    }

    public Rule(int ruleID) {
        this.ruleID = ruleID;
    }

    public String getFormattedPublicDate() {
        if (publicDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return publicDate.format(formatter);
    }

}
