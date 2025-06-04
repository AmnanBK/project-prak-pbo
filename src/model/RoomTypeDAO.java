package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

public class RoomTypeDAO {
    // Create: Add new room type
    public boolean insert(RoomType roomType) {
        String query = "INSERT INTO room_type (room_type_id, type_name, price) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, roomType.getRoomTypeId());
            stmt.setString(2, roomType.getTypeName());
            stmt.setInt(3, roomType.getPrice());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("RoomTypeDAO.insert failed: " + e.getMessage());
            return false;
        }
    }

    // Read: Search room type by id
    public RoomType findById(int roomTypeId) {
        String query = "SELECT * FROM room_type WHERE room_type_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, roomTypeId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RoomType(
                        rs.getInt("room_type_id"),
                        rs.getString("type_name"),
                        rs.getInt("price")
                );
            }

        } catch (SQLException e) {
            System.err.println("RoomTypeDAO.findById failed for roomTypeId " + roomTypeId + ": " + e.getMessage());
        }
        return null;
    }

    // Read: Find room type by name
    public RoomType findByName(String roomTypeName) {
        String query = "SELECT * FROM room_type WHERE type_name = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, roomTypeName);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RoomType(
                        rs.getInt("room_type_id"),
                        rs.getString("type_name"),
                        rs.getInt("price")
                );
            }

        } catch (SQLException e) {
            System.err.println("RoomTypeDAO.findByName failed for roomTypeName " + roomTypeName + ": " + e.getMessage());
        }
        return null;
    }

    // Read: Find all room types
    public List<RoomType> findAll() {
        List<RoomType> roomTypes = new ArrayList<>();
        String query = "SELECT * FROM room_type";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
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
            System.err.println("RoomTypeDAO.findAll failed: " + e.getMessage());
        }
        return roomTypes;
    }

    // Update: Change room type details by id
    public boolean update(RoomType roomType) {
        String query = "UPDATE room_type SET type_name = ?, price = ? WHERE room_type_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, roomType.getTypeName());
            stmt.setInt(2, roomType.getPrice());
            stmt.setInt(3, roomType.getRoomTypeId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("RoomTypeDAO.update failed for roomTypeId " + roomType.getRoomTypeId() + ": " + e.getMessage());
            return false;
        }
    }
    
    // Delete: Remove room type by id
    public boolean delete(int roomTypeId) {
        String query = "DELETE FROM room_type WHERE room_type_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, roomTypeId);

            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("RoomTypeDAO.delete failed for roomTypeId " + roomTypeId + ": " + e.getMessage());
            return false;
        }
    }
}
