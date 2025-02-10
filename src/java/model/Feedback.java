/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author admin
 */
public class Feedback {

    /*
    FeedbackID INT IDENTITY(1,1) PRIMARY KEY,
	Title NVARCHAR(100),
	[Description] NVARCHAR(500),
	[Date] DATETIME DEFAULT GETDATE(),
	StaffID INT NOT NULL,
	ResidentID INT,
     */
    private int feedbackID;
    private String title;
    private String description;
    private LocalDate date;
    private int rate;
    private int staffID;
    private int residentID;

    public Feedback() {
    }

    public Feedback(int feedbackID, String title, String description, LocalDate date, int rate, int staffID, int residentID) {
        this.feedbackID = feedbackID;
        this.title = title;
        this.description = description;
        this.date = date;
        this.rate = rate;
        this.staffID = staffID;
        this.residentID = residentID;
    }

    public Feedback(String title, String description, LocalDate date, int rate, int staffID, int residentID) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.rate = rate;
        this.staffID = staffID;
        this.residentID = residentID;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public int getResidentID() {
        return residentID;
    }

    public void setResidentID(int residentID) {
        this.residentID = residentID;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Feedback{" + "feedbackID=" + feedbackID + ", title=" + title + ", description=" + description + ", date=" + date + ", rate=" + rate + ", staffID=" + staffID + ", residentID=" + residentID + '}';
    }

}
