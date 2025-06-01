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
        String query = "INSERT INTO room_type (room_type_id, room_type_name, price) VALUES (?, ?, ?)";
        
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
}
