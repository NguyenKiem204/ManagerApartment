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
                + "inv.Description AS InvoiceDescription, inv.DueDate, "
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

                // Kiểm tra xem hóa đơn đã tồn tại trong danh sách chưa
                for (Invoices inv : list) {
                    if (inv.getInvoiceID() == invoiceID) {
                        invoice = inv;
                        break;
                    }
                }

                // Nếu chưa có, tạo hóa đơn mới và thêm vào danh sách
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

                    // Xử lý thời gian
                    String publicDate = "";
                    java.sql.Timestamp publicTimestamp = rs.getTimestamp("PublicDate");
                    if (publicTimestamp != null) {
                        publicDate = sdf.format(publicTimestamp);
                    }

                    String dueDate = "";
                    java.sql.Timestamp dueTimestamp = rs.getTimestamp("DueDate");
                    if (dueTimestamp != null) {
                        dueDate = sdf.format(dueTimestamp);
                    }

                    // So sánh thời gian thực
                    boolean isLate = dueTimestamp != null && dueTimestamp.before(new java.util.Date());

                    invoice = new Invoices(
                            invoiceID,
                            rs.getDouble("TotalAmount"),
                            publicDate,
                            rs.getString("InvoiceStatus"),
                            rs.getString("InvoiceDescription"),
                            dueDate,
                            resident,
                            apartment,
                            isLate
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

    @Override
    public Invoices selectById(Integer id) {
        String sql = "SELECT \n"
                + "    inv.InvoiceID, \n"
                + "    inv.TotalAmount, \n"
                + "    inv.PublicDate, \n"
                + "    inv.Status AS InvoiceStatus, \n"
                + "    inv.Description AS InvoiceDescription, \n"
                + "    inv.DueDate, \n"
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
                + "where inv.InvoiceID=?;";

        Invoices invoice = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (invoice == null) {

                        Resident resident = new Resident(
                                rs.getInt("ResidentID"),
                                rs.getString("ResidentName"), // Sửa từ FullName thành ResidentName
                                rs.getString("ResidentEmail"), // Sửa từ Email thành ResidentEmail
                                rs.getString("ResidentPhone")
                        );

                        Apartment apartment = new Apartment(
                                rs.getInt("ApartmentID"),
                                rs.getString("ApartmentName"),
                                rs.getString("Block"),
                                rs.getString("ApartmentStatus"),
                                rs.getString("ApartmentType")
                        );
                        String publicDate = "";
                        java.sql.Timestamp publicTimestamp = rs.getTimestamp("PublicDate");
                        if (publicTimestamp != null) {
                            publicDate = sdf.format(publicTimestamp);
                        }

                        String dueDate = "";
                        java.sql.Timestamp dueTimestamp = rs.getTimestamp("DueDate");
                        if (dueTimestamp != null) {
                            dueDate = sdf.format(dueTimestamp);
                        }
                        boolean isLate = dueTimestamp != null && dueTimestamp.before(new java.util.Date());

                        invoice = new Invoices(
                                rs.getInt("InvoiceID"),
                                rs.getDouble("TotalAmount"),
                                publicDate,
                                rs.getString("InvoiceStatus"),
                                rs.getString("InvoiceDescription"),
                                dueDate,
                                resident,
                                apartment,
                                isLate
                        );
                    }

                    // Lấy thông tin chi tiết hóa đơn (nếu có)
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

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("TypeBillID");
                String name = rs.getString("TypeName");
                typeBills.add(new TypeBill(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeBills;
    }

    public void insertInvoice(Invoices invoice) {
        String sql = "INSERT INTO Invoice (ResidentID, TotalAmount, [Description], DueDate, [Status], StaffID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, invoice.getResident().getResidentId());
            ps.setDouble(2, invoice.getTotalAmount());
            ps.setString(3, invoice.getDescription());
            ps.setDate(4, java.sql.Date.valueOf(LocalDate.parse(invoice.getDueDate())));
            ps.setString(5, "Unpaid");
            ps.setInt(6, 2);

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
            ps.setString(3, inv.getDueDate());
            ps.setInt(4, inv.getInvoiceID());
            int rowsUpdated = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
     public static void main(String[] args) {
        InvoiceDAO dao = new InvoiceDAO();

        // Giả sử InvoiceID = 1 cần cập nhật
        Invoices updatedInvoice = new Invoices();
        updatedInvoice.setInvoiceID(1);
        updatedInvoice.setTotalAmount(1800.00);
        updatedInvoice.setDescription("Updated Invoice for February");
        updatedInvoice.setDueDate("2025-03-01"); // Định dạng yyyy-MM-dd

        // Gọi hàm updateInvoice
        dao.updateInvoice(updatedInvoice);

        // Kiểm tra lại xem dữ liệu đã cập nhật thành công chưa
        System.out.println("Hóa đơn đã được cập nhật thành công!");
    }

}
