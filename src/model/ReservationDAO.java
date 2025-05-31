package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
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
    
    // Delete reservation
    public boolean delete(int reservationId) {
        String query = "DELETE FROM reservation WHERE reservation_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservationId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Delete reservation failed: " + e.getMessage());
            return false;
        }
    }

    // Checkout reservation
    public boolean checkout(int reservationId) {
        String updateReservationQuery = "UPDATE reservation SET reservation_status = false WHERE reservation_id = ?";
        String updateRoomQuery = "UPDATE room SET is_available = true WHERE room_id = (SELECT room_id FROM reservation WHERE reservation_id = ?)";

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(updateReservationQuery)) {
                stmt.setInt(1, reservationId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(updateRoomQuery)) {
                stmt2.setInt(1, reservationId);
                stmt2.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Checkout update failed: " + e.getMessage());
            return false;
        }
    }

    // showing list reservation
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();

        String query = "SELECT r.reservation_id, g.first_name, g.last_name, rm.room_number, rt.price, " +
                       "r.check_in_date, r.check_out_date, r.reservation_status " +
                       "FROM reservation r " +
                       "JOIN guest g ON r.guest_id = g.guest_id " +
                       "JOIN room rm ON r.room_id = rm.room_id " +
                       "JOIN room_type rt ON rm.room_type_id = rt.room_type_id";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("reservation_id");
                String guestName = rs.getString("first_name") + " " + rs.getString("last_name");
                String roomNumber = rs.getString("room_number");
                java.sql.Date checkIn = rs.getDate("check_in_date");
                java.sql.Date checkOut = rs.getDate("check_out_date");
                boolean status = rs.getBoolean("reservation_status");
                int pricePerNight = rs.getInt("price");

                long diff = checkOut.getTime() - checkIn.getTime();
                int nights = (int) (diff / (1000 * 60 * 60 * 24));
                int totalPrice = nights * pricePerNight;

                Reservation reservation = new Reservation(
                    id,
                    guestName,
                    roomNumber,
                    checkIn,
                    checkOut,
                    totalPrice,
                    status
                );
                reservations.add(reservation);
            }

        } catch (SQLException e) {
            System.err.println("Find all reservations failed: " + e.getMessage());
        }

        return reservations;
    }
}
