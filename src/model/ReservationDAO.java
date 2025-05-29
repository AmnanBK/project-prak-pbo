package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBUtil;

public class ReservationDAO {
    // Add new reservation
    public boolean insert(Reservation reservation) {
        String query = "INSERT INTO reservation (reservation_id, guest_id, room_id, check_in_date, check_out_date) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservation.getReservationId());
            stmt.setInt(2, reservation.getGuestId());
            stmt.setInt(3, reservation.getRoomId());
            stmt.setDate(5, new java.sql.Date(reservation.getCheckInDate().getTime()));
            stmt.setDate(6, new java.sql.Date(reservation.getCheckOutDate().getTime()));

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Insert reservation failed: " + e.getMessage());
            return false;
        }
    }
}
