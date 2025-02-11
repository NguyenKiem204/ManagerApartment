package model;

import java.util.ArrayList;
import java.util.List;

public class Invoices {

    private int invoiceID;
    private double totalAmount;
    private String publicDate;
    private String status;
    private String description;
    private String dueDate;
    private Resident resident;
    private Apartment apartment;
    private List<InvoiceDetail> details;
    private boolean isLate;

    public Invoices(int invoiceID, double totalAmount, String publicDate, String status,
            String description, String dueDate, Resident resident,
            Apartment apartment, boolean isLate) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.publicDate = publicDate;
        this.status = status;
        this.description = description;
        this.dueDate = dueDate;
        this.resident = resident;
        this.apartment = apartment;
        this.isLate = isLate;
        this.details = new ArrayList<>();
    }

    public Invoices() {
    }

    public Invoices(int invoiceID, double totalAmount, String description, String dueDate) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.description = description;
        this.dueDate = dueDate;
    }

    public Invoices(int invoiceID, double totalAmount, String description, String dueDate, List<InvoiceDetail> details) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.description = description;
        this.dueDate = dueDate;
        this.details = details;

    }

    public Invoices(int invoiceID, double totalAmount, String publicDate, String status, String description, String dueDate, Resident resident, Apartment apartment, List<InvoiceDetail> details, boolean isLate) {
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.publicDate = publicDate;
        this.status = status;
        this.description = description;
        this.dueDate = dueDate;
        this.resident = resident;
        this.apartment = apartment;
        this.details = details;
        this.isLate = isLate;
    }

    public Invoices(double totalAmount, String description, String dueDate, Resident resident, Apartment apartment, List<InvoiceDetail> details) {
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

    public String getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(String publicDate) {
        this.publicDate = publicDate;
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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
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

    public boolean isLate() {
        return isLate;
    }

    public void setIsLate(boolean isLate) {
        this.isLate = isLate;
    }
}
