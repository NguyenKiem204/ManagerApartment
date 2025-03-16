package model;

<<<<<<< HEAD
=======
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
>>>>>>> main
import java.util.ArrayList;
import java.util.List;

public class Invoices {

    private int invoiceID;
    private double totalAmount;
<<<<<<< HEAD
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
=======
    private LocalDate publicDate;
    private String status;
    private String description;
    private LocalDate dueDate;
    private Resident resident;
    private Apartment apartment;
    private List<InvoiceDetail> details;
    private double muon;// muon =islate
    private LocalDate paydate;

    public Invoices(int invoiceID, double totalAmount, LocalDate publicDate, String status, String description, LocalDate dueDate, Resident resident, Apartment apartment,double muon, LocalDate paydate) {
>>>>>>> main
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.publicDate = publicDate;
        this.status = status;
        this.description = description;
        this.dueDate = dueDate;
        this.resident = resident;
        this.apartment = apartment;
<<<<<<< HEAD
        this.isLate = isLate;
        this.details = new ArrayList<>();
=======
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
>>>>>>> main
    }

    public Invoices() {
    }

<<<<<<< HEAD
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
=======
    public Invoices(int invoiceID, double totalAmount, LocalDate publicDate, String status, String description, LocalDate dueDate, Resident resident, Apartment apartment, double muon) {
>>>>>>> main
        this.invoiceID = invoiceID;
        this.totalAmount = totalAmount;
        this.publicDate = publicDate;
        this.status = status;
        this.description = description;
        this.dueDate = dueDate;
        this.resident = resident;
        this.apartment = apartment;
<<<<<<< HEAD
        this.details = details;
        this.isLate = isLate;
    }

    public Invoices(double totalAmount, String description, String dueDate, Resident resident, Apartment apartment, List<InvoiceDetail> details) {
=======
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
>>>>>>> main
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

<<<<<<< HEAD
    public String getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(String publicDate) {
        this.publicDate = publicDate;
    }

=======
>>>>>>> main
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

<<<<<<< HEAD
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
=======
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
>>>>>>> main
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

<<<<<<< HEAD
    public boolean isLate() {
        return isLate;
    }

    public void setIsLate(boolean isLate) {
        this.isLate = isLate;
=======
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
>>>>>>> main
    }
}
