/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
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
    int managerFeedbackID;
    LocalDate MonthYear;
    int totalFeedback;
    double avgRating;
    int positivePercentage;
    int negativePercentage;
    String strengths;
    String weaknesses;
    String staffResponse;
    String actionPlan;
    LocalDate deadline;
    LocalDate createdAt;
    Staff staff;

    public ManagerFeedback(LocalDate MonthYear, int totalFeedback, double avgRating, int positivePercentage, int negativePercentage, String strengths, String weaknesses, String staffResponse, String actionPlan, LocalDate deadline, LocalDate createdAt, Staff staff) {
        this.MonthYear = MonthYear;
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
