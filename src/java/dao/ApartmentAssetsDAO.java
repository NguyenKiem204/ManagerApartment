/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ApartmentAssets;
//import model.StatusApartmentAssets;

/**
 *
 * @author admin
 */
public class ApartmentAssetsDAO implements DAOInterface<ApartmentAssets, Integer> {

    private AssetCategoryDAO acdao = new AssetCategoryDAO();
    private StatusApartmentAssetsDAO saadao = new StatusApartmentAssetsDAO();

    @Override
    public int insert(ApartmentAssets t) {
        int row = 0;
        String sqlInsert = """
                           INSERT INTO [dbo].[ApartmentAssets]
                                      ([AssetName]
                                      ,[CategoryID]
                                      ,[BoughtOn]
                                      ,[Quantity]
                                      ,[UpdatedAt]
                                      ,[Location]
                                      ,[StatusID])
                                VALUES
                                      (?, ?, ?, ?, ?, ?, ?)""";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            ps.setString(1, t.getAssetName());
            ps.setInt(2, t.getCategory().getCategoryId());
            ps.setDate(3, (Date) t.getBoughtOn());
            ps.setInt(4, t.getQuantity());
            ps.setTimestamp(5, Timestamp.valueOf(t.getUpdatedAt()));
            ps.setString(6, t.getLocation());
            ps.setInt(7, t.getStatus().getStatusId());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentAssetsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public int update(ApartmentAssets t) {
        int rowUpdated = 0;
        String sql = """
                 UPDATE [dbo].[ApartmentAssets]
                    SET [AssetName] = ?
                       ,[CategoryID] = ?
                       ,[Quantity] = ?
                       ,[UpdatedAt] = ?
                       ,[Location] = ?
                       ,[StatusID] = ?
                  WHERE AssetID = ?""";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getAssetName());
            ps.setInt(2, t.getCategory().getCategoryId());
            ps.setInt(3, t.getQuantity());
            ps.setTimestamp(4, Timestamp.valueOf(t.getUpdatedAt()));
            ps.setString(5, t.getLocation());
            ps.setInt(6, t.getStatus().getStatusId());
            ps.setInt(7, t.getAssetId());

            rowUpdated = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public int delete(ApartmentAssets t) {
        int row = 0;
        String sql = "DELETE FROM ApartmentAssets WHERE AssetID = ?";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, t.getAssetId());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentAssets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public List<ApartmentAssets> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ApartmentAssets selectById(Integer id) {
        String sql = "SELECT * FROM [ApartmentAssets] WHERE AssetID = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ApartmentAssets aa = new ApartmentAssets(rs.getInt("AssetID"),
                              rs.getString("AssetName"),
                              acdao.selectById(rs.getInt("CategoryID")),
                              rs.getDate("BoughtOn"),
                              rs.getInt("Quantity"),
                              rs.getTimestamp("updatedAt").toLocalDateTime(),
                              rs.getString("Location"),
                              saadao.selectById(rs.getInt("StatusID")));
                    return aa;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<ApartmentAssets> selectFirstPage() {
        List<ApartmentAssets> list = new ArrayList<>();
        String sql = "SELECT * FROM ApartmentAssets ORDER BY AssetID OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY";

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ApartmentAssets aa = new ApartmentAssets(rs.getInt("AssetID"),
                          rs.getString("AssetName"),
                          acdao.selectById(rs.getInt("CategoryID")),
                          rs.getDate("BoughtOn"),
                          rs.getInt("Quantity"),
                          rs.getTimestamp("updatedAt").toLocalDateTime(),
                          rs.getString("Location"),
                          saadao.selectById(rs.getInt("StatusID")));

                list.add(aa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int selectAllToCount() {
        String sql = "SELECT count(*) FROM ApartmentAssets";
        int num = 0;

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }

    public List<ApartmentAssets> getAllApartmentAssetsBySearchOrFilterOrSort(String keySearch, int catId, int keySort, int page, int pageSize) {
        List<ApartmentAssets> list = new ArrayList<>();
        String sql = """
                     SELECT *
                     FROM ApartmentAssets a 
                       join AssetCategory ac on a.CategoryID = ac.CategoryID
                       join StatusApartmentAssets saa on saa.StatusID = a.StatusID
                     WHERE 1 = 1""";

        List<Object> params = new ArrayList<>();
        try {
//Xu ly search
            if (keySearch != null && !keySearch.trim().isEmpty()) {
                sql += " AND AssetName LIKE  ? ";
                params.add("%" + keySearch + "%");
            }

//Xu ly filter
            //check catId is null or not
            if (catId != 0) {
                sql += " AND a.CategoryID = ?";
                params.add(catId);
            }

//------------------------------------------
            //Xu ly sort
            if (keySort != 0) {
                switch (keySort) {
                    case 1 ->
                        sql += " ORDER BY AssetName";
                    case 2 ->
                        sql += " ORDER BY AssetName DESC";
                    default ->
                        throw new AssertionError();
                }
            } else {
                sql += " ORDER BY AssetID";
            }

//xu ly phan trang
            sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            int offset = (page - 1) * pageSize;
            params.add(offset);
            params.add(pageSize);

        } catch (Exception e) {
        }

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ApartmentAssets aa = new ApartmentAssets(rs.getInt("AssetID"),
                          rs.getString("AssetName"),
                          acdao.selectById(rs.getInt("CategoryID")),
                          rs.getDate("BoughtOn"),
                          rs.getInt("Quantity"),
                          rs.getTimestamp("updatedAt").toLocalDateTime(),
                          rs.getString("Location"),
                          saadao.selectById(rs.getInt("StatusID")));

                list.add(aa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentAssetsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getNumberOfApartmentAssetsBySearchOrFilterOrSort(String keySearch, int catId) {
        int num = 0;
        String sql = """
                     SELECT COUNT(*)
                     FROM ApartmentAssets a 
                        join AssetCategory ac on a.CategoryID = ac.CategoryID
                        join StatusApartmentAssets saa on saa.StatusID = a.StatusID
                     WHERE 1 = 1""";

        List<Object> params = new ArrayList<>();
        try {
//Xu ly search
            if (keySearch != null && !keySearch.trim().isEmpty()) {
                sql += " AND AssetName LIKE  ? ";
                params.add("%" + keySearch + "%");
                params.add("%" + keySearch + "%");
                params.add("%" + keySearch + "%");
            }

//Xu ly filter
            //check catId is null or not
            if (catId != 0) {
                sql += " AND a.CategoryID = ?";
                params.add(catId);
            }
        } catch (Exception e) {
        }

        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentAssetsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }

    public boolean isAssetNameExists(String assetName) {
        String sql = "SELECT COUNT(*) FROM ApartmentAssets WHERE AssetName = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, assetName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResidentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
