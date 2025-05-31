package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

public class RoomDAO {
    // Get All Room Available based on type
    public List<Room> getAvailableRoomsByType(int roomTypeId, String roomNumber) {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM room WHERE (room_type_id = ? AND is_available = 1) OR room_number = ?";
        
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setInt(1, roomTypeId);
            stmt.setString(2, roomNumber);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Room room = new Room (
                        rs.getInt("room_id"),
                        rs.getInt("room_type_id"),
                        rs.getString("room_number"),
                        rs.getBoolean("is_available")
                );
                
                rooms.add(room);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding rooms available: " + e.getMessage());
        }
        
        return rooms;
    }
}
