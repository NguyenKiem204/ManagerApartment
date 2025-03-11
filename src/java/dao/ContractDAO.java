/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.Contract;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Apartment;
import model.Resident;


/**
 *
 * @author fptshop
 */
public class ContractDAO implements DAOInterface<Contract, Integer>{

    @Override
    public int insert(Contract contract) {
    int row = 0;
    String sql = "INSERT INTO Contract (ResidentID, ApartmentID, LeaseStartDate, LeaseEndDate) VALUES (?, ?, ?, ?)";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, contract.getResident().getResidentId());
        ps.setInt(2, contract.getApartment().getApartmentId());
        ps.setDate(3, Date.valueOf(contract.getLeaseStartDate()));
        ps.setDate(4, Date.valueOf(contract.getLeaseEndDate()));

        row = ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return row;
    }
    public boolean isOverlappingContract(int apartmentId, LocalDate leaseStartDate, LocalDate leaseEndDate) {
    String query = "SELECT COUNT(*) FROM Contract WHERE ApartmentID = ? AND " +
                   "(LeaseStartDate <= ? AND LeaseEndDate >= ?)";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, apartmentId);
        ps.setDate(2, Date.valueOf(leaseEndDate));  // End date của hợp đồng mới phải sau Start date của hợp đồng cũ
        ps.setDate(3, Date.valueOf(leaseStartDate)); // Start date của hợp đồng mới phải trước End date của hợp đồng cũ
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Có ít nhất 1 hợp đồng trùng thời gian
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}



    @Override
    public int update(Contract t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(Contract t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Contract> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Contract selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public Contract getContractByResidentId(int residentId) {
    Contract contract = null;
    String sql = "SELECT c.*, r.FullName AS TenantName, a.ApartmentName " +
                 "FROM Contract c " +
                 "JOIN Resident r ON c.ResidentID = r.ResidentID " +
                 "JOIN Apartment a ON c.ApartmentID = a.ApartmentID " +
                 "WHERE c.ResidentID = ?";

    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {

        ps.setInt(1, residentId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Resident tenant = new Resident();
            tenant.setResidentId(residentId);
            tenant.setFullName(rs.getString("TenantName")); // Lấy tên Tenant

            Apartment apartment = new Apartment();
            apartment.setApartmentId(rs.getInt("ApartmentID"));
            apartment.setApartmentName(rs.getString("ApartmentName")); // Lấy tên phòng

            contract = new Contract();
            contract.setResident(tenant);
            contract.setApartment(apartment);
            contract.setLeaseStartDate(rs.getDate("LeaseStartDate").toLocalDate());
            contract.setLeaseEndDate(rs.getDate("LeaseEndDate").toLocalDate());
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return contract;
}



}
