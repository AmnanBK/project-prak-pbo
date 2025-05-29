package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBUtil;

public class GuestDAO {
    // Search guest by id
    public Guest findById(int guestId) {
        String query = "SELECT * FROM guest WHERE guest_id = ?";
        
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setInt(1, guestId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Guest(
                        rs.getInt("guest_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding guest: " + e.getMessage());
        }
        
        return null;
    }
    
    // Add new guest
    public boolean insert(Guest guest) {
        String query = "INSERT INTO guest (guest_id, first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, guest.getGuestId());
            stmt.setString(2, guest.getFirstName());
            stmt.setString(3, guest.getLastName());
            stmt.setString(4, guest.getEmail());
            stmt.setString(5, guest.getPhoneNumber());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Insert guest failed: " + e.getMessage());
            return false;
        }
    }
}
