package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Apartment;
import model.InvoiceDetail;
import model.Invoices;
import model.Resident;
import model.TypeBill;

public class InvoiceDAO implements DAOInterface<Invoices, Integer> {

    @Override
    public int insert(Invoices t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int update(Invoices t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int delete(Invoices t) {
        int row = 0;
        String sqlDeleteDetails = "DELETE FROM InvoiceDetail WHERE InvoiceID = ?";
        String sqlDeleteInvoice = "DELETE FROM Invoice WHERE InvoiceID = ?";

        try (Connection connection = DBContext.getConnection()) {
            try (PreparedStatement psDetail = connection.prepareStatement(sqlDeleteDetails)) {
                psDetail.setInt(1, t.getInvoiceID());
                psDetail.executeUpdate();
            }
            try (PreparedStatement psInvoice = connection.prepareStatement(sqlDeleteInvoice)) {
                psInvoice.setInt(1, t.getInvoiceID());
                row = psInvoice.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return row;
    }

    public void deletebyID(int InvoiceId) {

        String sqlDeleteDetails = "DELETE FROM InvoiceDetail WHERE InvoiceID = ?";
        String sqlDeleteInvoice = "DELETE FROM Invoice WHERE InvoiceID = ?";

        try (Connection connection = DBContext.getConnection()) {
            try (PreparedStatement psDetail = connection.prepareStatement(sqlDeleteDetails)) {
                psDetail.setInt(1, InvoiceId);
                psDetail.executeUpdate();
            }
            try (PreparedStatement psInvoice = connection.prepareStatement(sqlDeleteInvoice)) {
                psInvoice.setInt(1, InvoiceId);
                psInvoice.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void deleteInvoiceDetail(int InvoiceId) {

        String sqlDeleteDetails = "DELETE FROM InvoiceDetail WHERE InvoiceID = ?";
        try (Connection connection = DBContext.getConnection()) {
            try (PreparedStatement psDetail = connection.prepareStatement(sqlDeleteDetails)) {
                psDetail.setInt(1, InvoiceId);
                psDetail.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    // Lấy danh sách hóa đơn từ database
    @Override
    public List<Invoices> selectAll() {
        List<Invoices> list = new ArrayList<>();

        String sql = "SELECT inv.InvoiceID, inv.TotalAmount, inv.PublicDate, inv.Status AS InvoiceStatus, "
                + "inv.Description AS InvoiceDescription, inv.DueDate, inv.islate,"
                + "idt.DetailID, idt.Amount, idt.Description AS DetailDescription, "
                + "tb.TypeName AS BillType, "
                + "res.ResidentID, res.FullName AS ResidentName, res.PhoneNumber AS ResidentPhone, res.Email AS ResidentEmail, "
                + "apt.ApartmentID, apt.ApartmentName, apt.Block, apt.Status AS ApartmentStatus, apt.Type AS ApartmentType "
                + "FROM Invoice inv "
                + "JOIN Resident res ON inv.ResidentID = res.ResidentID "
                + "JOIN Contract ct ON res.ResidentID = ct.ResidentID "
                + "JOIN Apartment apt ON ct.ApartmentID = apt.ApartmentID "
                + "LEFT JOIN InvoiceDetail idt ON inv.InvoiceID = idt.InvoiceID "
                + "LEFT JOIN TypeBill tb ON idt.TypeBillID = tb.TypeBillID "
                + "ORDER BY inv.InvoiceID;";

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("InvoiceID");
                Invoices invoice = null;
                for (Invoices inv : list) {
                    if (inv.getInvoiceID() == invoiceID) {
                        invoice = inv;
                        break;
                    }
                }
                if (invoice == null) {
                    Resident resident = new Resident(
                            rs.getInt("ResidentID"),
                            rs.getString("ResidentName"),
                            rs.getString("ResidentPhone"),
                            rs.getString("ResidentEmail")
                    );

                    Apartment apartment = new Apartment(
                            rs.getInt("ApartmentID"),
                            rs.getString("ApartmentName"),
                            rs.getString("Block"),
                            rs.getString("ApartmentStatus"),
                            rs.getString("ApartmentType")
                    );
                    int Late = rs.getInt("islate");
                    double totalamount = rs.getDouble("TotalAmount");
                    LocalDate publicDate = rs.getDate("PublicDate") != null ? rs.getDate("PublicDate").toLocalDate() : null;
                    LocalDate dueDate = rs.getDate("DueDate") != null ? rs.getDate("DueDate").toLocalDate() : null;
                    if (rs.getString("InvoiceStatus").equals("Unpaid") && rs.getInt("islate") != 1 && dueDate.isBefore(LocalDate.now())) {

                        totalamount = rs.getDouble("TotalAmount") * 0.02 + rs.getDouble("TotalAmount");
                        Late++;
                        this.updateislate(Late, totalamount, rs.getInt("InvoiceID"));
                    }
                    invoice = new Invoices(
                            invoiceID,
                            totalamount,
                            publicDate,
                            rs.getString("InvoiceStatus"),
                            rs.getString("InvoiceDescription"),
                            dueDate,
                            resident,
                            apartment,
                            Late
                    );

                    list.add(invoice);
                }

                int detailID = rs.getInt("DetailID");
                if (!rs.wasNull()) {
                    InvoiceDetail detail = new InvoiceDetail(
                            detailID,
                            rs.getDouble("Amount"),
                            rs.getString("DetailDescription"),
                            rs.getString("BillType")
                    );
                    invoice.addDetail(detail);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void updateislate(int islate, double totalamount, int id) {
        String sql = "UPDATE Invoice  \n"
                + "SET TotalAmount =?,  \n"
                + "    isLate = ?  \n"
                + "WHERE InvoiceID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, totalamount);
            ps.setInt(2, islate);
            ps.setInt(3, id);

            int rowsUpdated = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public Invoices selectById(Integer id) {
        String sql = "SELECT \n"
                + "    inv.InvoiceID, \n"
                + "    inv.TotalAmount, \n"
                + "    inv.PublicDate, \n"
                + "    inv.Status AS InvoiceStatus, \n"
                + "    inv.Description AS InvoiceDescription, \n"
                + "    inv.DueDate,inv.islate, \n"
                + "    idt.DetailID, \n"
                + "    idt.Amount, \n"
                + "    idt.Description AS DetailDescription, \n"
                + "    idt.TypeBillID AS TypeBillID , \n"
                + "    tb.TypeName AS BillType, \n"
                + "    res.ResidentID, \n"
                + "    res.FullName AS ResidentName, \n"
                + "    res.PhoneNumber AS ResidentPhone, \n"
                + "    res.Email AS ResidentEmail, \n"
                + "    apt.ApartmentID, \n"
                + "    apt.ApartmentName, \n"
                + "    apt.Block, \n"
                + "    apt.Status AS ApartmentStatus, \n"
                + "    apt.Type AS ApartmentType,\n"
                + "    tb.TypeName AS TypeName\n"
                + "FROM Invoice inv\n"
                + "JOIN Resident res ON inv.ResidentID = res.ResidentID\n"
                + "JOIN Contract ct ON res.ResidentID = ct.ResidentID\n"
                + "JOIN Apartment apt ON ct.ApartmentID = apt.ApartmentID\n"
                + "LEFT JOIN InvoiceDetail idt ON inv.InvoiceID = idt.InvoiceID\n"
                + "LEFT JOIN TypeBill tb ON idt.TypeBillID = tb.TypeBillID\n"
                + "WHERE inv.InvoiceID=?;";

        Invoices invoice = null;

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (invoice == null) {
                        Resident resident = new Resident(
                                rs.getInt("ResidentID"),
                                rs.getString("ResidentName"),
                                rs.getString("ResidentEmail"),
                                rs.getString("ResidentPhone")
                        );

                        Apartment apartment = new Apartment(
                                rs.getInt("ApartmentID"),
                                rs.getString("ApartmentName"),
                                rs.getString("Block"),
                                rs.getString("ApartmentStatus"),
                                rs.getString("ApartmentType")
                        );

                        LocalDate publicDate = rs.getDate("PublicDate") != null ? rs.getDate("PublicDate").toLocalDate() : null;
                        LocalDate dueDate = rs.getDate("DueDate") != null ? rs.getDate("DueDate").toLocalDate() : null;

                        invoice = new Invoices(
                                rs.getInt("InvoiceID"),
                                rs.getDouble("TotalAmount"),
                                publicDate,
                                rs.getString("InvoiceStatus"),
                                rs.getString("InvoiceDescription"),
                                dueDate,
                                resident,
                                apartment,
                                rs.getInt("islate")
                        );
                    }

                    int detailID = rs.getInt("DetailID");
                    if (detailID > 0) {
                        InvoiceDetail detail = new InvoiceDetail(
                                detailID,
                                rs.getDouble("Amount"),
                                rs.getString("DetailDescription"),
                                rs.getInt("TypeBillID"),
                                rs.getString("TypeName")
                        );
                        invoice.addDetail(detail);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoice;
    }

    public List<TypeBill> getAllTypeBills() {
    List<TypeBill> typeBills = new ArrayList<>();
    String sql = "SELECT * FROM TypeBill";

    try (Connection connection = DBContext.getConnection(); 
         PreparedStatement ps = connection.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("TypeBillID");
            String name = rs.getString("TypeName");
            typeBills.add(new TypeBill(id, name));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return null; // ❌ Trả về null thay vì danh sách rỗng -> Lỗi S1168
    }
    return typeBills;
}

    public void insertInvoice(Invoices invoice) {
        String sql = "INSERT INTO Invoice (ResidentID, TotalAmount, [Description], DueDate, [Status], StaffID,islate) "
                + "VALUES (?, ?, ?, ?, ?, ?,?)";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, invoice.getResident().getResidentId());
            ps.setDouble(2, invoice.getTotalAmount());
            ps.setString(3, invoice.getDescription());
            ps.setDate(4, java.sql.Date.valueOf(invoice.getDueDate()));
            ps.setString(5, "Unpaid");
            ps.setInt(6, 2);
            ps.setInt(7, 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertInvoiceDetail(int invoiceId, InvoiceDetail invoiceDetail) {
        String sql = "INSERT INTO InvoiceDetail (Amount, [Description], InvoiceID, TypeBillID) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, invoiceDetail.getAmount());
            ps.setString(2, invoiceDetail.getDescription());
            ps.setInt(3, invoiceId);
            ps.setInt(4, invoiceDetail.getTypeBillId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLatestInvoiceID() {
        int latestInvoiceID = -1;
        String query = "SELECT TOP 1 InvoiceID FROM Invoice ORDER BY InvoiceID DESC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                latestInvoiceID = rs.getInt("InvoiceID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return latestInvoiceID;
    }

    public void updateInvoice(Invoices inv) {
        String sql = "UPDATE Invoice\n"
                + "SET  \n"
                + "    TotalAmount = ?,  \n"
                + "    [Description] = ?, \n"
                + "    DueDate = ?\n"
                + "WHERE InvoiceID = ?;";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, inv.getTotalAmount());
            ps.setString(2, inv.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(inv.getDueDate()));
            ps.setInt(4, inv.getInvoiceID());
            int rowsUpdated = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void updateStatusInvoice(int id) {
        String sql = "UPDATE Invoice SET Status = 'Paid', PaymentDate = GETDATE() WHERE InvoiceID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Invoice " + id + " updated successfully!");
            } else {
                System.out.println("Invoice " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Invoices> filterInvoices(int apartmentId, String status, LocalDate fromDate, LocalDate dueDate) {
        List<Invoices> invoices = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        String sql = "SELECT inv.InvoiceID, inv.TotalAmount, inv.PublicDate, inv.Status AS InvoiceStatus, "
                + "inv.Description AS InvoiceDescription, inv.DueDate, inv.islate, inv.PaymentDate, "
                + "idt.DetailID, idt.Amount, idt.Description AS DetailDescription, "
                + "tb.TypeName AS BillType, "
                + "res.ResidentID, res.FullName AS ResidentName, res.PhoneNumber AS ResidentPhone, res.Email AS ResidentEmail, "
                + "apt.ApartmentID, apt.ApartmentName, apt.Block, apt.Status AS ApartmentStatus, apt.Type AS ApartmentType "
                + "FROM Invoice inv "
                + "JOIN Resident res ON inv.ResidentID = res.ResidentID "
                + "JOIN Contract ct ON res.ResidentID = ct.ResidentID "
                + "JOIN Apartment apt ON ct.ApartmentID = apt.ApartmentID "
                + "LEFT JOIN InvoiceDetail idt ON inv.InvoiceID = idt.InvoiceID "
                + "LEFT JOIN TypeBill tb ON idt.TypeBillID = tb.TypeBillID "
                + "WHERE 1=1";

        if (apartmentId != 0) {
            sql += " AND apt.ApartmentID = ?";
            parameters.add(apartmentId);
        }
        if (status != null && !status.isEmpty()) {
            sql += " AND inv.Status = ?";
            parameters.add(status);
        }
        if (fromDate != null) {
            sql += " AND inv.PublicDate >= ?";
            parameters.add(java.sql.Date.valueOf(fromDate));
        }
        if (dueDate != null) {
            sql += " AND inv.PublicDate <= ?";
            parameters.add(java.sql.Date.valueOf(dueDate));
        }

        sql += " ORDER BY inv.InvoiceID";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("InvoiceID");
                Invoices invoice = null;
                for (Invoices inv : invoices) {
                    if (inv.getInvoiceID() == invoiceID) {
                        invoice = inv;
                        break;
                    }
                }
                if (invoice == null) {
                    Resident resident = new Resident(
                            rs.getInt("ResidentID"),
                            rs.getString("ResidentName"),
                            rs.getString("ResidentPhone"),
                            rs.getString("ResidentEmail")
                    );

                    Apartment apartment = new Apartment(
                            rs.getInt("ApartmentID"),
                            rs.getString("ApartmentName"),
                            rs.getString("Block"),
                            rs.getString("ApartmentStatus"),
                            rs.getString("ApartmentType")
                    );
                    int late = rs.getInt("islate");
                    double totalAmount = rs.getDouble("TotalAmount");
                    LocalDate publicDate = rs.getDate("PublicDate") != null ? rs.getDate("PublicDate").toLocalDate() : null;
                    LocalDate dueDateLocal = rs.getDate("DueDate") != null ? rs.getDate("DueDate").toLocalDate() : null;
                    LocalDate payDate = rs.getDate("PaymentDate") != null ? rs.getDate("PaymentDate").toLocalDate() : null;
                    if ("Unpaid".equals(rs.getString("InvoiceStatus")) && late != 1 && dueDateLocal != null && dueDateLocal.isBefore(LocalDate.now())) {
                        totalAmount = rs.getDouble("TotalAmount") * 1.02;
                        late++;
                        this.updateislate(late, totalAmount, invoiceID);
                    }
                    invoice = new Invoices(
                            invoiceID,
                            totalAmount,
                            publicDate,
                            rs.getString("InvoiceStatus"),
                            rs.getString("InvoiceDescription"),
                            dueDateLocal,
                            resident,
                            apartment,
                            late,
                            payDate
                    );

                    invoices.add(invoice);
                }

                int detailID = rs.getInt("DetailID");
                if (!rs.wasNull()) {
                    InvoiceDetail detail = new InvoiceDetail(
                            detailID,
                            rs.getDouble("Amount"),
                            rs.getString("DetailDescription"),
                            rs.getString("BillType")
                    );
                    invoice.addDetail(detail);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invoices;
    }
}
