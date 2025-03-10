/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDateTime;
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
public class ManagerFeedback {
    int managerFeedbackId;
    LocalDate monthYear;
    int totalFeedback;
    double avgRating;
    int positivePercentage;
    int negativePercentage;
    String strengths;
    String weaknesses;
    String staffResponse;
    String actionPlan;
    LocalDate deadline;
    LocalDateTime createdAt;
    Staff staff;

    public ManagerFeedback(LocalDate monthYear, int totalFeedback, double avgRating, int positivePercentage, int negativePercentage, String strengths, String weaknesses, String staffResponse, String actionPlan, LocalDate deadline, LocalDateTime createdAt, Staff staff) {
        this.monthYear = monthYear;
        this.totalFeedback = totalFeedback;
        this.avgRating = avgRating;
        this.positivePercentage = positivePercentage;
        this.negativePercentage = negativePercentage;
        this.strengths = strengths;
        this.weaknesses = weaknesses;
        this.staffResponse = staffResponse;
        this.actionPlan = actionPlan;
        this.deadline = deadline;
        this.createdAt = createdAt;
        this.staff = staff;
    }

    
}
