package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

public class RoomTypeDAO {
    // Get all room type
    public List<RoomType> findAll() {
        List<RoomType> roomTypes = new ArrayList<>();
        String query = "SELECT * FROM room_type";
        
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                RoomType roomType = new RoomType (
                        rs.getInt("room_type_id"),
                        rs.getString("type_name"),
                        rs.getInt("price")
                );
                
                roomTypes.add(roomType);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding rooms available: " + e.getMessage());
        }
        
        return roomTypes;
    }
    
    // Delete room type
    public boolean delete(int roomTypeId) {
        String query = "DELETE FROM room_type WHERE room_type_id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, roomTypeId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Delete room type failed: " + e.getMessage());
            return false;
        }
    }
    
        
    // Insert new room type
    public boolean insert(RoomType roomType) {
        String query = "INSERT INTO room_type (room_type_id, type_name, price) VALUES (?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);) {
            
            stmt.setInt(1, roomType.getRoomTypeId());
            stmt.setString(2, roomType.getTypeName());
            stmt.setInt(3, roomType.getPrice());
            
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error insert new room type: " + e.getMessage());
            return false;
        }
    }
    
    // Update
    public boolean update(RoomType roomType) {
        String sql = "UPDATE room_type SET type_name = ?, price = ? WHERE room_type_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, roomType.getTypeName());
            stmt.setInt(2, roomType.getPrice());
            stmt.setInt(3, roomType.getRoomTypeId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Find by Id
    public RoomType findById(int id) {
        RoomType roomType = null;

        String query = "SELECT * FROM room_type WHERE room_type_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int roomTypeId = rs.getInt("room_type_id");
                String typeName = rs.getString("type_name");
                int price = rs.getInt("price");

                roomType = new RoomType(roomTypeId, typeName, price);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomType;
    }

}
