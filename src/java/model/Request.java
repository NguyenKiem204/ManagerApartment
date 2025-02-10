/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author admin
 */
public class Request {

    /*
    RequestID INT IDENTITY(1,1) PRIMARY KEY,
    [Description] VARCHAR(1000) NOT NULL,
    Title VARCHAR(255) NOT NULL,
    [Status] VARCHAR(50) NOT NULL,-- CHECK (Status IN ('Pending', 'Processing', 'Resolved'))
	[Date] DATETIME DEFAULT GETDATE(),
    StaffID INT NULL, -- Nhân viên xử lý (nếu có)
    ResidentID INT NOT NULL,
	TypeRqID INT,
     */
    private int requestID;
    private String description;
    private String title;
    private String status;
    private LocalDate date;
    private int staffID;
    private int residentID;
    private int typeID;

    public Request() {
    }

    public Request(String description, String title, String status, LocalDate date, int staffID, int residentID, int typeID) {
        this.description = description;
        this.title = title;
        this.status = status;
        this.date = date;
        this.staffID = staffID;
        this.residentID = residentID;
        this.typeID = typeID;
    }
    

    public Request(int requestID, String description, String title, String status, LocalDate date, int staffID, int residentID, int typeID) {
        this.requestID = requestID;
        this.description = description;
        this.title = title;
        this.status = status;
        this.date = date;
        this.staffID = staffID;
        this.residentID = residentID;
        this.typeID = typeID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    @Override
    public String toString() {
        return "Request{" + "requestID=" + requestID + ", description=" + description + ", title=" + title + ", status=" + status + ", date=" + date + ", staffID=" + staffID + ", residentID=" + residentID + ", typeID=" + typeID + '}';
    }

}
