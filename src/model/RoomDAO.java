package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

public class RoomDAO {
    // Create: Add new room
    public boolean insert(Room room) {
        String query = "INSERT INTO room (room_id, room_type_id, room_number, is_available) VALUES (?, ?, ?, 1)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, room.getRoomId());
            stmt.setInt(2, room.getRoomTypeId());
            stmt.setString(3, room.getRoomNumber());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("RoomDAO.insert failed: " + e.getMessage());
            return false;
        }
    }

    // Read: Find all rooms and room type name
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT rm.room_id, rm.room_type_id, rm.room_number, rm.is_available, rt.type_name " +
                "FROM room rm " +
                "JOIN room_type rt ON rm.room_type_id = rt.room_type_id";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room (
                        rs.getInt("room_id"),
                        rs.getInt("room_type_id"),
                        rs.getString("room_number"),
                        rs.getBoolean("is_available"),
                        rs.getString("type_name")
                );
                rooms.add(room);
            }

        } catch (SQLException e) {
            System.err.println("RoomDAO.findAll failed: " + e.getMessage());
        }
        return rooms;
    }

    // Read: Find available rooms by type and room number
    public List<Room> findAvailableRooms(int roomTypeId, String roomNumber) {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM room WHERE (room_type_id = ? AND is_available = 1) OR room_number = ?";
        
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
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
            System.err.println("RoomDAO.findAvailableRooms failed: " + e.getMessage());
        }
        return rooms;
    }

    // Update: Change room details by id
    public boolean update(Room room) {
        String query = "UPDATE room SET room_number = ?, is_available = ?, room_type_id = ? WHERE room_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, room.getRoomNumber());
            stmt.setBoolean(2, room.getIsAvailable());
            stmt.setInt(3, room.getRoomTypeId());
            stmt.setInt(4, room.getRoomId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("RoomDAO.update failed for roomId " + room.getRoomId() + ": " + e.getMessage());
            return false;
        }
    }

    // Update: Change room availability by room_number
    public boolean updateRoomAvailability(String roomNumber, boolean isAvailable) {
        String query = "UPDATE room SET is_available = ? WHERE room_number = ?";

        try (Connection conn = DBUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, isAvailable);
            stmt.setString(2, roomNumber);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("RoomDAO.updateRoomAvailability failed for roomId " + roomNumber + ": " + e.getMessage());
            return false;
        }
    }
    
    // Delete: Remove room by id
    public boolean delete(int roomId) {
        String query = "DELETE FROM room WHERE room_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, roomId);

            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("RoomDAO.delete failed for roomId " + roomId + ": " + e.getMessage());
            return false;
        }
    }
}
