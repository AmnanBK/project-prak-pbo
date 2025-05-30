package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBUtil;

public class ReservationDAO {
    // Add new reservation
    public boolean insert(Reservation reservation) {
        String insertQuery = "INSERT INTO reservation (reservation_id, guest_id, room_id, check_in_date, check_out_date, reservation_status) VALUES (?, ?, ?, ?, ?, ?)";
        String updateRoomQuery = "UPDATE room SET is_available = 0 WHERE room_id = ?";

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setInt(1, reservation.getReservationId());
                stmt.setInt(2, reservation.getGuestId());
                stmt.setInt(3, reservation.getRoomId());
                stmt.setDate(4, new java.sql.Date(reservation.getCheckInDate().getTime()));
                stmt.setDate(5, new java.sql.Date(reservation.getCheckOutDate().getTime()));
                stmt.setBoolean(6, true);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(updateRoomQuery)) {
                stmt2.setInt(1, reservation.getRoomId());
                stmt2.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Insert reservation failed: " + e.getMessage());
            return false;
        }
    }
}
