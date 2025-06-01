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
}
