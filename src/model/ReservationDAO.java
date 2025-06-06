package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import util.DBUtil;

public class ReservationDAO {
    // Create: Add new reservationo
    public boolean insert(Reservation reservation) {
        String query = "INSERT INTO reservation (reservation_id, guest_id, room_id, check_in_date, check_out_date, total_price, reservation_status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, reservation.getReservationId());
                stmt.setInt(2, reservation.getGuestId());
                stmt.setInt(3, reservation.getRoomId());
                stmt.setDate(4, new Date(reservation.getCheckInDate().getTime()));
                stmt.setDate(5, new Date(reservation.getCheckOutDate().getTime()));
                stmt.setInt(6, reservation.getTotalPrice());
                stmt.setBoolean(7, true);

                stmt.executeUpdate();
                return true;

        } catch (SQLException e) {
            System.err.println("ReservationDAO.insert failed: " + e.getMessage());
            return false;
        }
    }

    // Read: Find all reservation data
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT r.reservation_id, g.first_name, g.last_name, rm.room_number, r.total_price, " +
                "r.check_in_date, r.check_out_date, r.reservation_status " +
                "FROM reservation r " +
                "JOIN guest g ON r.guest_id = g.guest_id " +
                "JOIN room rm ON r.room_id = rm.room_id " +
                "JOIN room_type rt ON rm.room_type_id = rt.room_type_id";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("reservation_id");
                String guestName = rs.getString("first_name") + " " + rs.getString("last_name");
                String roomNumber = rs.getString("room_number");
                Date checkIn = rs.getDate("check_in_date");
                Date checkOut = rs.getDate("check_out_date");
                boolean status = rs.getBoolean("reservation_status");
                int totalPrice = rs.getInt("total_price");

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
            System.err.println("ReservationDAO.findAll failed: " + e.getMessage());
        }
        return reservations;
    }

    // Read: get reservation details by id
    public Reservation findById(int reservationId) {
        String query = "SELECT r.reservation_id, g.guest_id, g.first_name, g.last_name, g.email, g.phone_number, rm.room_number, rt.type_name, " +
                "r.check_in_date, r.check_out_date, r.reservation_status " +
                "FROM reservation r " +
                "JOIN guest g ON r.guest_id = g.guest_id " +
                "JOIN room rm ON r.room_id = rm.room_id " +
                "JOIN room_type rt ON rm.room_type_id = rt.room_type_id " +
                "WHERE r.reservation_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservationId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Reservation (
                            rs.getInt("r.reservation_id"),
                            rs.getInt("g.guest_id"),
                            rs.getString("g.first_name"),
                            rs.getString("g.last_name"),
                            rs.getString("g.email"),
                            rs.getString("g.phone_number"),
                            rs.getString("rt.type_name"),
                            rs.getString("rm.room_number"),
                            rs.getDate("check_in_date"),
                            rs.getDate("check_out_date")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("ReservationDAO.findById failed for reservationId " + reservationId + ": " + e.getMessage());
        }
        return null;
    }

    // Update: Change reservation details by id
    public boolean update(Reservation reservation) {
        String query = "UPDATE reservation SET guest_id = ?, check_in_date = ?, check_out_date = ?, room_id = ? WHERE reservation_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, reservation.getGuestId());
                stmt.setDate(2, new java.sql.Date(reservation.getCheckInDate().getTime()));
                stmt.setDate(3, new java.sql.Date(reservation.getCheckOutDate().getTime()));
                stmt.setInt(4, reservation.getRoomId());
                stmt.setInt(5, reservation.getReservationId());

                return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("ReservatinDAO.update failed for reservationId " + reservation.getReservationId()+ ": " + e.getMessage());
            return false;
        }
    }

    // Update: Change reservation status updateReservationStatus by id
    public boolean updateReservationStatus(int reservationId) {
        String query = "UPDATE reservation SET reservation_status = false WHERE reservation_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, reservationId);

                return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("ReservationDAO.updateReservationStatus failed for reservationId " + reservationId + ": " + e.getMessage());
            return false;
        }
    }
    
    // Delete: Remove reservation by id
    public boolean delete(int reservationId) {
        String query = "DELETE FROM reservation WHERE reservation_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservationId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("ReservationDAO.delete failed for reservationId " + reservationId + ": " + e.getMessage());
            return false;
        }
    }
}
