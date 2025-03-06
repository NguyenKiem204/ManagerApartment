package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import validation.UrlUtils;
import validation.UrlUtils.FilterMapping;

public class UrlDAO {
    public FilterMapping getFilterMappingByName(String filterName) {
        FilterMapping mapping = null;
        String sql = "SELECT ID, EncodedUrl FROM UrlTable WHERE EncodedUrl LIKE ?";

        try (Connection connection = DBContext.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%<filter-name>" + filterName + "</filter-name>%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int mappingId = rs.getInt("ID");
                    String xmlData = rs.getString("EncodedUrl");
                    mapping = UrlUtils.deserializeFilterMapping(xmlData);
                    if (mapping != null) {
                        mapping.setId(mappingId);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapping;
    }
    
    public int saveFilterMapping(String filterName, List<String> urlPatterns) {
        int generatedId = -1;
        FilterMapping existingMapping = getFilterMappingByName(filterName);
        
        if (existingMapping != null) {
            List<String> combinedPatterns = new ArrayList<>(existingMapping.getUrlPatterns());
            for (String pattern : urlPatterns) {
                if (!combinedPatterns.contains(pattern)) {
                    combinedPatterns.add(pattern);
                }
            }
            
            String xmlData = UrlUtils.serializeFilterMapping(filterName, combinedPatterns);
            String sqlUpdate = "UPDATE UrlTable SET EncodedUrl = ? WHERE ID = ?";
            
            try (Connection connection = DBContext.getConnection(); 
                 PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {
                ps.setString(1, xmlData);
                ps.setInt(2, existingMapping.getId());
                
                int row = ps.executeUpdate();
                System.out.println("(" + row + " row(s) updated)");
                generatedId = existingMapping.getId();
            } catch (SQLException ex) {
                Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String xmlData = UrlUtils.serializeFilterMapping(filterName, urlPatterns);
            String sqlInsert = "INSERT INTO UrlTable (EncodedUrl) VALUES (?)";
            
            try (Connection connection = DBContext.getConnection(); 
                 PreparedStatement ps = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, xmlData);
                
                int row = ps.executeUpdate();
                System.out.println("(" + row + " row(s) affected)");
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return generatedId;
    }
    
    /**
     * Updates an existing filter mapping with completely new URL patterns
     * @param id ID of the mapping to update
     * @param filterName Filter name
     * @param urlPatterns New list of URL patterns
     * @return true if update was successful, false otherwise
     */
    public boolean updateFilterMapping(int id, String filterName, List<String> urlPatterns) {
        String xmlData = UrlUtils.serializeFilterMapping(filterName, urlPatterns);
        String sql = "UPDATE UrlTable SET EncodedUrl = ? WHERE ID = ?";
        
        try (Connection connection = DBContext.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, xmlData);
            ps.setInt(2, id);
            
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public FilterMapping getFilterMapping(int id) {
        FilterMapping mapping = null;
        String sql = "SELECT ID, EncodedUrl FROM UrlTable WHERE ID = ?";

        try (Connection connection = DBContext.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int mappingId = rs.getInt("ID");
                    String xmlData = rs.getString("EncodedUrl");
                    mapping = UrlUtils.deserializeFilterMapping(xmlData);
                    if (mapping != null) {
                        mapping.setId(mappingId);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapping;
    }
    
    public List<FilterMapping> getAllFilterMappings() {
        List<FilterMapping> mappings = new ArrayList<>();
        String sql = "SELECT ID, EncodedUrl FROM UrlTable";

        try (Connection connection = DBContext.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("ID");
                String xmlData = rs.getString("EncodedUrl");
                FilterMapping mapping = UrlUtils.deserializeFilterMapping(xmlData);
                if (mapping != null) {
                    mapping.setId(id);
                    mappings.add(mapping);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mappings;
    }

    public int deleteFilterMapping(int id) {
        int row = 0;
        String sql = "DELETE FROM UrlTable WHERE ID = ?";

        try (Connection connection = DBContext.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            row = ps.executeUpdate();
            System.out.println("(" + row + " row(s) deleted)");
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }
}