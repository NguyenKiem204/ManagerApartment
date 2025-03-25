package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Invoices {

    private int invoiceID;
    private double totalAmount;
    private LocalDate publicDate;
    private String status;
    private String description;
    private LocalDate dueDate;
    private Resident resident;
    private Apartment apartment;
    private List<InvoiceDetail> details;
    private double muon;// muon =islate
    private LocalDate paydate;

    public Invoices(int invoiceID, double totalAmount, LocalDate publicDate, String status, String description, LocalDate dueDate, Resident resident, Apartment apartment, double muon, LocalDate paydate) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.publicDate = publicDate;
        this.status = status;
        this.description = description;
        this.dueDate = dueDate;
        this.resident = resident;
        this.apartment = apartment;
        this.muon = muon;
        this.paydate = paydate;
    }

    public Invoices(int invoiceID, double totalAmount, LocalDate publicDate, String status, String description, LocalDate dueDate, Resident resident, Apartment apartment, List<InvoiceDetail> details, double muon) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.publicDate = publicDate;
        this.status = status;
        this.description = description;
        this.dueDate = dueDate;
        this.resident = resident;
        this.apartment = apartment;
        this.details = details;
        this.muon = muon;
    }

    public Invoices() {
    }

    public Invoices(int invoiceID, double totalAmount, LocalDate publicDate, String status, String description, LocalDate dueDate, Resident resident, Apartment apartment, double muon) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.publicDate = publicDate;
        this.status = status;
        this.description = description;
        this.dueDate = dueDate;
        this.resident = resident;
        this.apartment = apartment;
        this.muon = muon;
    }

    public Invoices(int invoiceID, double totalAmount, String description, LocalDate dueDate) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.description = description;
        this.dueDate = dueDate;
    }

    public Invoices(int invoiceID, double totalAmount, String description, LocalDate dueDate, List<InvoiceDetail> details) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.description = description;
        this.dueDate = dueDate;
        this.details = details;
    }

    public Invoices(double totalAmount, String description, LocalDate dueDate, Resident resident, Apartment apartment, List<InvoiceDetail> details) {
        this.totalAmount = totalAmount;
        this.description = description;
        this.dueDate = dueDate;
        this.resident = resident;
        this.apartment = apartment;
        this.details = details;
    }

    public void addDetail(InvoiceDetail detail) {
        if (detail == null) {
            System.out.println("Lỗi: Không thể thêm chi tiết hóa đơn null.");
            return;
        }
        if (this.details == null) {
            this.details = new ArrayList<>();
        }
        this.details.add(detail);
    }

    // Getter & Setter
    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(LocalDate publicDate) {
        this.publicDate = publicDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public List<InvoiceDetail> getDetails() {
        return details;
    }

    public void setDetails(List<InvoiceDetail> details) {
        if (details == null) {
            System.out.println("Lỗi: Không thể gán danh sách chi tiết null.");
            return;
        }
        this.details = details;
    }

    public double getMuon() {
        return muon;
    }

    public void setMuon(double muon) {
        this.muon = muon;
    }

    public LocalDate getPaydate() {
        return paydate;
    }

    public void setPaydate(LocalDate paydate) {
        this.paydate = paydate;
    }

    public String getPublicDateft() {
        if (publicDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return publicDate.format(formatter);
    }

    public String getPaydateft() {
        if (paydate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return paydate.format(formatter);
    }

    public String getDueDateft() {
        if (dueDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dueDate.format(formatter);
    }

    public String getPublicTime() {
        return getTimeFormatted(publicDate);
    }

    public String getPayTime() {
        return getTimeFormatted(paydate);
    }

    public String getDueTime() {
        return getTimeFormatted(dueDate);
    }

    private String getTimeFormatted(LocalDate date) {
        if (date == null) {
            return "";
        }
        LocalTime defaultTime = LocalTime.MIDNIGHT; // Mặc định 00:00 vì LocalDate không có giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return defaultTime.format(formatter);
    }

    public String getPublicDateTimeFormatted() {
        return getDateTimeFormatted(publicDate);
    }

    public String getPayDateTimeFormatted() {
        return getDateTimeFormatted(paydate);
    }

    public String getDueDateTimeFormatted() {
        return getDateTimeFormatted(dueDate);
    }

    private String getDateTimeFormatted(LocalDate date) {
        if (date == null) {
            return "";
        }
        LocalTime defaultTime = LocalTime.MIDNIGHT;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return date.atTime(defaultTime).format(formatter);
    }

    public String getPublicDateElapsed() {
        return getTimeElapsedSinceTransaction(publicDate);
    }

    public String getPayDateElapsed() {
        return getTimeElapsedSinceTransaction(paydate);
    }

    public String getDueDateElapsed() {
        return getTimeElapsedSinceTransaction(dueDate);
    }

    public String getTimeElapsedSinceTransaction(LocalDate transactionDate) {
        if (transactionDate == null) {
            return "Không có dữ liệu";
        }

        // Kết hợp LocalDate với 00:00 vì không có giờ cụ thể
        LocalDateTime transactionDateTime = transactionDate.atTime(LocalTime.MIDNIGHT);
        LocalDateTime now = LocalDateTime.now();

        long days = ChronoUnit.DAYS.between(transactionDateTime, now);
        long hours = ChronoUnit.HOURS.between(transactionDateTime, now) % 24;
        long minutes = ChronoUnit.MINUTES.between(transactionDateTime, now) % 60;

        return String.format("%d ngày, %d giờ, %d phút trước", days, hours, minutes);
    }
}
